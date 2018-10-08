create table AssetListEntry (
	uuid_ VARCHAR(75) null,
	assetListEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	typeSettings TEXT null,
	title VARCHAR(75) null,
	type_ INTEGER,
	lastPublishDate DATE null
);

create table AssetListEntryAssetEntryRel (
	uuid_ VARCHAR(75) null,
	assetListEntryAssetEntryRelId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	assetListEntryId LONG,
	assetEntryId LONG,
	position INTEGER,
	lastPublishDate DATE null
);

create table AssetListEntryUsage (
	uuid_ VARCHAR(75) null,
	assetListEntryUsageId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	assetListEntryId LONG,
	classNameId LONG,
	classPK LONG,
	portletId VARCHAR(200) null,
	lastPublishDate DATE null
);