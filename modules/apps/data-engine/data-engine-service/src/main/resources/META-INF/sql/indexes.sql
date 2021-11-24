create index IX_94C1A22F on DEDataDefinitionFieldLink (classNameId, classPK, ctCollectionId);
create unique index IX_998C30ED on DEDataDefinitionFieldLink (classNameId, classPK, ddmStructureId, fieldName[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_F149FA5 on DEDataDefinitionFieldLink (classNameId, ddmStructureId, ctCollectionId);
create index IX_95CC on DEDataDefinitionFieldLink (classNameId, ddmStructureId, fieldName[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_110695CF on DEDataDefinitionFieldLink (ddmStructureId, ctCollectionId);
create index IX_57E0162 on DEDataDefinitionFieldLink (ddmStructureId, fieldName[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_1C2BB1CE on DEDataDefinitionFieldLink (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_8A366C16 on DEDataDefinitionFieldLink (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_AA8B1050 on DEDataDefinitionFieldLink (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_8DD9CAE7 on DEDataListView (ddmStructureId, ctCollectionId);
create index IX_29FAA225 on DEDataListView (groupId, companyId, ddmStructureId, ctCollectionId);
create index IX_B10600E6 on DEDataListView (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_9BB69BFE on DEDataListView (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_275E4568 on DEDataListView (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);