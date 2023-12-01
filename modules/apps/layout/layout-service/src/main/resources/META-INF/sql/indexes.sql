create index IX_B041F1F5 on LayoutClassedModelUsage (classNameId, classPK, type_);
create index IX_B51E9567 on LayoutClassedModelUsage (classNameId, companyId, cmExternalReferenceCode[$COLUMN_LENGTH:75$], type_);
create index IX_65B533A0 on LayoutClassedModelUsage (classNameId, containerType, companyId);
create unique index IX_67137E33 on LayoutClassedModelUsage (classNameId, containerType, plid, classPK, cmExternalReferenceCode[$COLUMN_LENGTH:75$], containerKey[$COLUMN_LENGTH:200$], ctCollectionId);
create index IX_F747B9BD on LayoutClassedModelUsage (containerType, plid, containerKey[$COLUMN_LENGTH:200$]);
create index IX_19448DD6 on LayoutClassedModelUsage (plid);
create index IX_2728BB89 on LayoutClassedModelUsage (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_F4B6F839 on LayoutLocalization (plid, languageId[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_F98CCB8B on LayoutLocalization (uuid_[$COLUMN_LENGTH:75$]);