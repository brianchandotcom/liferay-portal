create index IX_6DDD0C5F on AMImageEntry (companyId);
create index IX_51249CB0 on AMImageEntry (configurationUuid[$COLUMN_LENGTH:75$], companyId);
create unique index IX_EBBEA9CD on AMImageEntry (configurationUuid[$COLUMN_LENGTH:75$], fileVersionId, ctCollectionId);
create unique index IX_4076C51 on AMImageEntry (ctCollectionId, groupId, uuid_[$COLUMN_LENGTH:75$]);
create index IX_E879919E on AMImageEntry (fileVersionId);