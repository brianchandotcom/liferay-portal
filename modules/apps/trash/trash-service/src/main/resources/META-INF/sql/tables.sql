create table TrashEntry (
	mvccVersion LONG default 0 not null,
	entryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	classNameId LONG,
	classPK LONG,
	systemEventSetKey LONG,
	typeSettings TEXT null,
	status INTEGER
);

create table TrashVersion (
	mvccVersion LONG default 0 not null,
	versionId LONG not null primary key,
	companyId LONG,
	entryId LONG,
	classNameId LONG,
	classPK LONG,
	typeSettings TEXT null,
	status INTEGER
);