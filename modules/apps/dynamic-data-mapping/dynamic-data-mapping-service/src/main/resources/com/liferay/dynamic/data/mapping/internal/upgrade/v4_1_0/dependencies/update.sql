create index IX_5378BAAD on DDMField (companyId, fieldType[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_582EBFF1 on DDMField (storageId, ctCollectionId);
create unique index IX_1BB20E75 on DDMField (storageId, instanceId[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_5C0B8AE5 on DDMField (structureVersionId, ctCollectionId);

create index IX_52703248 on DDMFieldAttribute (attributeName[$COLUMN_LENGTH:255$], smallAttributeValue[$COLUMN_LENGTH:255$], ctCollectionId);
create unique index IX_22EEBF0C on DDMFieldAttribute (fieldId, attributeName[$COLUMN_LENGTH:255$], languageId[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_EC62446F on DDMFieldAttribute (storageId, ctCollectionId);
create index IX_1E90C536 on DDMFieldAttribute (storageId, languageId[$COLUMN_LENGTH:75$], ctCollectionId);