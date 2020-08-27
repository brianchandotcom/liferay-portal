create index IX_72E3390C on CommerceMLForecastAlertEntry (commerceAccountId);
create index IX_7100F5C on CommerceMLForecastAlertEntry (companyId, commerceAccountId, relativeChange, status);
create index IX_2DE6935C on CommerceMLForecastAlertEntry (companyId, commerceAccountId, status, relativeChange);
create index IX_D398D120 on CommerceMLForecastAlertEntry (companyId, commerceAccountId, timestamp);
create index IX_F5973D05 on CommerceMLForecastAlertEntry (uuid_[$COLUMN_LENGTH:75$], companyId);