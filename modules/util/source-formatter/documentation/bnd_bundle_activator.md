## BND Bundle information

If the `bnd.bnd` file contains a `Bundle-Activator` header, the value for
`Bundle-Activator` should end with `BundleActivator` and match the
`Bundle-SymbolicName`.

### Example

```
Bundle-Activator: com.liferay.portal.configuration.persistence.internal.activator.ConfigurationPersistenceImplBundleActivator
Bundle-SymbolicName: com.liferay.portal.configuration.persistence.impl
```