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
	externalReferenceCode VARCHAR(75) null,
	commercePaymentEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	commerceChannelId LONG,
	amount BIGDECIMAL null,
	callbackURL TEXT null,
	cancelURL TEXT null,
	currencyCode VARCHAR(75) null,
	errorMessages TEXT null,
	languageId VARCHAR(75) null,
	note TEXT null,
	payload TEXT null,
	paymentIntegrationKey VARCHAR(75) null,
	paymentIntegrationType INTEGER,
	paymentStatus INTEGER,
	reasonKey VARCHAR(75) null,
	reasonName STRING null,
	redirectURL TEXT null,
	transactionCode VARCHAR(255) null,
	type_ INTEGER
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
	amount BIGDECIMAL null,
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
	active_ BOOLEAN,
	imageId LONG,
	paymentIntegrationKey VARCHAR(75) null,
	priority DOUBLE,
	typeSettings TEXT null
);