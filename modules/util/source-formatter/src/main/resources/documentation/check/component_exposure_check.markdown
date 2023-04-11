## ComponentExposureCheck

Static components provide a false sense of safety,
as they are accessible even before activation and after deactivation.

### Examples

Incorrect:

```java
@Component(service = {})
public class CTCollectionPermission {

    public static boolean contains(
            PermissionChecker permissionChecker, CTCollection ctCollection,
            String actionId)
        throws PortalException {

        return _ctCollectionModelResourcePermission.contains(
            permissionChecker, ctCollection, actionId);
    }

...

    private static ModelResourcePermission<CTCollection>
        _ctCollectionModelResourcePermission;
}
```

Correct:

```java
import com.liferay.osgi.util.service.Snapshot;

public class CTCollectionPermission {

    public static boolean contains(
            PermissionChecker permissionChecker, CTCollection ctCollection,
            String actionId)
        throws PortalException {

        ModelResourcePermission<CTCollection> modelResourcePermission =
            _ctCollectionModelResourcePermissionSnapshot.get();

        return modelResourcePermission.contains(
            permissionChecker, ctCollection, actionId);
    }

...

    private static final Snapshot<ModelResourcePermission<CTCollection>>
        _ctCollectionModelResourcePermissionSnapshot = new Snapshot<>(
            CTCollectionPermission.class,
            Snapshot.cast(ModelResourcePermission.class),
            "(model.class.name=com.liferay.change.tracking.model." +
                "CTCollection)");
}
```