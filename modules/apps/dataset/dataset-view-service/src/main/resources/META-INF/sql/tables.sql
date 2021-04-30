create table DatasetViewActiveEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	datasetViewActiveEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	datasetDisplayId VARCHAR(75) null,
	datasetViewStateEntryId LONG,
	plid LONG,
	portletId VARCHAR(75) null
);

create table DatasetViewStateEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	datasetViewStateEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	viewState VARCHAR(75) null
);