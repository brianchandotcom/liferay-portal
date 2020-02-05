create index IX_57C912B4 on AssetCategoryProperty (categoryId, ctCollectionId);
create unique index IX_87C75408 on AssetCategoryProperty (categoryId, key_[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_83E29FD on AssetCategoryProperty (companyId, ctCollectionId);
create index IX_11761091 on AssetCategoryProperty (companyId, key_[$COLUMN_LENGTH:255$], ctCollectionId);