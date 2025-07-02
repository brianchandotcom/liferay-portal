create table Child (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	childId LONG not null,
	companyId LONG,
	name VARCHAR(75) null,
	grandParentId LONG,
	parentChildId LONG,
	parentName VARCHAR(75) null,
	primary key (childId, ctCollectionId)
);

create table GrandParent (
	mvccVersion LONG default 0 not null,
	grandParentId LONG not null primary key,
	companyId LONG,
	name VARCHAR(75) null,
	parentGrandParentId LONG
);

create table Parent (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	parentId LONG not null,
	companyId LONG,
	name VARCHAR(75) null,
	grandParentId LONG,
	primary key (parentId, ctCollectionId)
);