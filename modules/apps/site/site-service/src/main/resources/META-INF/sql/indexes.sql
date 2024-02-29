create unique index IX_FF899B2F on SiteFriendlyURL (companyId, friendlyURL[$COLUMN_LENGTH:75$]);
create unique index IX_7A3B7A2C on SiteFriendlyURL (companyId, groupId, languageId[$COLUMN_LENGTH:75$]);
create unique index IX_508DB72D on SiteFriendlyURL (groupId, uuid_[$COLUMN_LENGTH:75$]);