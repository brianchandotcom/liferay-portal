create index IX_48CB043 on AccountEntry (companyId, status);

create index IX_A16FB71D on AccountEntryUserRel (accountEntryId, userId);
create index IX_1EACF71D on AccountEntryUserRel (userId, accountEntryId);