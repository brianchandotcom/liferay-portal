create table AssetListEntry (
	uuid_ VARCHAR(75) null,
	defaultLanguageId VARCHAR(75) null,
	assetListEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	type_ INTEGER,
	lastPublishDate DATE null
);

create table AssetListEntryLocalization (
	mvccVersion LONG default 0 not null,
	assetListEntryLocalizationId LONG not null primary key,
	companyId LONG,
	assetListEntryId LONG,
	languageId VARCHAR(75) null,
	title VARCHAR(75) null,
	description VARCHAR(75) null,
	groupId LONG
);