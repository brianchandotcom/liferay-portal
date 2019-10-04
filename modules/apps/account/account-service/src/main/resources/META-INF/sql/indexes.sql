create index IX_48CB043 on AccountEntry (companyId, status);

create index IX_ED720A80 on AccountEntryUserRel (accountEntryId, accountUserId);
create index IX_4EA60AB4 on AccountEntryUserRel (accountUserId);