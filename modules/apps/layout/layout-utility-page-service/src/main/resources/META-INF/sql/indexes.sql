create index IX_41AE9EE1 on LayoutUtilityPageEntry (groupId, ctCollectionId);
create index IX_56E7F204 on LayoutUtilityPageEntry (groupId, defaultLayoutUtilityPageEntry, type_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_E84DFDB8 on LayoutUtilityPageEntry (groupId, externalReferenceCode[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_D08C3F1 on LayoutUtilityPageEntry (groupId, name[$COLUMN_LENGTH:75$], type_[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_B1A82450 on LayoutUtilityPageEntry (groupId, type_[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_BF95A05E on LayoutUtilityPageEntry (groupId, type_[$COLUMN_LENGTH:75$], defaultLayoutUtilityPageEntry, ctCollectionId);
create index IX_2CA6CC99 on LayoutUtilityPageEntry (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_1740222B on LayoutUtilityPageEntry (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_F81461DB on LayoutUtilityPageEntry (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);