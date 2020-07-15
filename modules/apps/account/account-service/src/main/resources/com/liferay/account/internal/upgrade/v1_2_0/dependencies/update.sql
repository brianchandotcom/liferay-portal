create table AccountGroupAccountEntryRel (
	mvccVersion LONG default 0 not null,
	AccountGroupAccountEntryRelId LONG not null primary key,
	companyId LONG,
	accountGroupId LONG,
	accountEntryId LONG
);

create index IX_8177283C on AccountGroupAccountEntryRel (accountEntryId);
create index IX_1FACB57D on AccountGroupAccountEntryRel (accountGroupId, accountEntryId);