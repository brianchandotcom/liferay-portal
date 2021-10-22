create table RemoteAppEntryLocalization (
	mvccVersion LONG default 0 not null,
	remoteAppEntryLocalizationId LONG not null primary key,
	companyId LONG,
	remoteAppEntryId LONG,
	languageId VARCHAR(75) null,
	description TEXT null,
	name VARCHAR(75) null
);

create unique index IX_46476743 on RemoteAppEntryLocalization (remoteAppEntryId, languageId[$COLUMN_LENGTH:75$]);

COMMIT_TRANSACTION;

