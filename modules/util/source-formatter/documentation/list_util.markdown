## ListUtil

Use `ListUtil.fromArray` to simplify code, when possible:

```java
addUsers(ListUtil.fromArray(UserLocalServiceUtil.getUser(userId)));
```

Instead of

```java
List<User> users = new ArrayList<>();

users.add(UserLocalServiceUtil.getUser(userId));

addUsers(users);
```