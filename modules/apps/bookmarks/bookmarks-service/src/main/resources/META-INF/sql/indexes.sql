create index IX_CC03A68B on BookmarksEntry (companyId, ctCollectionId);
create index IX_46DADC71 on BookmarksEntry (companyId, status, ctCollectionId);
create index IX_4D414E6A on BookmarksEntry (groupId, folderId, ctCollectionId);
create index IX_71E3550 on BookmarksEntry (groupId, folderId, status, ctCollectionId);
create index IX_899D6433 on BookmarksEntry (groupId, status, ctCollectionId);
create index IX_7635600A on BookmarksEntry (groupId, userId, folderId, status, ctCollectionId);
create index IX_1A2DCF6D on BookmarksEntry (groupId, userId, status, ctCollectionId);
create index IX_C25D34AD on BookmarksEntry (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_CCF9FE97 on BookmarksEntry (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_CEADBEEF on BookmarksEntry (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_57E46E35 on BookmarksFolder (companyId, ctCollectionId);
create index IX_9A765A1B on BookmarksFolder (companyId, status, ctCollectionId);
create index IX_EA45BA77 on BookmarksFolder (groupId, ctCollectionId);
create index IX_62F6F01E on BookmarksFolder (groupId, parentFolderId, ctCollectionId);
create index IX_E1C0E304 on BookmarksFolder (groupId, parentFolderId, status, ctCollectionId);
create index IX_1FE659C3 on BookmarksFolder (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_58F52B41 on BookmarksFolder (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_65493185 on BookmarksFolder (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);