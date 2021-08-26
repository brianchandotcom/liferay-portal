create table CDiscountCAccountGroupRel (
	CDiscountCAccountGroupRelId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceDiscountId LONG,
	commerceAccountGroupId LONG
);

create table CommerceDiscount (
	uuid_ VARCHAR(75) null,
	externalReferenceCode VARCHAR(75) null,
	commerceDiscountId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	title VARCHAR(75) null,
	target VARCHAR(75) null,
	useCouponCode BOOLEAN,
	couponCode VARCHAR(75) null,
	usePercentage BOOLEAN,
	maximumDiscountAmount DECIMAL(30, 16) null,
	levelType VARCHAR(75) null,
	level1 DECIMAL(30, 16) null,
	level2 DECIMAL(30, 16) null,
	level3 DECIMAL(30, 16) null,
	level4 DECIMAL(30, 16) null,
	limitationType VARCHAR(75) null,
	limitationTimes INTEGER,
	limitationTimesPerAccount INTEGER,
	numberOfUse INTEGER,
	rulesConjunction BOOLEAN,
	active_ BOOLEAN,
	displayDate DATE null,
	expirationDate DATE null,
	lastPublishDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table CommerceDiscountAccountRel (
	uuid_ VARCHAR(75) null,
	commerceDiscountAccountRelId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceAccountId LONG,
	commerceDiscountId LONG,
	order_ INTEGER,
	lastPublishDate DATE null
);

create table CommerceDiscountOrderTypeRel (
	uuid_ VARCHAR(75) null,
	commerceDiscountOrderTypeRelId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceDiscountId LONG,
	commerceOrderTypeId LONG,
	priority INTEGER,
	lastPublishDate DATE null
);

create table CommerceDiscountRel (
	commerceDiscountRelId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceDiscountId LONG,
	classNameId LONG,
	classPK LONG
);

create table CommerceDiscountRule (
	commerceDiscountRuleId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	commerceDiscountId LONG,
	type_ VARCHAR(75) null,
	typeSettings TEXT null
);

create table CommerceDiscountUsageEntry (
	commerceDiscountUsageEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceAccountId LONG,
	commerceOrderId LONG,
	commerceDiscountId LONG
);