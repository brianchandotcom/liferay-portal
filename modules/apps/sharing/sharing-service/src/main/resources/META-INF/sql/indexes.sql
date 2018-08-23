create index IX_1ED300B1 on SharingEntry (classNameId, classPK);
create index IX_717794B5 on SharingEntry (fromUserId, classNameId, classPK);
create unique index IX_10F1564A on SharingEntry (fromUserId, toUserId, classNameId, classPK);
create index IX_F066C0CE on SharingEntry (groupId);
create index IX_3062F746 on SharingEntry (toUserId, classNameId, classPK);
create index IX_E7A29E50 on SharingEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_5EDE78D2 on SharingEntry (uuid_[$COLUMN_LENGTH:75$], groupId);