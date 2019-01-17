create table BigDecimalEntry (
	bigDecimalEntryId LONG not null primary key,
	bigDecimalValue DECIMAL(30, 16) null
);

create table LVEntry (
	mvccVersion LONG default 0 not null,
	headId LONG,
	defaultLanguageId VARCHAR(75) null,
	lvEntryId LONG not null primary key,
	groupId LONG
);

create table LVEntryLocalization (
	mvccVersion LONG default 0 not null,
	headId LONG,
	lvEntryLocalizationId LONG not null primary key,
	lvEntryId LONG,
	languageId VARCHAR(75) null,
	title VARCHAR(75) null,
	content VARCHAR(75) null
);

create table LVEntryLocalizationVersion (
	lvEntryLocalizationVersionId LONG not null primary key,
	version INTEGER,
	lvEntryLocalizationId LONG,
	lvEntryId LONG,
	languageId VARCHAR(75) null,
	title VARCHAR(75) null,
	content VARCHAR(75) null
);

create table LVEntryVersion (
	lvEntryVersionId LONG not null primary key,
	version INTEGER,
	defaultLanguageId VARCHAR(75) null,
	lvEntryId LONG,
	groupId LONG
);

create table LocalizedEntry (
	defaultLanguageId VARCHAR(75) null,
	localizedEntryId LONG not null primary key
);

create table LocalizedEntryLocalization (
	mvccVersion LONG default 0 not null,
	localizedEntryLocalizationId LONG not null primary key,
	localizedEntryId LONG,
	languageId VARCHAR(75) null,
	title VARCHAR(75) null,
	content VARCHAR(75) null
);

create table UADPartialEntry (
	uadPartialEntryId LONG not null primary key,
	userId LONG,
	userName VARCHAR(75) null,
	message VARCHAR(75) null
);

create table VersionedEntry (
	mvccVersion LONG default 0 not null,
	headId LONG,
	versionedEntryId LONG not null primary key,
	groupId LONG
);

create table VersionedEntryVersion (
	versionedEntryVersionId LONG not null primary key,
	version INTEGER,
	versionedEntryId LONG,
	groupId LONG
);