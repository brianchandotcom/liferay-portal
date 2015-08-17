create index IX_CC7576C7 on JournalArticleResource (uuid_, companyId);

alter table JournalFolder add restrictionType INTEGER;

create table JournalFolders_DDMStructures (
	companyId LONG not null,
	structureId LONG not null,
	folderId LONG not null,
	primary key (structureId, folderId, companyId)
);

COMMIT_TRANSACTION;