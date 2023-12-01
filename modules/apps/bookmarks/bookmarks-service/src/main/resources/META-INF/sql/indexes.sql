create index IX_1F90CA2D on BookmarksEntry (companyId);
create index IX_5200100C on BookmarksEntry (groupId, folderId);
create index IX_69D78EAC on BookmarksEntry (groupId, status, folderId, userId);
create index IX_37518B0F on BookmarksEntry (groupId, status, userId);
create index IX_F16A8A87 on BookmarksEntry (status, companyId);
create index IX_B670BA39 on BookmarksEntry (uuid_[$COLUMN_LENGTH:75$]);

create index IX_2ABA25D7 on BookmarksFolder (companyId);
create index IX_8C7A9C31 on BookmarksFolder (status, companyId);
create index IX_A254EA9A on BookmarksFolder (status, groupId, parentFolderId);
create index IX_451E7AE3 on BookmarksFolder (uuid_[$COLUMN_LENGTH:75$]);