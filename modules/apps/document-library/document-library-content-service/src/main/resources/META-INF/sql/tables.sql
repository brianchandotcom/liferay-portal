create table DLContent (
	mvccVersion LONG default 0 not null,
	contentId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	repositoryId LONG,
	path_ VARCHAR(255) null,
	version VARCHAR(75) null,
	data_ BLOB,
	size_ LONG
);