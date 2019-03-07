create unique index IX_A0CFE092 on CTCollection (companyId, name[$COLUMN_LENGTH:75$]);

create index IX_15C20AD4 on CTCollection_CTEntryAggregate (companyId);
create index IX_ACB81B32 on CTCollection_CTEntryAggregate (ctCollectionId);
create index IX_90D19701 on CTCollection_CTEntryAggregate (ctEntryAggregateId);

create index IX_AFC3725E on CTCollections_CTEntries (companyId);
create index IX_D9FBD9E8 on CTCollections_CTEntries (ctCollectionId);
create index IX_6EA8BE62 on CTCollections_CTEntries (ctEntryId);

create unique index IX_776391C on CTEntry (classNameId, classPK);
create index IX_C57DDD34 on CTEntry (resourcePrimKey);

create index IX_49B471E1 on CTEntryAggregate (ownerCTEntryId);

create index IX_5C08EBAD on CTEntryAggregates_CTEntries (companyId);
create index IX_4E879A48 on CTEntryAggregates_CTEntries (ctEntryAggregateId);
create index IX_1AEE37B1 on CTEntryAggregates_CTEntries (ctEntryId);

create index IX_14B1CD88 on CTEntryBag (ownerCTEntryId, ctCollectionId);

create index IX_60232A36 on CTEntryBags_CTEntries (companyId);
create index IX_E5BD89E8 on CTEntryBags_CTEntries (ctEntryBagId);
create index IX_1F08763A on CTEntryBags_CTEntries (ctEntryId);

create index IX_7523B0A4 on CTProcess (companyId);
create index IX_B4859762 on CTProcess (ctCollectionId);
create index IX_5F9B5D3E on CTProcess (userId);