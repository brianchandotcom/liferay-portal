create index IX_66102211 on CommerceCurrency (groupId, active_);
create index IX_C38368E0 on CommerceCurrency (groupId, primary_, active_);
create index IX_7C490A66 on CommerceCurrency (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_113CF268 on CommerceCurrency (uuid_[$COLUMN_LENGTH:75$], groupId);