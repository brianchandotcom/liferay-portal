create index IX_D3F478C on SharingEntry (className[$COLUMN_LENGTH:75$], classPK);
create index IX_DA712A60 on SharingEntry (fromUserId);
create unique index IX_FA15DF61 on SharingEntry (toUserId, className[$COLUMN_LENGTH:75$], classPK);
create index IX_E7A29E50 on SharingEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_5EDE78D2 on SharingEntry (uuid_[$COLUMN_LENGTH:75$], groupId);