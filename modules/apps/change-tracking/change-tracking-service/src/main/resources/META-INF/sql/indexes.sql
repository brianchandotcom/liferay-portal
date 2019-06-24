create unique index IX_A0CFE092 on CTCollection (companyId, name[$COLUMN_LENGTH:75$]);

create unique index IX_295C418C on CTEntry (ctCollectionId, modelClassNameId, modelClassPK);
create index IX_CE8C7174 on CTEntry (ctCollectionId, modelClassNameId, status);
create index IX_BA657F81 on CTEntry (ctCollectionId, modelResourcePrimKey, status);
create index IX_20AB084B on CTEntry (ctCollectionId, status);
create index IX_88A1512E on CTEntry (modelClassNameId, modelClassPK);

create index IX_4F01443F on CTEntryAggregate (ctCollectionId, ownerCTEntryId);
create index IX_49B471E1 on CTEntryAggregate (ownerCTEntryId);

create index IX_5C08EBAD on CTEntryAggregates_CTEntries (companyId);
create index IX_4E879A48 on CTEntryAggregates_CTEntries (ctEntryAggregateId);

create index IX_7523B0A4 on CTProcess (companyId);
create index IX_B4859762 on CTProcess (ctCollectionId);
create index IX_5F9B5D3E on CTProcess (userId);