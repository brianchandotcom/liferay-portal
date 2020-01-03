create table AccountEntry (
	mvccVersion LONG default 0 not null,
	accountEntryId LONG not null,
	companyId LONG not null,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentAccountEntryId LONG,
	name VARCHAR(100) null,
	description STRING null,
	domains STRING null,
	logoId LONG,
	status INTEGER,
	primary key (accountEntryId, companyId)
);

create table AccountEntryUserRel (
	mvccVersion LONG default 0 not null,
	accountEntryUserRelId LONG not null,
	companyId LONG not null,
	accountEntryId LONG,
	accountUserId LONG,
	primary key (accountEntryUserRelId, companyId)
);

create table AccountRole (
	mvccVersion LONG default 0 not null,
	accountRoleId LONG not null,
	companyId LONG not null,
	accountEntryId LONG,
	roleId LONG,
	primary key (accountRoleId, companyId)
);