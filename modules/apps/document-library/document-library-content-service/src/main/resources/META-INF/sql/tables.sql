create table DLContent (
	contentId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	repositoryId LONG,
	path_ VARCHAR(255) null,
	version VARCHAR(75) null,
	data_ BLOB,
	size_ LONG
);