create unique index IX_A0CFE092 on CTCollection (companyId, name[$COLUMN_LENGTH:75$]);

create index IX_15C20AD4 on CTCollection_CTEntryAggregate (companyId);
create index IX_90D19701 on CTCollection_CTEntryAggregate (ctEntryAggregateId);

create index IX_AFC3725E on CTCollections_CTEntries (companyId);
create index IX_6EA8BE62 on CTCollections_CTEntries (ctEntryId);

create unique index IX_88A1512E on CTEntry (modelClassNameId, modelClassPK);

create index IX_49B471E1 on CTEntryAggregate (ownerCTEntryId);

create index IX_5C08EBAD on CTEntryAggregates_CTEntries (companyId);
create index IX_4E879A48 on CTEntryAggregates_CTEntries (ctEntryAggregateId);

create index IX_7523B0A4 on CTProcess (companyId);
create index IX_B4859762 on CTProcess (ctCollectionId);
create index IX_5F9B5D3E on CTProcess (userId);