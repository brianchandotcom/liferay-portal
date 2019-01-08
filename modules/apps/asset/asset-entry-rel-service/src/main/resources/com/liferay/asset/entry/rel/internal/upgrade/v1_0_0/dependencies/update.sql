create table AssetEntryAssetCategoryRel (
	assetEntryAssetCategoryRelId LONG not null primary key,
	assetEntryId LONG,
	assetCategoryId LONG,
	priority INTEGER
);

create index IX_19EC1746 on AssetEntryAssetCategoryRel (assetCategoryId);
create index IX_E597E5D5 on AssetEntryAssetCategoryRel (assetEntryId, assetCategoryId);

COMMIT_TRANSACTION;