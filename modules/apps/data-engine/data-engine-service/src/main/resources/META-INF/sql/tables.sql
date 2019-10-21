create table DEDataDefinitionFieldLink (
	uuid_ VARCHAR(75) null,
	deDataDefinitionFieldLinkId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	classNameId LONG,
	classPK LONG,
	ddmStructureId LONG,
	fieldName VARCHAR(75) null
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
	appliedFilters VARCHAR(75) null,
	ddmStructureId LONG,
	fieldNames TEXT null,
	name STRING null,
	sortField VARCHAR(75) null
);