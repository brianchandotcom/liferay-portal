## ListUtil

Use `ListUtil.toList` to simplify code, when possible:

```java
addUsers(ListUtil.toList(UserLocalServiceUtil.getUser(userId)));
```

Instead of

```java
List<User> users = new ArrayList<>();

users.add(UserLocalServiceUtil.getUser(userId));

addUsers(users);
```