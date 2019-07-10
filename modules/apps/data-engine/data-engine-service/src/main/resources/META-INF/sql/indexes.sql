create unique index IX_41983C6F on DEDataDefinitionFieldLink (classNameId, classPK, DDMStructureId, fieldName);
create unique index IX_AAE65DF2 on DEDataDefinitionFieldLink (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_3B1DF81D on DEListView (groupId, companyId, DDMStructureId);
create index IX_2159723E on DEListView (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_22757040 on DEListView (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_6D18C38 on DERecordQuery (uuid_[$COLUMN_LENGTH:75$]);