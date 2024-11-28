create unique index IX_87C75408 on AssetCategoryProperty (categoryId, key_[$COLUMN_LENGTH:255$], ctCollectionId);
create unique index IX_E889D6A0 on AssetCategoryProperty (companyId, ctCollectionId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_52340033 on AssetCategoryProperty (companyId, key_[$COLUMN_LENGTH:255$]);