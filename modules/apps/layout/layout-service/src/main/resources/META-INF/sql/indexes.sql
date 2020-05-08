create unique index IX_63EAFC82 on LayoutClassedModelUsage (classNameId, classPK, containerKey[$COLUMN_LENGTH:200$], containerType, plid, ctCollectionId);
create index IX_2644723E on LayoutClassedModelUsage (classNameId, classPK, ctCollectionId);
create index IX_C03C3E53 on LayoutClassedModelUsage (classNameId, classPK, type_, ctCollectionId);
create index IX_F1220AB7 on LayoutClassedModelUsage (containerKey[$COLUMN_LENGTH:200$], containerType, plid, ctCollectionId);
create index IX_A3F4F834 on LayoutClassedModelUsage (plid, ctCollectionId);
create index IX_B0FEE15D on LayoutClassedModelUsage (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_ACA55FE7 on LayoutClassedModelUsage (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_8A32D79F on LayoutClassedModelUsage (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);