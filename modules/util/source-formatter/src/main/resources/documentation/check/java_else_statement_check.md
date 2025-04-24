## JavaElseStatementCheck

When the `if` statement ends with `return` statement, using `else` is not needed.

### Example

Incorrect:

```java
public void _activate(User user) {
    if (user.isActive()) {
        return;
    }
    else {
        user.setActive(true);
    }
}
```

Correct:

```java
public void _activate(User user) {
    if (user.isActive()) {
        return;
    }

    user.setActive(true);
}
```