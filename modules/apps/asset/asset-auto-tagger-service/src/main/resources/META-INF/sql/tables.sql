create table AssetAutoTaggerEntry (
	mvccVersion LONG default 0 not null,
	assetAutoTaggerEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	assetEntryId LONG,
	assetTagId LONG
);