create table CTCollection (
	ctCollectionId LONG not null primary key,
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

create table CTCollection_CTEntryAggregate (
	companyId LONG not null,
	ctCollectionId LONG not null,
	ctEntryAggregateId LONG not null,
	primary key (ctCollectionId, ctEntryAggregateId)
);

create table CTCollections_CTEntries (
	companyId LONG not null,
	ctCollectionId LONG not null,
	ctEntryId LONG not null,
	primary key (ctCollectionId, ctEntryId)
);

create table CTEntry (
	ctEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	resourcePrimKey LONG,
	changeType INTEGER,
	status INTEGER
);

create table CTEntryAggregate (
	ctEntryAggregateId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	ownerCTEntryId LONG
);

create table CTEntryAggregates_CTEntries (
	companyId LONG not null,
	ctEntryId LONG not null,
	ctEntryAggregateId LONG not null,
	primary key (ctEntryId, ctEntryAggregateId)
);

create table CTEntryBag (
	ctEntryBagId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	ownerCTEntryId LONG,
	ctCollectionId LONG
);

create table CTEntryBags_CTEntries (
	companyId LONG not null,
	ctEntryId LONG not null,
	ctEntryBagId LONG not null,
	primary key (ctEntryId, ctEntryBagId)
);

create table CTProcess (
	ctProcessId LONG not null primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	ctCollectionId LONG,
	backgroundTaskId LONG
);