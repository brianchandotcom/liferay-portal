create index IX_99628DD1 on DEDataDefinitionFieldLink (classNameId, classPK);
create unique index IX_78BC4050 on DEDataDefinitionFieldLink (ctCollectionId, uuid_[$COLUMN_LENGTH:75$], groupId);
create unique index IX_B0B67DC9 on DEDataDefinitionFieldLink (ddmStructureId, classNameId, fieldName[$COLUMN_LENGTH:255$], classPK, ctCollectionId);
create index IX_E931B304 on DEDataDefinitionFieldLink (ddmStructureId, fieldName[$COLUMN_LENGTH:255$]);

create index IX_1C932689 on DEDataListView (ddmStructureId);
create index IX_6C111CBD on DEDataListView (groupId, ddmStructureId, companyId);
create unique index IX_D13C29BC on DEDataListView (groupId, uuid_[$COLUMN_LENGTH:75$], ctCollectionId);