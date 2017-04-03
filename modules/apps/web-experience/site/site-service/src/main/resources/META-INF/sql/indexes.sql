create unique index IX_1641E337 on GroupFriendlyURL (companyId, friendlyURL[$COLUMN_LENGTH:75$]);
create unique index IX_3A4F3A34 on GroupFriendlyURL (companyId, groupId, languageId[$COLUMN_LENGTH:75$]);
create index IX_5F45589F on GroupFriendlyURL (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_1397AE1 on GroupFriendlyURL (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_4A4B2F0F on Site_GroupFriendlyURL (companyId, friendlyURL[$COLUMN_LENGTH:75$]);
create unique index IX_7B9B2E0C on Site_GroupFriendlyURL (companyId, groupId, languageId[$COLUMN_LENGTH:75$]);
create index IX_77CA6677 on Site_GroupFriendlyURL (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_6D81EB9 on Site_GroupFriendlyURL (uuid_[$COLUMN_LENGTH:75$], groupId);