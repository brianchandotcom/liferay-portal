create table AnalyticsMessage (
	mvccVersion LONG default 0 not null,
	analyticsMessageId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	body BLOB
);