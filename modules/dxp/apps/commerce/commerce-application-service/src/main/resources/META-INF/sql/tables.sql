create table CAModelCProductRel (
	CAModelCProductRelId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceApplicationModelId LONG,
	CProductId LONG
);

create table CommerceApplicationBrand (
	commerceApplicationBrandId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	logoId LONG
);

create table CommerceApplicationModel (
	commerceApplicationModelId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceApplicationBrandId LONG,
	name VARCHAR(75) null,
	year VARCHAR(75) null
);