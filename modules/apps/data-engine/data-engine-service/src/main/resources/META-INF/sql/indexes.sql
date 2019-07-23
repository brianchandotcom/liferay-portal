create unique index IX_2CEE588F on DEDataDefinitionFieldLink (classNameId, classPK, ddmStructureId, fieldName);
create unique index IX_AAE65DF2 on DEDataDefinitionFieldLink (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_FA1639C7 on DEDataListView (groupId, companyId, ddmStructureId);
create index IX_7113A88 on DEDataListView (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_3336C30A on DEDataListView (uuid_[$COLUMN_LENGTH:75$], groupId);