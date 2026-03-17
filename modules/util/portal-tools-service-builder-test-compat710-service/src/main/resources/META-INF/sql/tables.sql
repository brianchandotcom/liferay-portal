create table ArrayableEntry (
	arrayableEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	integer_ INTEGER,
	name VARCHAR(75) null,
	type_ VARCHAR(75) null
);

create table BasicEntry (
	basicEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description VARCHAR(75) null
);

create table CacheDisabledEntry (
	cacheDisabledEntryId LONG not null primary key,
	name VARCHAR(75) null
);

create table ConvertNullEntry (
	convertNullEntryId LONG not null primary key,
	name VARCHAR(75) null
);

create table ERCCompanyEntry (
	uuid_ VARCHAR(75) null,
	externalReferenceCode VARCHAR(75) null,
	ercCompanyEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	column1 INTEGER
);

create table ERCGroupEntry (
	uuid_ VARCHAR(75) null,
	externalReferenceCode VARCHAR(75) null,
	ercGroupEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG
);

create table ERCVersionedEntry (
	uuid_ VARCHAR(75) null,
	externalReferenceCode VARCHAR(75) null,
	ercVersionedEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG
);

create table EagerBlobEntry (
	uuid_ VARCHAR(75) null,
	eagerBlobEntryId LONG not null primary key,
	groupId LONG,
	blob_ BLOB
);

create table LazyBlobEntry (
	uuid_ VARCHAR(75) null,
	lazyBlobEntryId LONG not null primary key,
	groupId LONG,
	blob1 BLOB,
	blob2 BLOB
);

create table LikeFinderEntry (
	likeFinderEntryId LONG not null primary key,
	companyId LONG,
	ownerId LONG,
	ownerType INTEGER,
	portletId VARCHAR(75) null
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

create table MVCCEntry (
	mvccVersion LONG default 0 not null,
	mvccEntryId LONG not null primary key,
	companyId LONG,
	name VARCHAR(75) null
);

create table ManyColumnsEntry (
	manyColumnsEntryId LONG not null primary key,
	column1 INTEGER,
	column2 INTEGER,
	column3 INTEGER,
	column4 INTEGER,
	column5 INTEGER,
	column6 INTEGER,
	column7 INTEGER,
	column8 INTEGER,
	column9 INTEGER,
	column10 INTEGER,
	column11 INTEGER,
	column12 INTEGER,
	column13 INTEGER,
	column14 INTEGER,
	column15 INTEGER,
	column16 INTEGER,
	column17 INTEGER,
	column18 INTEGER,
	column19 INTEGER,
	column20 INTEGER,
	column21 INTEGER,
	column22 INTEGER,
	column23 INTEGER,
	column24 INTEGER,
	column25 INTEGER,
	column26 INTEGER,
	column27 INTEGER,
	column28 INTEGER,
	column29 INTEGER,
	column30 INTEGER,
	column31 INTEGER,
	column32 INTEGER,
	column33 INTEGER,
	column34 INTEGER,
	column35 INTEGER,
	column36 INTEGER,
	column37 INTEGER,
	column38 INTEGER,
	column39 INTEGER,
	column40 INTEGER,
	column41 INTEGER,
	column42 INTEGER,
	column43 INTEGER,
	column44 INTEGER,
	column45 INTEGER,
	column46 INTEGER,
	column47 INTEGER,
	column48 INTEGER,
	column49 INTEGER,
	column50 INTEGER,
	column51 INTEGER,
	column52 INTEGER,
	column53 INTEGER,
	column54 INTEGER,
	column55 INTEGER,
	column56 INTEGER,
	column57 INTEGER,
	column58 INTEGER,
	column59 INTEGER,
	column60 INTEGER,
	column61 INTEGER,
	column62 INTEGER,
	column63 INTEGER,
	column64 INTEGER
);

create table MappingEntries_BasicEntries (
	companyId LONG not null,
	basicEntryId LONG not null,
	mappingEntryId LONG not null,
	primary key (basicEntryId, mappingEntryId)
);

create table MappingEntry (
	mappingEntryId LONG not null primary key,
	companyId LONG,
	name VARCHAR(75) null
);

create table TrashEntry (
	trashEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null
);

create table TreeEntry (
	treeEntryId LONG not null primary key,
	groupId LONG,
	parentTreeEntryId LONG,
	leftTreeEntryId LONG,
	rightTreeEntryId LONG
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

create table WhereClauseEntry (
	whereClauseEntryId LONG not null primary key,
	name VARCHAR(75) null,
	nickname VARCHAR(75) null
);