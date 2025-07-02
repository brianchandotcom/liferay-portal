create table TrashEntry (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	entryId LONG not null,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	classNameId LONG,
	classPK LONG,
	systemEventSetKey LONG,
	typeSettings TEXT null,
	status INTEGER,
	primary key (entryId, ctCollectionId)
);

create table TrashVersion (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	versionId LONG not null,
	companyId LONG,
	entryId LONG,
	classNameId LONG,
	classPK LONG,
	typeSettings TEXT null,
	status INTEGER,
	primary key (versionId, ctCollectionId)
);