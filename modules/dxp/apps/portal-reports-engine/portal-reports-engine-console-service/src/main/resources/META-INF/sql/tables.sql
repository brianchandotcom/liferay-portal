create table Reports_Definition (
	uuid_ VARCHAR(75) null,
	definitionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name STRING null,
	description STRING null,
	sourceId LONG,
	reportName VARCHAR(75) null,
	reportParameters TEXT null,
	lastPublishDate DATE null
);

create table Reports_Entry (
	entryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	definitionId LONG,
	format VARCHAR(75) null,
	scheduleRequest BOOLEAN,
	startDate DATE null,
	endDate DATE null,
	repeating BOOLEAN,
	recurrence VARCHAR(75) null,
	emailNotifications VARCHAR(200) null,
	emailDelivery VARCHAR(200) null,
	portletId VARCHAR(75) null,
	pageURL STRING null,
	reportParameters TEXT null,
	status VARCHAR(75) null,
	errorMessage STRING null
);

create table Reports_Source (
	uuid_ VARCHAR(75) null,
	sourceId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	lastPublishDate DATE null,
	name STRING null,
	driverClassName VARCHAR(75) null,
	driverUrl STRING null,
	driverUserName VARCHAR(75) null,
	driverPassword VARCHAR(75) null
);