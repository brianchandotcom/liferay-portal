create table SchemaEntry (
	mvccVersion LONG default 0 not null,
	entryId LONG not null primary key,
	companyId LONG
);