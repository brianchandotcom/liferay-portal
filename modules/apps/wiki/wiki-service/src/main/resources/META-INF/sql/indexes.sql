create index IX_BA986105 on WikiNode (externalReferenceCode[$COLUMN_LENGTH:75$]);
create unique index IX_A83A2D0F on WikiNode (groupId, name[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_23325358 on WikiNode (groupId, status);
create index IX_7F41314A on WikiNode (status, companyId);
create index IX_6C112D7C on WikiNode (uuid_[$COLUMN_LENGTH:75$]);

create index IX_B65BBC83 on WikiPage (companyId);
create index IX_8DBCF518 on WikiPage (externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_A2001730 on WikiPage (format[$COLUMN_LENGTH:75$]);
create index IX_65E84AF4 on WikiPage (nodeId, head, parentTitle[$COLUMN_LENGTH:255$]);
create index IX_64CCB282 on WikiPage (nodeId, head, redirectTitle[$COLUMN_LENGTH:255$]);
create index IX_E30FDBD1 on WikiPage (nodeId, head, resourcePrimKey);
create index IX_B2C73C6C on WikiPage (nodeId, head, title[$COLUMN_LENGTH:255$], groupId);
create index IX_46EEF3C8 on WikiPage (nodeId, parentTitle[$COLUMN_LENGTH:255$]);
create index IX_1ECC7656 on WikiPage (nodeId, redirectTitle[$COLUMN_LENGTH:255$]);
create unique index IX_5AD01561 on WikiPage (nodeId, resourcePrimKey, version, ctCollectionId);
create index IX_6F9E3908 on WikiPage (nodeId, status, groupId, userId);
create index IX_E730F01C on WikiPage (nodeId, status, head, parentTitle[$COLUMN_LENGTH:255$], groupId);
create index IX_861DF55C on WikiPage (nodeId, status, head, redirectTitle[$COLUMN_LENGTH:255$]);
create index IX_D1F6BA7F on WikiPage (nodeId, status, resourcePrimKey);
create index IX_4A2D62C on WikiPage (nodeId, status, title[$COLUMN_LENGTH:255$]);
create index IX_EF476996 on WikiPage (nodeId, status, userId);
create unique index IX_149A1ED4 on WikiPage (nodeId, title[$COLUMN_LENGTH:255$], version, ctCollectionId);
create index IX_85E7CC76 on WikiPage (resourcePrimKey);
create index IX_5D2E2B50 on WikiPage (status, resourcePrimKey);
create index IX_9C0E478F on WikiPage (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_574104C2 on WikiPageResource (nodeId, title[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_BE898221 on WikiPageResource (uuid_[$COLUMN_LENGTH:75$]);