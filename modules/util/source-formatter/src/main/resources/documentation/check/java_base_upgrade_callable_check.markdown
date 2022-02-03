## JavaBaseUpgradeCallableCheck

When using Callable or Runnable in Upgrade or Verify classes we must use
BaseUpgradeCallable instead. With this, we ensure the CompanyThreadLocal value
is passed to child threads.

The only difference is that we need to override doCall() instead of call().

### Example

Incorrect:

```java
	private class MyCallable implements Callable<Boolean> {

        public MyCallable() {
        }

        @Override
        public Boolean call() {
        }
    }
```

Correct:

```java
	private class MyUpgradeCallable extends BaseUpgradeCallable<Boolean> {

	public MyCallable() {
	}

	@Override
	protected Boolean doCall() {
	}
}
```