create table DEDataDefinitionFieldLink (
	uuid_ VARCHAR(75) null,
	deDataDefinitionFieldLinkId LONG not null,
	groupId LONG,
	companyId LONG not null,
	classNameId LONG,
	classPK LONG,
	ddmStructureId LONG,
	fieldName VARCHAR(75) null,
	primary key (deDataDefinitionFieldLinkId, companyId)
);

create table DEDataListView (
	uuid_ VARCHAR(75) null,
	deDataListViewId LONG not null,
	groupId LONG,
	companyId LONG not null,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	appliedFilters TEXT null,
	ddmStructureId LONG,
	fieldNames TEXT null,
	name STRING null,
	sortField VARCHAR(75) null,
	primary key (deDataListViewId, companyId)
);