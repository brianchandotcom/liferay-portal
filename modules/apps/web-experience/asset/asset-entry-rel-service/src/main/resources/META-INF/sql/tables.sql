create table AssetEntryAssetCategoryRel (
	assetEntryAssetCategoryRelId LONG not null primary key,
	assetEntryId LONG,
	assetCategoryId LONG,
	priority INTEGER
);

create table AssetEntryDisplayPageRel (
	assetEntryDisplayPageRelId LONG not null primary key,
	assetEntryId LONG,
	displayPageId LONG
);