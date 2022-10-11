create table AccountEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	externalReferenceCode VARCHAR(75) null,
	accountEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	defaultBillingAddressId LONG,
	defaultCPaymentMethodKey VARCHAR(75) null,
	defaultShippingAddressId LONG,
	parentAccountEntryId LONG,
	description STRING null,
	domains STRING null,
	emailAddress VARCHAR(254) null,
	logoId LONG,
	name VARCHAR(100) null,
	restrictMembership BOOLEAN,
	taxExemptionCode VARCHAR(75) null,
	taxIdNumber VARCHAR(75) null,
	type_ VARCHAR(75) null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table AccountEntryOrganizationRel (
	mvccVersion LONG default 0 not null,
	accountEntryOrganizationRelId LONG not null primary key,
	companyId LONG,
	accountEntryId LONG,
	organizationId LONG
);

create table AccountEntryUserRel (
	mvccVersion LONG default 0 not null,
	accountEntryUserRelId LONG not null primary key,
	companyId LONG,
	accountEntryId LONG,
	accountUserId LONG
);

create table AccountGroup (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	externalReferenceCode VARCHAR(75) null,
	accountGroupId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	defaultAccountGroup BOOLEAN,
	description VARCHAR(75) null,
	name VARCHAR(75) null,
	type_ VARCHAR(75) null
);

create table AccountGroupRel (
	mvccVersion LONG default 0 not null,
	accountGroupRelId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	accountGroupId LONG,
	classNameId LONG,
	classPK LONG
);

create table AccountRole (
	mvccVersion LONG default 0 not null,
	accountRoleId LONG not null primary key,
	companyId LONG,
	accountEntryId LONG,
	roleId LONG
);