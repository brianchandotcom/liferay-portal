create index IX_2DE6935C on CommerceMLForecastAlertEntry (companyId, commerceAccountId, status, relativeChange);
create index IX_D398D120 on CommerceMLForecastAlertEntry (companyId, commerceAccountId, timestamp);
create index IX_794DAF43 on CommerceMLForecastAlertEntry (uuid_[$COLUMN_LENGTH:75$]);