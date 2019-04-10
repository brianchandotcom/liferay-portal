create table AssetEntryUsage (
	uuid_ VARCHAR(75) null,
	assetEntryUsageId LONG not null primary key,
	groupId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	assetEntryId LONG,
	containerType LONG,
	containerKey VARCHAR(200) null,
	plid LONG,
	type_ INTEGER,
	lastPublishDate DATE null
);