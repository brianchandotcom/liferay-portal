create table CPMethodGroupRelQualifier (
	mvccVersion LONG default 0 not null,
	CPMethodGroupRelQualifierId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	CPaymentMethodGroupRelId LONG
);

create table CommercePaymentEntry (
	mvccVersion LONG default 0 not null,
	commercePaymentEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	amount DECIMAL(30, 16) null,
	currencyCode VARCHAR(75) null,
	paymentMethodName VARCHAR(75) null,
	paymentStatus INTEGER,
	transactionCode VARCHAR(75) null
);

create table CommercePaymentEntryAudit (
	mvccVersion LONG default 0 not null,
	commercePaymentEntryAuditId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commercePaymentEntryId LONG,
	amount DECIMAL(30, 16) null,
	currencyCode VARCHAR(75) null,
	logType VARCHAR(75) null,
	logTypeSettings TEXT null
);

create table CommercePaymentMethodGroupRel (
	mvccVersion LONG default 0 not null,
	CPaymentMethodGroupRelId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name STRING null,
	description STRING null,
	imageId LONG,
	engineKey VARCHAR(75) null,
	priority DOUBLE,
	active_ BOOLEAN
);