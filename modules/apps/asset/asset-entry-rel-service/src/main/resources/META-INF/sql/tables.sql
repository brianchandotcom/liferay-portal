create table AssetEntryAssetCategoryRel (
	mvccVersion LONG default 0 not null,
	assetEntryAssetCategoryRelId LONG not null,
	companyId LONG not null,
	assetEntryId LONG,
	assetCategoryId LONG,
	priority INTEGER,
	primary key (assetEntryAssetCategoryRelId, companyId)
);