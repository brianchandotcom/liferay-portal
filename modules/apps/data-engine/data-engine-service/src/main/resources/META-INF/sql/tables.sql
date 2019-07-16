create table DEDataDefinitionFieldLink (
	uuid_ VARCHAR(75) null,
	deDataDefinitionFieldLinkId LONG not null primary key,
	groupId LONG,
	classNameId LONG,
	classPK LONG,
	ddmStructureId LONG,
	fieldName LONG
);

create table DEDataListView (
	uuid_ VARCHAR(75) null,
	deDataListViewId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	ddmStructureId LONG,
	name STRING null,
	deDataRecordQueryId LONG
);

create table DEDataRecordQuery (
	uuid_ VARCHAR(75) null,
	deDataRecordQueryId LONG not null primary key,
	appliedFilters VARCHAR(75) null,
	fieldNames VARCHAR(75) null
);