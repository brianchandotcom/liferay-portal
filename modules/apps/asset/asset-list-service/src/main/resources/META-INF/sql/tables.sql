create table AssetListEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	assetListEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	assetListEntryKey VARCHAR(75) null,
	title VARCHAR(75) null,
	type_ INTEGER,
	lastPublishDate DATE null
);

create table AssetListEntryAssetEntryRel (
	mvccVersion LONG default 0 not null,
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
	segmentsEntryId LONG,
	position INTEGER,
	lastPublishDate DATE null
);

create table AssetListEntrySegmentsEntryRel (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	alEntrySegmentsEntryRelId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	assetListEntryId LONG,
	segmentsEntryId LONG,
	typeSettings TEXT null,
	lastPublishDate DATE null
);

create table AssetListEntryUsage (
	mvccVersion LONG default 0 not null,
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