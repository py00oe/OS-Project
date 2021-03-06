package models.bank.logic;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import models.user.logic.User;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Transaction implements Serializable {

    private static final SimpleDateFormat date_format = new SimpleDateFormat("HH:mm:ss.SSS");

    private String completeTime;
    private String startTime;

    private final String transactionUUID;

    private Account account;
    private User user;
    private Double amount;
    private Double balanceAfterTransaction;

    public String getType() {
        return type;
    }

    private String type;
    private String status;

    private Transaction() {
        completeTime = date_format.format(new Date());
        transactionUUID = UUID.randomUUID().toString();
    }

    public Transaction(Account account, User user, Double amount, String type) {
        this();
        this.account = account;
        this.user = user;
        this.amount = amount;
        this.type = type;
        balanceAfterTransaction = account.getBalance();

        if (account.getBalance() - amount < 0) {
            status = "Failed";
        } else {
            status = "Completed";
        }
    }

    public Transaction(Account account, User user, Double amount, String type, Date startTime) {
        this(account, user, amount, type);
        this.startTime = date_format.format(startTime);
    }

    public StringProperty getIdSP() {
        return new SimpleStringProperty(transactionUUID);
    }

    public StringProperty getAccountSP() {
        return new SimpleStringProperty(account.getName());
    }

    public StringProperty getBalanceSP() {
        return new SimpleStringProperty("$" + balanceAfterTransaction);
    }

    public StringProperty getStartTimeSP() {
        return new SimpleStringProperty(startTime);
    }

    public StringProperty getCompleteTimeSP() {
        return new SimpleStringProperty(completeTime);
    }

    public StringProperty getAmountSP() {
        return new SimpleStringProperty("$" + amount);
    }

    public StringProperty getStatusSP() {
        return new SimpleStringProperty(status);
    }

    public StringProperty getTypeSP() {
        return new SimpleStringProperty(type);
    }

    public StringProperty getUserSP() {
        return new SimpleStringProperty(user.getName());
    }

    Account getAccount() {
        return account;
    }

    public User getUser() {
        return user;
    }

    Double getAmount() {
        return amount;
    }

    void completeSuccessful(Account account, Double newAmount, Date newDate) {
        amount = newAmount;
        balanceAfterTransaction = account.getBalance();
        completeTime = date_format.format(newDate);
        this.status = "Completed";
    }

    void completeFail(Account account, Double newAmount, Date newDate) {
        completeSuccessful(account, newAmount, newDate);
        this.status = "Failed";
    }
}

