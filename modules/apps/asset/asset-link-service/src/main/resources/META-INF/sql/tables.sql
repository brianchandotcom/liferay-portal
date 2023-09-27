create table AssetLink (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	linkId LONG not null,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	entryId1 LONG,
	entryId2 LONG,
	type_ INTEGER,
	weight INTEGER,
	primary key (linkId, ctCollectionId)
);