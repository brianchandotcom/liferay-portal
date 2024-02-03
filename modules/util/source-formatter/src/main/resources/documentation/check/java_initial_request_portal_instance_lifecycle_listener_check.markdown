## JavaInitialRequestPortalInstanceLifecycleListenerCheck

All subclasses of `com.liferay.portal.instance.lifecycle.InitialRequestPortalInstanceLifecycleListener`
must override the `activate(BundleContext bundleContext)` method and call `super.activate(bundleContext)`.

### Example

Incorrect:

```java
import com.liferay.portal.instance.lifecycle.InitialRequestPortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

@Component(service = PortalInstanceLifecycleListener.class)
public class MyInitialRequestPortalInstanceLifecycleListener
	extends InitialRequestPortalInstanceLifecycleListener {

	@Override
	protected void doPortalInstanceRegistered(long companyId) throws Exception {
		doSomething(companyId);
	}

}
```

Correct

```java
import com.liferay.portal.instance.lifecycle.InitialRequestPortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

@Component(service = PortalInstanceLifecycleListener.class)
public class MyInitialRequestPortalInstanceLifecycleListener
	extends InitialRequestPortalInstanceLifecycleListener {

	@Activate
	@Override
	protected void activate(BundleContext bundleContext) {
		super.activate(bundleContext);
	}

	@Override
	protected void doPortalInstanceRegistered(long companyId) throws Exception {
		doSomething(companyId);
	}

}
```