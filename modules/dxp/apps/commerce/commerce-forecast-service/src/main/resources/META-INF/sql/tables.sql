create table CommerceForecastEntry (
	commerceForecastEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	date_ DATE null,
	period INTEGER,
	target INTEGER,
	customerId LONG,
	sku VARCHAR(75) null,
	assertivity DECIMAL(30, 16) null
);

create table CommerceForecastValue (
	commerceForecastValueId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceForecastEntryId LONG,
	date_ DATE null,
	lowerValue DECIMAL(30, 16) null,
	value DECIMAL(30, 16) null,
	upperValue DECIMAL(30, 16) null
);