create unique index IX_E2103380 on CommerceForecastEntry (companyId, period, target, customerId, sku[$COLUMN_LENGTH:75$]);

create unique index IX_651A613B on CommerceForecastValue (commerceForecastEntryId, date_);