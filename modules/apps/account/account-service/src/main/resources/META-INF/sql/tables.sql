create table AccountEntry (
	accountEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentAccountEntryId LONG,
	name VARCHAR(100) null,
	description STRING null,
	logoId LONG,
	status INTEGER
);
create table AccountEntryUserRel (
	accountEntryUserRelId LONG not null,
	companyId LONG,
	userId LONG not null,
	accountEntryId LONG not null,
	primary key (accountEntryUserRelId, userId, accountEntryId)
);