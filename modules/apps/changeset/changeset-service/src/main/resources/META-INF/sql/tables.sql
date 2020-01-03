create table ChangesetCollection (
	changesetCollectionId LONG not null,
	groupId LONG,
	companyId LONG not null,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description VARCHAR(75) null,
	primary key (changesetCollectionId, companyId)
);

create table ChangesetEntry (
	changesetEntryId LONG not null,
	groupId LONG,
	companyId LONG not null,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	changesetCollectionId LONG,
	classNameId LONG,
	classPK LONG,
	primary key (changesetEntryId, companyId)
);