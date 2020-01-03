create table DLFileRank (
	mvccVersion LONG default 0 not null,
	fileRankId LONG not null,
	groupId LONG,
	companyId LONG not null,
	userId LONG,
	createDate DATE null,
	fileEntryId LONG,
	active_ BOOLEAN,
	primary key (fileRankId, companyId)
);