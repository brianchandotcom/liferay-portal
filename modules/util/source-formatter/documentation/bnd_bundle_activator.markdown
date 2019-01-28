## BND Bundle information

If `bnd.bnd` contains `Bundle-Activator` header, the `Bundle-Activator` should
end with `BundleActivator`, and match the `Bundle-SymbolicName`.

### Example

    Bundle-Activator: com.liferay.portal.configuration.persistence.internal.activator.ConfigurationPersistenceImplBundleActivator
    Bundle-SymbolicName: com.liferay.portal.configuration.persistence.impl