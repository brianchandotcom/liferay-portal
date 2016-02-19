create index IX_6B4FFAC1 on MeetupsEntry (companyId);
create index IX_421F9041 on MeetupsEntry (userId);

create index IX_61A365C6 on MeetupsRegistration (meetupsEntryId, status);
create index IX_E1769B1A on MeetupsRegistration (userId, meetupsEntryId);

create index IX_A23BBE36 on WallEntry (groupId, userId);
create index IX_5B5CB8A8 on WallEntry (userId);