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

---

Inline using lambda, when possible.

### Example

Incorrect:

```java
public Map<String, Object> getMap() {
    Company company = _getCompany();
    User user = _getUser();

    return HashMapBuilder.<String, Object>put(
        "companyGroup", company.getGroup()
    ).put(
        "userGroup", user.getGroup()
    ).build();
}
```

Correct:

```java
public Map<String, Object> getMap() {
    return HashMapBuilder.<String, Object>put(
        "companyGroup",
        () -> {
             Company company = _getCompany();

             return company.getGroup();
        }
    ).put(
        "userGroup",
        () -> {
             User user = _getUser();

             return user.getGroup();
        }
    ).build();
}
```