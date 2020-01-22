drop table CTCollection;

drop table CTCollection_CTEntryAggregate;

drop table CTCollections_CTEntries;

drop table CTEntry;

drop table CTEntryAggregate;

drop table CTEntryAggregates_CTEntries;

drop table CTProcess;

create table CTCollection (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG not null primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description VARCHAR(200) null,
	status INTEGER,
	statusByUserId LONG,
	statusDate DATE null
);

create table CTEntry (
	mvccVersion LONG default 0 not null,
	ctEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	ctCollectionId LONG,
	modelClassNameId LONG,
	modelClassPK LONG,
	modelMvccVersion LONG,
	changeType INTEGER
);

create table CTMessage (
	mvccVersion LONG default 0 not null,
	ctMessageId LONG not null primary key,
	ctCollectionId LONG,
	messageContent TEXT null
);

create table CTPreferences (
	mvccVersion LONG default 0 not null,
	ctPreferencesId LONG not null primary key,
	companyId LONG,
	userId LONG,
	ctCollectionId LONG,
	confirmationEnabled BOOLEAN
);

create table CTProcess (
	mvccVersion LONG default 0 not null,
	ctProcessId LONG not null primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	ctCollectionId LONG,
	backgroundTaskId LONG
);

create index IX_8D52E6F9 on CTCollection (companyId, status);

create unique index IX_295C418C on CTEntry (ctCollectionId, modelClassNameId, modelClassPK);

create index IX_9FB742FA on CTMessage (ctCollectionId);

create unique index IX_516E5375 on CTPreferences (companyId, userId);
create index IX_3FECC82B on CTPreferences (ctCollectionId);

create index IX_7523B0A4 on CTProcess (companyId);
create index IX_B4859762 on CTProcess (ctCollectionId);
create index IX_5F9B5D3E on CTProcess (userId);

COMMIT_TRANSACTION;