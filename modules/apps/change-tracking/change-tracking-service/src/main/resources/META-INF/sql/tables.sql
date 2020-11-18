create table CTAutoResolutionInfo (
	mvccVersion LONG default 0 not null,
	ctAutoResolutionInfoId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	ctCollectionId LONG,
	modelClassNameId LONG,
	sourceModelClassPK LONG,
	targetModelClassPK LONG,
	conflictIdentifier VARCHAR(500) null
);

create table CTCollection (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG not null primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description VARCHAR(200) null,
	schemaVersionId LONG,
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
	companyId LONG,
	ctCollectionId LONG,
	messageContent TEXT null
);

create table CTPreferences (
	mvccVersion LONG default 0 not null,
	ctPreferencesId LONG not null primary key,
	companyId LONG,
	userId LONG,
	ctCollectionId LONG,
	previousCtCollectionId LONG,
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

create table CTSchemaVersion (
	mvccVersion LONG default 0 not null,
	schemaVersionId LONG not null primary key,
	companyId LONG,
	schemaContext TEXT null
);