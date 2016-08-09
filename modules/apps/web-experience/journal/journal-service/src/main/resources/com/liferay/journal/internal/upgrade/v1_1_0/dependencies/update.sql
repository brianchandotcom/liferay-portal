create table JournalArticleLocalization (
	articleLocalizationId LONG not null primary key,
	companyId LONG,
	articlePK LONG,
	title VARCHAR(75) null,
	description VARCHAR(75) null,
	languageId VARCHAR(75) null
);

alter table JournalArticle add defaultLanguageId VARCHAR(75) null;

COMMIT_TRANSACTION;