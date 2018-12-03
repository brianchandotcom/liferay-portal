create table JournalArticleLocalization (
	articleLocalizationId LONG not null primary key,
	companyId LONG,
	articlePK LONG,
	title VARCHAR(400) null,
	description STRING null,
	languageId VARCHAR(75) null
);

create unique index IX_ACF2560A on JournalArticleLocalization (articlePK, languageId[$COLUMN_LENGTH:75$]);

COMMIT_TRANSACTION;