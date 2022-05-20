create index IX_44D73BB3 on AnalyticsAssociationChange (companyId, associationClassName[$COLUMN_LENGTH:75$], associationClassPK);
create index IX_AA23AE58 on AnalyticsAssociationChange (companyId, modifiedDate, associationClassName[$COLUMN_LENGTH:75$]);

create index IX_3BF42B97 on AnalyticsDeleteMessage (companyId, modifiedDate);

create index IX_3A69CC81 on AnalyticsMessage (companyId);