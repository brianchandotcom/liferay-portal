create index IX_867C5A9 on BigDecimalEntry (bigDecimalValue);

create index IX_60161569 on VersionedEntry (groupId);
create unique index IX_AAA6F330 on VersionedEntry (headId);

create unique index IX_B26F94B3 on VersionedEntryContent (headId);
create unique index IX_7CE51C55 on VersionedEntryContent (versionedEntryId, languageId[$COLUMN_LENGTH:75$]);

create unique index IX_D849DC5F on VersionedEntryContentVersion (versionedEntryContentId, version);
create unique index IX_2C699F17 on VersionedEntryContentVersion (versionedEntryId, languageId[$COLUMN_LENGTH:75$], version);
create index IX_62E8C07E on VersionedEntryContentVersion (versionedEntryId, version);

create index IX_D2594361 on VersionedEntryVersion (groupId, version);
create unique index IX_B51BCCBB on VersionedEntryVersion (versionedEntryId, version);