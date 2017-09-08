create index IX_868E8C82 on AMImageEntry (companyId, configurationUuid[$COLUMN_LENGTH:75$]);
create unique index IX_C1EE916F on AMImageEntry (configurationUuid[$COLUMN_LENGTH:75$], fileVersionId);
create index IX_E879919E on AMImageEntry (fileVersionId);
create index IX_65AB1EA1 on AMImageEntry (groupId);
create index IX_257F1DDD on AMImageEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_A0FF779F on AMImageEntry (uuid_[$COLUMN_LENGTH:75$], groupId);