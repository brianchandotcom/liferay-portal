## @see Annotation

Do not nest annotations inside a `@see` annotation.

### Example

Incorrect:

```java
/**
 * @see {@link com.liferay.portal.servlet.MainServlet#initPlugins}
 */
```

Correct:

```java
/**
 * @see com.liferay.portal.servlet.MainServlet#initPlugins
 */
```