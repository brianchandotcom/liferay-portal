create table AnalyticsAssociationChange (
	mvccVersion LONG default 0 not null,
	analyticsAssociationChangeId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	userId LONG,
	associationClassName VARCHAR(75) null,
	associationClassPK LONG,
	className VARCHAR(75) null,
	classPK LONG
);

create table AnalyticsDeleteMessage (
	mvccVersion LONG default 0 not null,
	analyticsDeleteMessageId LONG not null primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	className VARCHAR(255) null,
	classPK LONG
);

create table AnalyticsMessage (
	mvccVersion LONG default 0 not null,
	analyticsMessageId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	body BLOB
);