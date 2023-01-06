## ResourcePermissionFactoryCheck

When inside `modules`, avoid retrieving resource permissions using `ModelResourcePermissionFactory.getInstance()`
for `ModelResourcePermission` or `PortletResourcePermissionFactory.getInstance()`
for `PortletResourcePermission`. Instead, use OSGi references (i.e. `@Reference`)
to get resource permissions.

### Example

Incorrect:

```java
private static volatile ModelResourcePermission<DDMStructure>
	_ddmStructureModelResourcePermission =
		ModelResourcePermissionFactory.getInstance(
			DDMStructureServiceImpl.class,
			"_ddmStructureModelResourcePermission", DDMStructure.class);
```

Correct:

```java
@Reference(
	target = "(model.class.name=com.liferay.dynamic.data.mapping.model.DDMStructure)"
)
private ModelResourcePermission<DDMStructure> _ddmStructureModelResourcePermission;
```

### Example

Incorrect:

```java
private static volatile PortletResourcePermission
	_portletResourcePermission =
		PortletResourcePermissionFactory.getInstance(
			CommerceTermEntryServiceImpl.class,
			"_portletResourcePermission",
			CommerceTermEntryConstants.RESOURCE_NAME);
```

Correct:

```java
@Reference(
	target = "(resource.name=" + CommerceTermEntryConstants.RESOURCE_NAME + ")"
)
private PortletResourcePermission _portletResourcePermission; 
```