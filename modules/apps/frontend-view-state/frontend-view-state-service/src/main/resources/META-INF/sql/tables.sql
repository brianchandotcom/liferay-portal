create table FrontendViewStateActiveEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	frontendViewStateActiveEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	datasetDisplayId VARCHAR(75) null,
	frontendViewStateEntryId LONG,
	plid LONG,
	portletId VARCHAR(200) null
);

create table FrontendViewStateEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	frontendViewStateEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	viewState TEXT null
);