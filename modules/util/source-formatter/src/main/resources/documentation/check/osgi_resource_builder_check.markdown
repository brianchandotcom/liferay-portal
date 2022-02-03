## OSGiResourceBuilderCheck

In OSGi component, do not call `*Resource.builder`, use the Factory component instead of the static field.

### Example

Incorrect:

```java
DataListViewResource dataListViewResource =
	DataListViewResource.builder(
	).checkPermissions(
		false
	).user(
		contextUser
	).build();
```

Correct:

```java
DataListViewResource.Builder dataListViewResourceBuilder =
	_dataListViewResourceFactory.create();

DataListViewResource dataListViewResource =
	dataListViewResourceBuilder.checkPermissions(
		false
	).user(
		contextUser
	).build();
```

```java
@Reference
private DataListViewResource.Factory _dataListViewResourceFactory;
```

### Exception

`*Resource` package contains `.client.`

#### Example

```java
com.liferay.data.engine.rest.client.resource.v2_0.DataDefinitionResource;
```