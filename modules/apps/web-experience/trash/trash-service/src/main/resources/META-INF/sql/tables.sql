create table TrashEntry (
	entryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	classNameId LONG,
	classPK LONG,
	systemEventSetKey LONG,
	typeSettings VARCHAR(75) null,
	status INTEGER
);

create table TrashVersion (
	versionId LONG not null primary key,
	companyId LONG,
	entryId LONG,
	classNameId LONG,
	classPK LONG,
	typeSettings VARCHAR(75) null,
	status INTEGER
);