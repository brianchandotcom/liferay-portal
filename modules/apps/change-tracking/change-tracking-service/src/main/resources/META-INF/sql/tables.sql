create table CTCollection (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG not null,
	companyId LONG not null,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description VARCHAR(200) null,
	status INTEGER,
	statusByUserId LONG,
	statusDate DATE null,
	primary key (ctCollectionId, companyId)
);

create table CTEntry (
	mvccVersion LONG default 0 not null,
	ctEntryId LONG not null,
	companyId LONG not null,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	ctCollectionId LONG,
	modelClassNameId LONG,
	modelClassPK LONG,
	modelMvccVersion LONG,
	changeType INTEGER,
	primary key (ctEntryId, companyId)
);

create table CTMessage (
	mvccVersion LONG default 0 not null,
	ctMessageId LONG not null primary key,
	ctCollectionId LONG,
	messageContent TEXT null
);

create table CTPreferences (
	mvccVersion LONG default 0 not null,
	ctPreferencesId LONG not null,
	companyId LONG not null,
	userId LONG,
	ctCollectionId LONG,
	confirmationEnabled BOOLEAN,
	primary key (ctPreferencesId, companyId)
);

create table CTProcess (
	mvccVersion LONG default 0 not null,
	ctProcessId LONG not null,
	companyId LONG not null,
	userId LONG,
	createDate DATE null,
	ctCollectionId LONG,
	backgroundTaskId LONG,
	primary key (ctProcessId, companyId)
);