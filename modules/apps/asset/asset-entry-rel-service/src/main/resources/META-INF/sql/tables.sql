create table AssetEntryAssetCategoryRel (
	mvccVersion LONG default 0 not null,
	assetEntryAssetCategoryRelId LONG not null primary key,
	companyId LONG,
	assetEntryId LONG,
	assetCategoryId LONG,
	priority INTEGER
);