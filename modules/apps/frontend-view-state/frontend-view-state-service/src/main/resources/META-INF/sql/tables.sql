create table FVSActiveEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	fvsActiveEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	fvsEntryId LONG,
	clayDataSetDisplayId VARCHAR(75) null,
	plid LONG,
	portletId VARCHAR(200) null
);

create table FVSCustomEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	fvsCustomEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	entityId VARCHAR(200) null,
	fvsEntryId LONG,
	name VARCHAR(200) null
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
	viewState TEXT null
);