create table AssetAutoTaggerEntry (
	assetAutoTaggerEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	assetEntryId LONG,
	assetTagId LONG
);