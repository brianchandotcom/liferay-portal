create table Ranking (
	mvccVersion LONG default 0 not null,
	rankingId LONG not null primary key,
	companyId LONG,
	json STRING null
);