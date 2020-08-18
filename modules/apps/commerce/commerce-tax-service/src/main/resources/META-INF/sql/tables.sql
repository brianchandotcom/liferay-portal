create table CommerceTaxMethod (
	commerceTaxMethodId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name STRING null,
	description STRING null,
	engineKey VARCHAR(75) null,
	percentage BOOLEAN,
	active_ BOOLEAN
);