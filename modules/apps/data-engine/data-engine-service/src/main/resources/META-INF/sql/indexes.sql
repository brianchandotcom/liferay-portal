create index IX_99628DD1 on DEDataDefinitionFieldLink (classNameId, classPK);
create unique index IX_B0B67DC9 on DEDataDefinitionFieldLink (ddmStructureId, classNameId, fieldName[$COLUMN_LENGTH:255$], classPK, ctCollectionId);
create index IX_E931B304 on DEDataDefinitionFieldLink (ddmStructureId, fieldName[$COLUMN_LENGTH:255$]);
create index IX_255E05B8 on DEDataDefinitionFieldLink (uuid_[$COLUMN_LENGTH:75$]);

create index IX_81B6947 on DEDataListView (ddmStructureId, groupId, companyId);
create index IX_40CB05A0 on DEDataListView (uuid_[$COLUMN_LENGTH:75$]);