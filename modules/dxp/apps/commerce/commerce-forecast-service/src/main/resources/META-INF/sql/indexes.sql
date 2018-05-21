create unique index IX_E2103380 on CommerceForecastEntry (companyId, period, target, customerId, sku[$COLUMN_LENGTH:75$]);

create unique index IX_C0C235BC on CommerceForecastValue (commerceForecastEntryId, time_);