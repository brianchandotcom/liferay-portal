create table AssetEntryUsage (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	assetEntryUsageId LONG not null,
	groupId LONG,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	assetEntryId LONG,
	containerType LONG,
	containerKey VARCHAR(200) null,
	plid LONG,
	type_ INTEGER,
	lastPublishDate DATE null,
	primary key (assetEntryUsageId, ctCollectionId)
);