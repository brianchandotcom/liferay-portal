create table StyleBookEntry (
	mvccVersion LONG default 0 not null,
	styleBookEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	name VARCHAR(75) null,
	styleBookEntryKey VARCHAR(75) null,
	previewFileEntryId LONG
);