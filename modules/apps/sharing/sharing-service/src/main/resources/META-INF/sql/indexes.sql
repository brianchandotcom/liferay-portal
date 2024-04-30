create index IX_1ED300B1 on SharingEntry (classNameId, classPK);
create unique index IX_E0D3AF7C on SharingEntry (classNameId, toUserId, classPK);
create index IX_8E0359AC on SharingEntry (classNameId, userId);
create index IX_1E35B88D on SharingEntry (expirationDate);
create index IX_F066C0CE on SharingEntry (groupId);
create index IX_C024CFB1 on SharingEntry (toUserId);
create index IX_EA2FF796 on SharingEntry (userId);
create unique index IX_5EDE78D2 on SharingEntry (uuid_[$COLUMN_LENGTH:75$], groupId);