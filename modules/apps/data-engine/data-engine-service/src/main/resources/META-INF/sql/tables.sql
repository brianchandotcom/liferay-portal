create table DEDataDefinitionFieldLink (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	deDataDefinitionFieldLinkId LONG not null,
	groupId LONG,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	ddmStructureId LONG,
	fieldName VARCHAR(255) null,
	lastPublishDate DATE null,
	primary key (deDataDefinitionFieldLinkId, ctCollectionId)
);

create table DEDataListView (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	deDataListViewId LONG not null,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	appliedFilters TEXT null,
	ddmStructureId LONG,
	fieldNames TEXT null,
	name STRING null,
	sortField VARCHAR(75) null,
	primary key (deDataListViewId, ctCollectionId)
);