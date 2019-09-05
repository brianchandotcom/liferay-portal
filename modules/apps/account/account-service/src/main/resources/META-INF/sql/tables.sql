create table AccountEntry (
	accountEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentAccountEntryId LONG,
	name VARCHAR(75) null,
	description VARCHAR(75) null,
	logoId LONG,
	status INTEGER
);

create table AccountEntryUserRel (
	accountEntryId LONG not null,
	userId LONG not null,
	companyId LONG,
	primary key (accountEntryId, userId)
);