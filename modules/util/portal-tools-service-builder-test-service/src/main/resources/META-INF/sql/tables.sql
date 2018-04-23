create table BigDecimalEntry (
	bigDecimalEntryId LONG not null primary key,
	bigDecimalValue DECIMAL(30, 16) null
);

create table VersionedEntry (
	mvccVersion LONG default 0 not null,
	versionedEntryId LONG not null primary key,
	groupId LONG,
	headId LONG,
	defaultLanguageId VARCHAR(75) null
);

create table VersionedEntryContent (
	mvccVersion LONG default 0 not null,
	versionedEntryContentId LONG not null primary key,
	versionedEntryId LONG,
	languageId VARCHAR(75) null,
	content VARCHAR(75) null,
	headId LONG
);

create table VersionedEntryContentVersion (
	versionedEntryContentVersionId LONG not null primary key,
	version INTEGER,
	versionedEntryContentId LONG,
	versionedEntryId LONG,
	languageId VARCHAR(75) null,
	content VARCHAR(75) null
);

create table VersionedEntryVersion (
	versionedEntryVersionId LONG not null primary key,
	version INTEGER,
	versionedEntryId LONG,
	groupId LONG,
	defaultLanguageId VARCHAR(75) null
);