create table CAccountGroupCAccountRel (
	externalReferenceCode VARCHAR(75) null,
	CAccountGroupCAccountRelId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceAccountGroupId LONG,
	commerceAccountId LONG
);

create table CommerceAccount (
	externalReferenceCode VARCHAR(75) null,
	commerceAccountId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentCommerceAccountId LONG,
	name VARCHAR(255) null,
	logoId LONG,
	email VARCHAR(75) null,
	taxId VARCHAR(75) null,
	type_ INTEGER,
	active_ BOOLEAN,
	displayDate DATE null,
	defaultBillingAddressId LONG,
	defaultShippingAddressId LONG,
	expirationDate DATE null,
	lastPublishDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table CommerceAccountGroupRel (
	commerceAccountGroupRelId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	commerceAccountGroupId LONG
);

create table CommerceAccountOrganizationRel (
	commerceAccountId LONG not null,
	organizationId LONG not null,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	primary key (commerceAccountId, organizationId)
);

create table CommerceAccountUserRel (
	commerceAccountId LONG not null,
	commerceAccountUserId LONG not null,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	primary key (commerceAccountId, commerceAccountUserId)
);