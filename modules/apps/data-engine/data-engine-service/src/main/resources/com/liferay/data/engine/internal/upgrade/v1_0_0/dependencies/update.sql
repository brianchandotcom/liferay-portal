create table DEDataDefinitionFieldLink (
	uuid_ VARCHAR(75) null,
	deDataDefinitionFieldLinkId LONG not null primary key,
	groupId LONG,
	classNameId LONG,
	classPK LONG,
	ddmStructureId LONG,
	fieldName LONG
);

create unique index IX_2CEE588F on DEDataDefinitionFieldLink (classNameId, classPK, ddmStructureId, fieldName);
create unique index IX_AAE65DF2 on DEDataDefinitionFieldLink (uuid_[$COLUMN_LENGTH:75$], groupId);

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
	fieldNames VARCHAR(75) null,
	name STRING null,
	sortField VARCHAR(75) null
);

create index IX_FA1639C7 on DEDataListView (groupId, companyId, ddmStructureId);
create index IX_7113A88 on DEDataListView (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_3336C30A on DEDataListView (uuid_[$COLUMN_LENGTH:75$], groupId);