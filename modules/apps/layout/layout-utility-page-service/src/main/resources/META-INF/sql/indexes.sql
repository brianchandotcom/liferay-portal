create unique index IX_CA8014F0 on LayoutUtilityPageEntry (ctCollectionId, plid);
create unique index IX_373A213C on LayoutUtilityPageEntry (groupId, ctCollectionId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create unique index IX_28103BB3 on LayoutUtilityPageEntry (groupId, ctCollectionId, uuid_[$COLUMN_LENGTH:75$]);
create unique index IX_2089F80F on LayoutUtilityPageEntry (groupId, type_[$COLUMN_LENGTH:75$], ctCollectionId, name[$COLUMN_LENGTH:75$]);
create index IX_DCFECA00 on LayoutUtilityPageEntry (groupId, type_[$COLUMN_LENGTH:75$], defaultLayoutUtilityPageEntry);
create index IX_B0D10431 on LayoutUtilityPageEntry (groupId, type_[$COLUMN_LENGTH:75$], name[$COLUMN_LENGTH:75$]);
create index IX_997885CD on LayoutUtilityPageEntry (uuid_[$COLUMN_LENGTH:75$]);