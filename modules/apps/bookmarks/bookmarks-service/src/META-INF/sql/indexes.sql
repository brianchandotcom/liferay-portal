create index IX_276C8C13 on BookmarksEntry (companyId, status);
create index IX_146382F2 on BookmarksEntry (groupId, folderId, status);
create index IX_416AD7D5 on BookmarksEntry (groupId, status);
create index IX_C78B61AC on BookmarksEntry (groupId, userId, folderId, status);
create index IX_9D9CF70F on BookmarksEntry (groupId, userId, status);
create index IX_E848278F on BookmarksEntry (resourceBlockId);
create index IX_89BEDC4F on BookmarksEntry (uuid_, companyId);
create unique index IX_EAA02A91 on BookmarksEntry (uuid_, groupId);

create index IX_C27C9DBD on BookmarksFolder (companyId, status);
create index IX_D16018A6 on BookmarksFolder (groupId, parentFolderId, status);
create index IX_28A49BB9 on BookmarksFolder (resourceBlockId);
create index IX_54F0ED65 on BookmarksFolder (uuid_, companyId);
create unique index IX_DC2F8927 on BookmarksFolder (uuid_, groupId);