create table CAModelCProductRel (
	mvccVersion LONG default 0 not null,
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
	mvccVersion LONG default 0 not null,
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
	mvccVersion LONG default 0 not null,
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