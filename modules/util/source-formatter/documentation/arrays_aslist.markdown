## Arrays.asList

Use `Arrays.asList` to simplify code, when possible:

```java
addUsers(Arrays.asList(UserLocalServiceUtil.getUser(userId)));
```

Instead of

```java
List<User> users = new ArrayList<>();

users.add(UserLocalServiceUtil.getUser(userId));

addUsers(users);
```