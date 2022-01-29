## ExceptionCheck

Avoid cases where a method is throwing specific exception, but all the methods
that are calling this method are throwing a generic `java.lang.Exception`. The
additional information that the specific exception provides is passed unnecessarily.

### Example

Incorrect:

```java
public void validateUser(User user) throws Exception {
    _validateEmailAddress(user.getEmailAddress());
    _validateUserId(user.getUserId());
}

private void _validateEmailAddress(String emailAddress)
    throws InvalidEmailAddressException {

    if (!Validator.isEmailAddress(emailAddress)) {
        throw new InvalidEmailAddressException();
    }
}

private void _validateUserId(long userId) throws InvalidUserIdException {
    if (userId < 0) {
        throw new InvalidUserIdException();
    }
}
```

Correct:

**Solution 1:**

Catch and throw the specific exceptions properly for the `caller` method:

```java
public void validateUser(User user)
    throws InvalidEmailAddressException, InvalidUserIdException {

    _validateEmailAddress(user.getEmailAddress());
    _validateUserId(user.getUserId());
}

private void _validateEmailAddress(String emailAddress)
    throws InvalidEmailAddressException {

    if (!Validator.isEmailAddress(emailAddress)) {
        throw new InvalidEmailAddressException();
    }
}

private void _validateUserId(long userId) throws InvalidUserIdException {
    if (userId < 0) {
        throw new InvalidUserIdException();
    }
}
```

**Solution 2:**

Throw `java.lang.Exception` for the `called` method:

```java
public void validateUser(User user) throws Exception {
    _validateEmailAddress(user.getEmailAddress());
    _validateUserId(user.getUserId());
}

private void _validateEmailAddress(String emailAddress) throws Exception {
    if (!Validator.isEmailAddress(emailAddress)) {
        throw new Exception();
    }
}

private void _validateUserId(long userId) throws Exception {
    if (userId < 0) {
        throw new Exception();
    }
}
```