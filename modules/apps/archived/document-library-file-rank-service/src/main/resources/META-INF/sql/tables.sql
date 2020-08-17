create table DLFileRank (
	mvccVersion LONG default 0 not null,
	fileRankId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	fileEntryId LONG,
	active_ BOOLEAN
);