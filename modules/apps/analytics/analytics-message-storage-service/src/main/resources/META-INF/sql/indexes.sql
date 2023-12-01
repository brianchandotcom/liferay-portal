create index IX_F25E6543 on AnalyticsAssociation (companyId, associationClassName[$COLUMN_LENGTH:75$], associationClassPK);
create index IX_6431FFA8 on AnalyticsAssociation (companyId, associationClassName[$COLUMN_LENGTH:75$], modifiedDate);

create index IX_3BF42B97 on AnalyticsDeleteMessage (companyId, modifiedDate);

create index IX_3A69CC81 on AnalyticsMessage (companyId);