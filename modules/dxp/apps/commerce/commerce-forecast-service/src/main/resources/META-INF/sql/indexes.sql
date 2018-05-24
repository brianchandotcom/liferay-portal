create unique index IX_E8DA397E on CommerceForecastEntry (companyId, period, target, customerId, CPInstanceId);

create unique index IX_C0C235BC on CommerceForecastValue (commerceForecastEntryId, time_);