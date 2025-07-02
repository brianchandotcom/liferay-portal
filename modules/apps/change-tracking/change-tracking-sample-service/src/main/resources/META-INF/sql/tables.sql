create table CTSChild (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	ctsChildId LONG not null,
	companyId LONG,
	name VARCHAR(75) null,
	ctsGrandParentId LONG,
	ctsParentName VARCHAR(75) null,
	parentCTSChildId LONG,
	primary key (ctsChildId, ctCollectionId)
);

create table CTSGrandParent (
	mvccVersion LONG default 0 not null,
	ctsGrandParentId LONG not null primary key,
	companyId LONG,
	name VARCHAR(75) null,
	parentCTSGrandParentId LONG
);

create table CTSParent (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	ctsParentId LONG not null,
	companyId LONG,
	name VARCHAR(75) null,
	ctsGrandParentId LONG,
	primary key (ctsParentId, ctCollectionId)
);