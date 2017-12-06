## BND packageinfo

The `packageinfo` file is required when it is defined in `Export-Package`
property of the `bnd.bnd` file.

#### Example

When the module's `bnd.bnd` file contains the following `Export-Package`
property:

```
Export-Package:\
	com.liferay.blogs.configuration
```

The `packageinfo` file in the following location is required.

```
src\main\resources\com\liferay\blogs\configuration\packageinfo
```