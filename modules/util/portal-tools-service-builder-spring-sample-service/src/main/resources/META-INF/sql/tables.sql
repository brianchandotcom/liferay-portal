create table SpringEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	springEntryId LONG not null primary key,
	companyId LONG,
	createDate DATE null
);