create index IX_8009037E on CTCollections_CTEntries (changeTrackingCollectionId);
create index IX_FAA16F0C on CTCollections_CTEntries (changeTrackingEntryId);
create index IX_AFC3725E on CTCollections_CTEntries (companyId);

create unique index IX_7618AA1C on ChangeTrackingCollection (companyId, name[$COLUMN_LENGTH:75$]);

create index IX_DCBF02A6 on ChangeTrackingEntry (classNameId, classPK);
create index IX_38CBC56A on ChangeTrackingEntry (resourcePrimKey);