create table CommerceTaxFixedRate (
	mvccVersion LONG default 0 not null,
	commerceTaxFixedRateId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	CPTaxCategoryId LONG,
	commerceTaxMethodId LONG,
	rate DOUBLE
);

create table CommerceTaxFixedRateAddressRel (
	mvccVersion LONG default 0 not null,
	CTaxFixedRateAddressRelId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceTaxMethodId LONG,
	CPTaxCategoryId LONG,
	countryId LONG,
	regionId LONG,
	zip VARCHAR(75) null,
	rate DOUBLE
);