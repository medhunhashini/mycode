import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentRegistrationApp extends JFrame {
    private final JTextField idField = new JTextField(15);
    private final JTextField nameField = new JTextField(15);
    private final JTextField emailField = new JTextField(15);
    private final JComboBox<String> departmentBox = new JComboBox<>(
            new String[]{"Computer Science", "Mathematics", "Physics", "Commerce", "Arts"}
    );
    private final JRadioButton maleButton = new JRadioButton("Male");
    private final JRadioButton femaleButton = new JRadioButton("Female");
    private final JRadioButton otherButton = new JRadioButton("Other");
    private final JTable table;
    private final DefaultTableModel tableModel;

    public StudentRegistrationApp() {
        setTitle("Student Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(860, 520);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(12, 12));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new String[]{"Student ID", "Name", "Email", "Department", "Gender"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        genderGroup.add(otherButton);

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        genderPanel.add(otherButton);

        JButton registerButton = new JButton("Register Student");
        JButton clearButton = new JButton("Clear Form");
        JButton deleteButton = new JButton("Delete Selected");

        registerButton.addActionListener(e -> addStudent());
        clearButton.addActionListener(e -> clearForm());
        deleteButton.addActionListener(e -> deleteSelectedRow());

        int row = 0;
        addRow(formPanel, gbc, row++, "Student ID:", idField);
        addRow(formPanel, gbc, row++, "Name:", nameField);
        addRow(formPanel, gbc, row++, "Email:", emailField);
        addRow(formPanel, gbc, row++, "Department:", departmentBox);
        addRow(formPanel, gbc, row++, "Gender:", genderPanel);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttons.add(registerButton);
        buttons.add(clearButton);
        buttons.add(deleteButton);
        formPanel.add(buttons, gbc);

        return formPanel;
    }

    private void addRow(JPanel panel, GridBagConstraints gbc, int row, String label, Component component) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(component, gbc);
    }

    private void addStudent() {
        String studentId = idField.getText().trim();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String department = (String) departmentBox.getSelectedItem();
        String gender = getSelectedGender();

        if (studentId.isEmpty() || name.isEmpty() || email.isEmpty() || gender == null) {
            JOptionPane.showMessageDialog(this,
                    "Please fill all required fields.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            JOptionPane.showMessageDialog(this,
                    "Please provide a valid email address.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        tableModel.addRow(new Object[]{studentId, name, email, department, gender});
        clearForm();
        JOptionPane.showMessageDialog(this,
                "Student registered successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private String getSelectedGender() {
        if (maleButton.isSelected()) {
            return maleButton.getText();
        }
        if (femaleButton.isSelected()) {
            return femaleButton.getText();
        }
        if (otherButton.isSelected()) {
            return otherButton.getText();
        }
        return null;
    }

    private void clearForm() {
        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        departmentBox.setSelectedIndex(0);
        maleButton.setSelected(false);
        femaleButton.setSelected(false);
        otherButton.setSelected(false);
        idField.requestFocus();
    }

    private void deleteSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a student row to delete.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        tableModel.removeRow(selectedRow);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentRegistrationApp app = new StudentRegistrationApp();
            app.setVisible(true);
        });
    }
}
