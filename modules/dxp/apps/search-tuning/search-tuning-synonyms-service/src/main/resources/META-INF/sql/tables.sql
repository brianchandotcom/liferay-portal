create table STSynonymsEntry (
	mvccVersion LONG default 0 not null,
	STSynonymsEntryId LONG not null primary key,
	companyId LONG
);