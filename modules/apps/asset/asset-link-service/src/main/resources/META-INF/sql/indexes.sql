create index IX_9BB95D26 on AssetLink (entryId1, ctCollectionId);
create index IX_97B1F7F on AssetLink (entryId1, entryId2, ctCollectionId);
create unique index IX_7FC555F2 on AssetLink (entryId1, entryId2, type_, ctCollectionId);
create index IX_F75CBE6B on AssetLink (entryId1, type_, ctCollectionId);
create index IX_6963BEE7 on AssetLink (entryId2, ctCollectionId);
create index IX_F936118A on AssetLink (entryId2, type_, ctCollectionId);