create index IX_2CD455A1 on AnalyticsAssociation (companyId, associationClassName[$COLUMN_LENGTH:75$], associationClassPK, ctCollectionId);
create index IX_FCC5D87B on AnalyticsAssociation (companyId, associationClassName[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_DAE34A46 on AnalyticsAssociation (companyId, modifiedDate, associationClassName[$COLUMN_LENGTH:75$], ctCollectionId);

create index IX_ECACC4AA on AnalyticsDeleteMessage (companyId, ctCollectionId);
create index IX_85DAF3F5 on AnalyticsDeleteMessage (companyId, modifiedDate, ctCollectionId);

create index IX_E05580DF on AnalyticsMessage (companyId, ctCollectionId);