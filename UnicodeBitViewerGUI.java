import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UnicodeBitViewerGUI extends JFrame {

    private JTextField inputField;
    private JTextArea outputArea;
    private JButton analyzeButton, exitButton;

    public UnicodeBitViewerGUI() {
        setTitle("Unicode Bit Viewer");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout setup
        setLayout(new BorderLayout(10, 10));

        // Input panel with label and text field
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.add(new JLabel("Enter characters or emoji:"), BorderLayout.WEST);

        inputField = new JTextField();
        inputPanel.add(inputField, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        analyzeButton = new JButton("Analyze");
        exitButton = new JButton("Exit");
        buttonsPanel.add(analyzeButton);
        buttonsPanel.add(exitButton);

        // Output area inside scroll pane
        outputArea = new JTextArea();
        outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        // Button actions
        analyzeButton.addActionListener(e -> analyzeInput());
        exitButton.addActionListener(e -> System.exit(0));
    }

    private void analyzeInput() {
        String input = inputField.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter some characters!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("--- Bit Code Information ---\n\n");

        for (int i = 0; i < input.length(); i++) {
            int codePoint = input.codePointAt(i);
            String binary = Integer.toBinaryString(codePoint);
            String hex = Integer.toHexString(codePoint).toUpperCase();

            sb.append("Character: ").append(new String(Character.toChars(codePoint))).append("\n");
            sb.append("Unicode Code Point: U+").append(hex).append("\n");
            sb.append("Decimal: ").append(codePoint).append("\n");
            sb.append("Binary: ").append(binary).append("\n");
            sb.append("Binary (32-bit padded): ").append(String.format("%32s", binary).replace(' ', '0')).append("\n\n");

            // Skip surrogate pair second char if emoji
            if (Character.isSupplementaryCodePoint(codePoint)) {
                i++;
            }
        }

        outputArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new UnicodeBitViewerGUI().setVisible(true);
        });
    }
}
