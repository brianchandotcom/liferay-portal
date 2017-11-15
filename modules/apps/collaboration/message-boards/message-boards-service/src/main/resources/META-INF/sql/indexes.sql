create index IX_69951A25 on MBBan (banUserId);
create unique index IX_8ABC4E3B on MBBan (groupId, banUserId);
create index IX_48814BBA on MBBan (userId);
create index IX_4F841574 on MBBan (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_2A3B68F6 on MBBan (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_BFEB984F on MBMailingList (active_);
create unique index IX_76CE9CDD on MBMailingList (groupId, categoryId);
create index IX_FC61676E on MBMailingList (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_E858F170 on MBMailingList (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_9168E2C9 on MBStatsUser (groupId, userId);
create index IX_847F92B5 on MBStatsUser (userId);