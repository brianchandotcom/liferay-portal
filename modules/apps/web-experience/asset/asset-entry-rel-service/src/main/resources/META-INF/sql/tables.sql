create table AssetEntryAssetCategoryRel (
	assetEntryAssetCategoryRelId LONG not null primary key,
	assetEntryId LONG,
	assetCategoryId LONG,
	priority INTEGER
);

create table AssetEntryAssetDisplayPageRel (
	assetEntryAssetDisplayPageId LONG not null primary key,
	assetEntryId LONG,
	assetDisplayPageId LONG
);