create table ChangeCollection (
	changeCollectionId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description VARCHAR(75) null
);

create table ChangeEntry (
	changeEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	resourcePrimKey LONG
);

create table Collections_Entries (
	companyId LONG not null,
	changeCollectionId LONG not null,
	changeEntryId LONG not null,
	primary key (changeCollectionId, changeEntryId)
);