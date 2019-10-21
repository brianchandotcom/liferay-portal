create unique index IX_2CEE588F on DEDataDefinitionFieldLink (classNameId, classPK, ddmStructureId, fieldName[$COLUMN_LENGTH:75$]);
create index IX_7BAE5B6E on DEDataDefinitionFieldLink (classNameId, ddmStructureId, fieldName[$COLUMN_LENGTH:75$]);
create index IX_9442C171 on DEDataDefinitionFieldLink (ddmStructureId);
create index IX_5145BB70 on DEDataDefinitionFieldLink (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_AAE65DF2 on DEDataDefinitionFieldLink (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_1C932689 on DEDataListView (ddmStructureId);
create index IX_FA1639C7 on DEDataListView (groupId, companyId, ddmStructureId);
create index IX_7113A88 on DEDataListView (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_3336C30A on DEDataListView (uuid_[$COLUMN_LENGTH:75$], groupId);