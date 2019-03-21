## Lambda

When the lambda expression contains a single return statement, it can be
simplified:

```java
expectedRolesStream.filter(role -> !excludedRoleNames.contains(role.getName()));
```

Instead of

```java
expectedRolesStream.filter(
    role -> {
        return !excludedRoleNames.contains(role.getName());
    });
```