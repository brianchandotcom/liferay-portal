create table CTermEntryLocalization (
	mvccVersion LONG default 0 not null,
	cTermEntryLocalizationId LONG not null primary key,
	companyId LONG,
	commerceTermEntryId LONG,
	languageId VARCHAR(75) null,
	description TEXT null,
	label VARCHAR(75) null
);

create table CommerceTermEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	externalReferenceCode VARCHAR(75) null,
	defaultLanguageId VARCHAR(75) null,
	commerceTermEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	active_ BOOLEAN,
	displayDate DATE null,
	expirationDate DATE null,
	name VARCHAR(75) null,
	priority DOUBLE,
	type_ VARCHAR(75) null,
	typeSettings VARCHAR(75) null,
	lastPublishDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table CommerceTermEntryRel (
	mvccVersion LONG default 0 not null,
	commerceTermEntryRelId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	commerceTermEntryId LONG
);