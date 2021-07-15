create table STRankingsEntry (
	mvccVersion LONG default 0 not null,
	STRankingsEntryId LONG not null primary key,
	companyId LONG
);