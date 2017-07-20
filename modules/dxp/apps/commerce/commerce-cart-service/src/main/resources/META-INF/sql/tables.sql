create table CommerceCart (
	uuid_ VARCHAR(75) null,
	CommerceCartId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	cartUserId LONG,
	name VARCHAR(75) null,
	type_ INTEGER
);

create table CommerceCartItem (
	uuid_ VARCHAR(75) null,
	CommerceCartItemId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	CommerceCartId LONG,
	CPDefinitionId LONG,
	CPInstanceId LONG,
	quantity INTEGER,
	json VARCHAR(75) null
);