## JavaServiceTrackerFactoryCheck

The method `org.osgi.util.tracker.ServiceTracker.open(java.lang.Class)` is
deprecated because it leaks ServiceTrackers. Instead, use
`org.osgi.util.tracker.ServiceTracker.open(org.osgi.framework.Bundle, java.lang.Class)`.

### Example

Incorrect:

```java
import org.osgi.util.tracker.ServiceTracker;

public class SearchDisplayContextFactoryUtil {

...

    private static final ServiceTracker
        <SearchDisplayContextFactory, SearchDisplayContextFactory>
            _serviceTracker = ServiceTrackerFactory.open(
                SearchDisplayContextFactory.class);

}
```

Correct:

```java
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

public class SearchDisplayContextFactoryUtil {

...

    private static final ServiceTracker
        <SearchDisplayContextFactory, SearchDisplayContextFactory>
            _serviceTracker = ServiceTrackerFactory.open(
                FrameworkUtil.getBundle(SearchDisplayContextFactoryUtil.class),
                SearchDisplayContextFactory.class);

}
```