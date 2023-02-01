## ServiceComponentRuntimeCheck

Methods `disableComponent()` and `enableComponent()` from `ServiceComponentRuntime`
are async operations that return `Promise` objects. Calling `getValue()` from
those promise objects allows us to wait for those operations to sync/finish.
While syncing promises from both operations may not be necessary in production
code, it is necessary in tests to avoid race conditions. Thus, tests must sync
promises from enable/disable operations by executing `getValue()`.

### Example

Incorrect:

```java
import org.osgi.service.component.runtime.ServiceComponentRuntime;

ServiceComponentRuntime serviceComponentRuntime = getServiceComponentRuntime();

serviceComponentRuntime.enableComponent(componentDescriptionDTO);
```

Correct:

```java
import org.osgi.service.component.runtime.ServiceComponentRuntime;
import org.osgi.util.promise.Promise;

ServiceComponentRuntime serviceComponentRuntime = getServiceComponentRuntime();

Promise<?> promise = serviceComponentRuntime.enableComponent(
	componentDescriptionDTO);

promise.getValue();
```