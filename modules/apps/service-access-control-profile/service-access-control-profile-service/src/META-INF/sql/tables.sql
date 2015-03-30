create table SACPEntry (
	uuid_ VARCHAR(75) null,
	sacpEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	allowedServices STRING null,
	name VARCHAR(75) null,
	title STRING null
);