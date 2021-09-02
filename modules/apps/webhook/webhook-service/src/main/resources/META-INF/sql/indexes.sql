create unique index IX_203322A6 on WebhookEntry (companyId, name[$COLUMN_LENGTH:75$]);
create index IX_12D38D15 on WebhookEntry (uuid_[$COLUMN_LENGTH:75$], companyId);