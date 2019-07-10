create table DEDataDefinitionFieldLink (
	uuid_ VARCHAR(75) null,
	dataDefinitionFieldLinkId LONG not null primary key,
	groupId LONG,
	classNameId LONG,
	classPK LONG,
	DDMStructureId LONG,
	fieldName LONG
);

create table DEListView (
	uuid_ VARCHAR(75) null,
	listViewId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	DDMStructureId LONG,
	name STRING null,
	recordQueryId LONG
);

create table DERecordQuery (
	uuid_ VARCHAR(75) null,
	recordQueryId LONG not null primary key,
	appliedFilters VARCHAR(75) null,
	fieldNames VARCHAR(75) null
);