create unique index IX_9C5C8C9A on CPDefinitionVirtualSetting (CPDefinitionId);
create index IX_F1182A3F on CPDefinitionVirtualSetting (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_8ED43481 on CPDefinitionVirtualSetting (uuid_[$COLUMN_LENGTH:75$], groupId);