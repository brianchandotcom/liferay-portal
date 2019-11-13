create table BigDecimalEntries_LVEntries (
	companyId LONG not null,
	bigDecimalEntryId LONG not null,
	lvEntryId LONG not null,
	primary key (bigDecimalEntryId, lvEntryId)
);

create table BigDecimalEntry (
	bigDecimalEntryId LONG not null primary key,
	companyId LONG,
	bigDecimalValue DECIMAL(30, 16) null
);

create table EagerBlobEntity (
	uuid_ VARCHAR(75) null,
	eagerBlobEntityId LONG not null primary key,
	groupId LONG,
	blob_ BLOB
);

create table LVEntries_BigDecimalEntries (
	companyId LONG not null,
	bigDecimalEntryId LONG not null,
	lvEntryId LONG not null,
	primary key (bigDecimalEntryId, lvEntryId)
);

create table LVEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	headId LONG,
	head BOOLEAN,
	defaultLanguageId VARCHAR(75) null,
	lvEntryId LONG not null primary key,
	companyId LONG,
	groupId LONG,
	uniqueGroupKey VARCHAR(75) null
);

create table LVEntryLocalization (
	mvccVersion LONG default 0 not null,
	headId LONG,
	head BOOLEAN,
	lvEntryLocalizationId LONG not null primary key,
	companyId LONG,
	lvEntryId LONG,
	languageId VARCHAR(75) null,
	title VARCHAR(75) null,
	content VARCHAR(75) null
);

create table LVEntryLocalizationVersion (
	lvEntryLocalizationVersionId LONG not null primary key,
	version INTEGER,
	lvEntryLocalizationId LONG,
	companyId LONG,
	lvEntryId LONG,
	languageId VARCHAR(75) null,
	title VARCHAR(75) null,
	content VARCHAR(75) null
);

create table LVEntryVersion (
	lvEntryVersionId LONG not null primary key,
	version INTEGER,
	uuid_ VARCHAR(75) null,
	defaultLanguageId VARCHAR(75) null,
	lvEntryId LONG,
	companyId LONG,
	groupId LONG,
	uniqueGroupKey VARCHAR(75) null
);

create table LazyBlobEntity (
	uuid_ VARCHAR(75) null,
	lazyBlobEntityId LONG not null primary key,
	groupId LONG,
	blob1 BLOB,
	blob2 BLOB
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

create table NestedSetsTreeEntry (
	nestedSetsTreeEntryId LONG not null primary key,
	groupId LONG,
	parentNestedSetsTreeEntryId LONG,
	leftNestedSetsTreeEntryId LONG,
	rightNestedSetsTreeEntryId LONG
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
	head BOOLEAN,
	versionedEntryId LONG not null primary key,
	groupId LONG
);

create table VersionedEntryVersion (
	versionedEntryVersionId LONG not null primary key,
	version INTEGER,
	versionedEntryId LONG,
	groupId LONG
);