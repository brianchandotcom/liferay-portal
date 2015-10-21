create table SAPEntry (
	companyId LONG,
	uuid_ VARCHAR(75) null,
	sapEntryId LONG not null primary key,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	allowedServiceSignatures STRING null,
	defaultSAPEntry BOOLEAN,
	enabled BOOLEAN,
	name VARCHAR(75) null,
	title STRING null
);