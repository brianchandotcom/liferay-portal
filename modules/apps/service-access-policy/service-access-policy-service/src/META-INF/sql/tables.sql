create table SACPEntry (
	uuid_ VARCHAR(75) null,
	sacpEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	allowedServiceSignatures STRING null,
	defaultSACPEntry BOOLEAN,
	name VARCHAR(75) null,
	title STRING null
);

create table ServiceAccessPolicy (
	uuid_ VARCHAR(75) null,
	serviceAccessPolicyId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	allowedServiceSignatures STRING null,
	defaultServiceAccessPolicy BOOLEAN,
	name VARCHAR(75) null,
	title STRING null
);