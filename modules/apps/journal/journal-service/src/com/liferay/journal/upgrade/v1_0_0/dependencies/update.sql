alter table JournalArticleImage add companyId LONG;

alter table JournalArticleResource add companyId LONG;

create index IX_CC7576C7 on JournalArticleResource (uuid_, companyId);

alter table JournalFolder add restrictionType INTEGER;

create table JournalFolders_DDMStructures (
	structureId LONG not null,
	folderId LONG not null,
	companyId LONG not null,
	primary key (structureId, folderId, companyId)
);

COMMIT_TRANSACTION;