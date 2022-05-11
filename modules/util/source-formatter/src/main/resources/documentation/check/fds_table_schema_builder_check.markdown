## FDSTableSchemaBuilderCheck

Use `add` instead of `addFDSTableSchemaField`.

### Examples

Incorrect:

```java
FDSTableSchemaBuilder fdsTableSchemaBuilder =
	_fdsTableSchemaBuilderFactory.create();

fdsTableSchemaBuilder.addFDSTableSchemaField("id", "id");
fdsTableSchemaBuilder.addFDSTableSchemaField("title", "title");
fdsTableSchemaBuilder.addFDSTableSchemaField(
	"description", "description");
fdsTableSchemaBuilder.addFDSTableSchemaField("date", "date");

FDSTableSchemaField statusFDSTableSchemaField =
	fdsTableSchemaBuilder.addFDSTableSchemaField("status", "status");

statusFDSTableSchemaField.setContentRenderer("status");

fdsTableSchemaBuilder.addFDSTableSchemaField("creator.name", "author");

return fdsTableSchemaBuilder.build();
```

Correct:

```java
FDSTableSchemaBuilder fdsTableSchemaBuilder =
	_fdsTableSchemaBuilderFactory.create();

return fdsTableSchemaBuilder.add(
	"id", "id"
).add(
	"title", "title"
).add(
	"description", "description"
).add(
	"date", "date"
).add(
	"status", "status",
	fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
		"status")
).add(
	"creator.name", "author"
).build();
```