## ServiceProxyFactoryCheck

Pass the current class as the second parameter when calling method
`ServiceProxyFactory.newServiceTrackedInstance`

### Example

```java
public class HookFactory {

	public static Hook getInstance() {
		return _hook;
	}

	private HookFactory() {
	}

	private static volatile Hook _hook =
		ServiceProxyFactory.newServiceTrackedInstance(
			Hook.class, HookFactory.class, "_hook", false, true);

}
```