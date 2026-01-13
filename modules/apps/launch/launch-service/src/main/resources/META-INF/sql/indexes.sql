create unique index IX_F33C55AE on LaunchEntry (classNameId, classPK, classVersion[$COLUMN_LENGTH:75$]);
create unique index IX_4F6F61F0 on LaunchEntry (externalReferenceCode[$COLUMN_LENGTH:75$], companyId);
create index IX_B56D8695 on LaunchEntry (launchSetId);
create index IX_4A8501AF on LaunchEntry (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_157FB26A on LaunchSet (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_79106559 on LaunchSet (companyId, status);
create index IX_1A8F7EAD on LaunchSet (companyId, userId);
create index IX_8A4BCC7F on LaunchSet (uuid_[$COLUMN_LENGTH:75$]);