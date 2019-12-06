create table AnalyticsMessage (
	mvccVersion LONG default 0 not null,
	analyticsMessageId LONG not null,
	companyId LONG not null,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	body BLOB,
	primary key (analyticsMessageId, companyId)
);