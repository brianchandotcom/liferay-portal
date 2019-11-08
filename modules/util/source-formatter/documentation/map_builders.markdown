## MapBuilders

Use `*MapBuilder` when possible.

### Example

Incorrect:

```java
public Map<String, Object> getUserMap(User user) {
    Map<String, Object> userMap = new HashMap<>();

    userMap.put("addresses", user.getAddresses());
    userMap.put("emailAddresses", user.getEmailAddress());
    userMap.put("fullName", user.getFullName());
    userMap.put("locale", user.getLocale());
    userMap.put("jobTitle", user.getJobTitle());

    return userMap;
}
```

Correct:

```java
public Map<String, Object> getUserMap(User user) {
    return HashMapBuilder.<String, Object>put(
        "addresses, user.getAddresses()
    ).put(
        "emailAddresses, user.getEmailAddress()
    ).put(
        "fullName, user.getFullName()
    ).put(
        "locale, user.getLocale()
    ).put(
        "jobTitle, user.getJobTitle()
    ).build();
}
```