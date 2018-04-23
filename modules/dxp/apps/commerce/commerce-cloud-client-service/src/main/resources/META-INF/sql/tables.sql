create table CCOrderForecastSync (
	CCOrderForecastSyncId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	createDate DATE null,
	commerceOrderId LONG,
	syncDate DATE null
);