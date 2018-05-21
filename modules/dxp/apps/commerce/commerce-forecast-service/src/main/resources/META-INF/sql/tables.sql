create table CommerceForecastEntry (
	commerceForecastEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	time_ LONG,
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
	time_ LONG,
	lowerValue DECIMAL(30, 16) null,
	value DECIMAL(30, 16) null,
	upperValue DECIMAL(30, 16) null
);