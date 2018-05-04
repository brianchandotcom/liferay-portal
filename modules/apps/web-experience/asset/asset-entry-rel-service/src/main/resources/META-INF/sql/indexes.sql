create index IX_19EC1746 on AssetEntryAssetCategoryRel (assetCategoryId);
create index IX_CB3E2B64 on AssetEntryAssetCategoryRel (assetEntryId);

create unique index IX_A6587BFD on AssetEntryAssetDisplayPageRel (assetEntryId, assetDisplayPageId);