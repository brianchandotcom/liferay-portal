create table CCart (
	uuid_ VARCHAR(75) null,
	CCartId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	cartUserId LONG,
	title VARCHAR(75) null,
	type_ INTEGER
);

create table CCartItem (
	uuid_ VARCHAR(75) null,
	CCartItemId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	CCartId LONG,
	CPDefinitionId LONG,
	CPInstanceId LONG,
	quantity INTEGER,
	json VARCHAR(75) null
);