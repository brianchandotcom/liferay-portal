create index IX_1ED300B1 on SharingEntry (classNameId, classPK);
create index IX_1E35B88D on SharingEntry (expirationDate);
create index IX_F066C0CE on SharingEntry (groupId);
create unique index IX_3062F746 on SharingEntry (toUserId, classNameId, classPK);
create index IX_9A668578 on SharingEntry (userId, classNameId);
create index IX_E7A29E50 on SharingEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_5EDE78D2 on SharingEntry (uuid_[$COLUMN_LENGTH:75$], groupId);