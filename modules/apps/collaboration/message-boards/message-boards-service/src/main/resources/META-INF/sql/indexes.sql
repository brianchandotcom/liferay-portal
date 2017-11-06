create unique index IX_9168E2C9 on MBStatsUser (groupId, userId);
create index IX_847F92B5 on MBStatsUser (userId);

create index IX_8CB0A24A on MBThreadFlag (threadId);
create unique index IX_33781904 on MBThreadFlag (userId, threadId);
create index IX_DCE308C5 on MBThreadFlag (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_FEB0FC87 on MBThreadFlag (uuid_[$COLUMN_LENGTH:75$], groupId);