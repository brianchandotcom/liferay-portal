## CreatingThreadsForDBAccessCheck

Use `CompanyInheritableThreadLocalCallable` to create threads for DB access
when thread logic contains the usage of `CompanyThreadLocal`, `*LocalService`
or `*LocalServiceUtil`.

### Examples

Incorrect:

```java
public void run() throws Exception {

...

    long companyId = CompanyThreadLocal.getNonsystemCompanyId();
    String finalPortletName = portletName;
    String finalPortletId = portletId;

    FutureTask<com.liferay.portal.kernel.model.Portlet> futureTask =
        new FutureTask<>(
            () -> {
                CompanyThreadLocal.setCompanyId(companyId);

                com.liferay.portal.kernel.model.Portlet addedPortletModel =
                    _addingPortlet(
                        serviceReference, portlet, finalPortletName,
                        finalPortletId);

                if (addedPortletModel == null) {
                    _bundleContext.ungetService(serviceReference);
                }

                return addedPortletModel;
            });

...

}
```

Correct:

```java
public void run() throws Exception {

...

    String finalPortletName = portletName;
    String finalPortletId = portletId;

    FutureTask<com.liferay.portal.kernel.model.Portlet> futureTask =
        new FutureTask<>(
            new CompanyInheritableThreadLocalCallable<>(
                () -> {
                    com.liferay.portal.kernel.model.Portlet
                        addedPortletModel = _addingPortlet(
                            serviceReference, portlet, finalPortletName,
                            finalPortletId);

                    if (addedPortletModel == null) {
                        _bundleContext.ungetService(serviceReference);
                    }

                    return addedPortletModel;
                }));

...

}
```