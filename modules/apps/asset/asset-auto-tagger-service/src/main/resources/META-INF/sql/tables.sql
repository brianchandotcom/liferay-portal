create table AssetAutoTaggerEntry (
	mvccVersion LONG default 0 not null,
	assetAutoTaggerEntryId LONG not null,
	groupId LONG,
	companyId LONG not null,
	createDate DATE null,
	modifiedDate DATE null,
	assetEntryId LONG,
	assetTagId LONG,
	primary key (assetAutoTaggerEntryId, companyId)
);