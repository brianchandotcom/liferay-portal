create index IX_868E8C82 on AMImageEntry (companyId, configurationUuid[$COLUMN_LENGTH:75$]);
create unique index IX_2F7D8759 on AMImageEntry (configurationUuid[$COLUMN_LENGTH:75$], fileVersionId, companyId);
create index IX_E879919E on AMImageEntry (fileVersionId);
create index IX_65AB1EA1 on AMImageEntry (groupId);
create index IX_257F1DDD on AMImageEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_524FE329 on AMImageEntry (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);