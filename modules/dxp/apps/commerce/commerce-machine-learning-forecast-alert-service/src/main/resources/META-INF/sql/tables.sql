create table CommerceMLForecastAlertEntry (
	uuid_ VARCHAR(75) null,
	commerceMLForecastAlertEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceAccountId LONG,
	actual DOUBLE,
	forecast DOUBLE,
	timestamp DATE null,
	relativeChange DOUBLE,
	status INTEGER
);