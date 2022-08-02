create table LayoutUtilityPageEntry (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	externalReferenceCode VARCHAR(75) null,
	LayoutUtilityPageEntryId LONG not null,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	plid LONG,
	type_ INTEGER,
	defaultLayoutUtilityPageEntry BOOLEAN,
	lastPublishDate DATE null,
	primary key (LayoutUtilityPageEntryId, ctCollectionId)
);