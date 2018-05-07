create unique index IX_FF899B2F on SiteFriendlyURL (companyId, friendlyURL[$COLUMN_LENGTH:75$]);
create unique index IX_7A3B7A2C on SiteFriendlyURL (companyId, groupId, languageId[$COLUMN_LENGTH:75$]);
create index IX_E6D46A97 on SiteFriendlyURL (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_82D4AAD9 on SiteFriendlyURL (uuid_[$COLUMN_LENGTH:75$], groupId);