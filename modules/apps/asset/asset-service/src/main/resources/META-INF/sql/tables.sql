create table AssetEntryUsage (
	uuid_ VARCHAR(75) null,
	assetEntryUsageId LONG not null primary key,
	groupId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	assetEntryId LONG,
	plid LONG,
	containerType LONG,
	containerKey VARCHAR(75) null,
	type_ INTEGER,
	lastPublishDate DATE null
);