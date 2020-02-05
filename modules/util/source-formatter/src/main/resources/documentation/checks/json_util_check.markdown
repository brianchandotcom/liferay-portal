## JSONUtilCheck

Use `JSONUtil.put` or `JSONUtil.putAll`, when possible.

### Examples

```java
JSONObject jsonObject = JSONUtil.put("Hello", "World");
```

Instead of

```java
JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

jsonObject.put("Hello", "World");
```

---

```java
JSONObject jsonObject = JSONUtil.put(
    "Hello", "World"
).put(
    "World", "Hello"
);
```

Instead of

```java
JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

jsonObject.put("Hello", "World");
jsonObject.put("World", "Hello");
```

---

```java
JSONArray jsonArray = JSONUtil.put(user);
```

Instead of

```java
JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

jsonArray.put(user);
```

---

```java
JSONArray jsonArray = JSONUtil.putAll(user1, user2, user3, user4);
```

Instead of

```java
JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

jsonArray.put(user1);
jsonArray.put(user2);
jsonArray.put(user3);
jsonArray.put(user4);
```