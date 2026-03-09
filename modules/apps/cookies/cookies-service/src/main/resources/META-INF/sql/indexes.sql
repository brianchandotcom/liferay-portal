create index IX_4DB85A2C on ConsentPreference (expirationDate);
create unique index IX_93D9046C on ConsentPreference (userId, domain[$COLUMN_LENGTH:75$], name[$COLUMN_LENGTH:75$]);