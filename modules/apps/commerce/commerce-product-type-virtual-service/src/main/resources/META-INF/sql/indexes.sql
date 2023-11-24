create index IX_C606354 on CPDVirtualSettingFileEntry (CPDefinitionVirtualSettingId);
create index IX_763742D4 on CPDVirtualSettingFileEntry (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_19B2FD20 on CPDefinitionVirtualSetting (classNameId, classPK);
create index IX_FBDCC249 on CPDefinitionVirtualSetting (uuid_[$COLUMN_LENGTH:75$]);