create table ChangeTrackingCollection (
	changeTrackingCollectionId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description VARCHAR(75) null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table ChangeTrackingEntry (
	changeTrackingEntryId LONG not null primary key,
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
	changeTrackingCollectionId LONG not null,
	changeTrackingEntryId LONG not null,
	primary key (changeTrackingCollectionId, changeTrackingEntryId)
);