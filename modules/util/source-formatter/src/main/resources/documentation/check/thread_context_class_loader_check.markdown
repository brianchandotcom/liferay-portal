## ThreadContextClassLoaderCheck

Instead of directly swapping the context class loader for the current thread, use the method
`com.liferay.petra.lang.ThreadContextClassLoaderUtil#swap`.

### Example

Incorrect:

```java
Thread currentThread = Thread.currentThread();

ClassLoader contextClassLoader = currentThread.getContextClassLoader();

try {
    currentThread.setContextClassLoader(DiffHtml.class.getClassLoader());

    ...
}
finally {
    currentThread.setContextClassLoader(contextClassLoader);
}
```

Correct:

```java
try (SafeCloseable safeCloseable = ThreadContextClassLoaderUtil.swap(
        DiffHtml.class.getClassLoader())) {

	...
}
```