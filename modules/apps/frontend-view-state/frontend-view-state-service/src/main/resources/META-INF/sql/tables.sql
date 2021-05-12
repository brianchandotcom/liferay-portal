create table FVSActiveEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	fvsActiveEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	datasetDisplayId VARCHAR(75) null,
	fvsEntryId LONG,
	plid LONG,
	portletId VARCHAR(75) null
);

create table FVSEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	fvsEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	viewState VARCHAR(75) null
);