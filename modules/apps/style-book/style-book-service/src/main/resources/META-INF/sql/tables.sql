create table StyleBookEntry (
	mvccVersion LONG default 0 not null,
	headId LONG,
	head BOOLEAN,
	styleBookEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	defaultStyleBookEntry BOOLEAN,
	name VARCHAR(75) null,
	previewFileEntryId LONG,
	styleBookEntryKey VARCHAR(75) null,
	tokensValues TEXT null
);

create table StyleBookEntryVersion (
	styleBookEntryVersionId LONG not null primary key,
	version INTEGER,
	styleBookEntryId LONG,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	defaultStyleBookEntry BOOLEAN,
	name VARCHAR(75) null,
	previewFileEntryId LONG,
	styleBookEntryKey VARCHAR(75) null,
	tokensValues TEXT null
);