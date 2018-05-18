create table BigDecimalEntry (
	bigDecimalEntryId LONG not null primary key,
	bigDecimalValue DECIMAL(30, 16) null
);

create table LVEntry (
	mvccVersion LONG default 0 not null,
	lvEntryId LONG not null primary key,
	groupId LONG,
	headId LONG,
	defaultLanguageId VARCHAR(75) null
);

create table LVEntryLocalization (
	mvccVersion LONG default 0 not null,
	lvEntryLocalizationId LONG not null primary key,
	lvEntryId LONG,
	languageId VARCHAR(75) null,
	title VARCHAR(75) null,
	content VARCHAR(75) null,
	headId LONG
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
	lvEntryId LONG,
	groupId LONG,
	defaultLanguageId VARCHAR(75) null
);

create table LocalizedEntry (
	localizedEntryId LONG not null primary key,
	defaultLanguageId VARCHAR(75) null
);

create table LocalizedEntryLocalization (
	mvccVersion LONG default 0 not null,
	localizedEntryLocalizationId LONG not null primary key,
	localizedEntryId LONG,
	languageId VARCHAR(75) null,
	title VARCHAR(75) null,
	content VARCHAR(75) null
);

create table VersionedEntry (
	mvccVersion LONG default 0 not null,
	versionedEntryId LONG not null primary key,
	groupId LONG,
	headId LONG
);

create table VersionedEntryVersion (
	versionedEntryVersionId LONG not null primary key,
	version INTEGER,
	versionedEntryId LONG,
	groupId LONG
);