alter table JournalFolder add restrictionType INTEGER;

create table JournalFolders_DDMStructures (
	structureId LONG not null,
	folderId LONG not null,
	primary key (structureId, folderId)
);

COMMIT_TRANSACTION;