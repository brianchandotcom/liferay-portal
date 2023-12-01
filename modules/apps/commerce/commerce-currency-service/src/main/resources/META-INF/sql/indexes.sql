create index IX_5667FA64 on CommerceCurrency (companyId, active_, primary_);
create unique index IX_2127F18C on CommerceCurrency (companyId, code_[$COLUMN_LENGTH:75$]);
create index IX_EE967482 on CommerceCurrency (uuid_[$COLUMN_LENGTH:75$]);