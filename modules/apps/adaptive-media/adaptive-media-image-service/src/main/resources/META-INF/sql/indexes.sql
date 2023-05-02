create index IX_1BA41EE0 on AMImageEntry (companyId, configurationUuid[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_8EB944BD on AMImageEntry (companyId, ctCollectionId);
create index IX_24B252D6 on AMImageEntry (configurationUuid[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_EBBEA9CD on AMImageEntry (configurationUuid[$COLUMN_LENGTH:75$], fileVersionId, ctCollectionId);
create index IX_6B796BFC on AMImageEntry (fileVersionId, ctCollectionId);
create index IX_E7EE92FF on AMImageEntry (groupId, ctCollectionId);
create index IX_BDB49A3B on AMImageEntry (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_668A85C9 on AMImageEntry (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_681D2FFD on AMImageEntry (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);