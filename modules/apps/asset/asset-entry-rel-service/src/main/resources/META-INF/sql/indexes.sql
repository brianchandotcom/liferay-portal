create index IX_19EC1746 on AssetEntryAssetCategoryRel (assetCategoryId);
create unique index IX_7DEE7233 on AssetEntryAssetCategoryRel (assetEntryId, assetCategoryId, ctCollectionId);