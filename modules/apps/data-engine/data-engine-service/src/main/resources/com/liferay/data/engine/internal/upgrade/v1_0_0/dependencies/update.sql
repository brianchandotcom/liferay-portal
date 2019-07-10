create table DEDataDefinitionFieldLink (
	uuid_ VARCHAR(75) null,
	dataDefinitionFieldLinkId LONG not null primary key,
	groupId LONG,
	classNameId LONG,
	classPK LONG,
	DDMStructureId LONG,
	fieldName LONG
);

create unique index IX_41983C6F on DEDataDefinitionFieldLink (classNameId, classPK, DDMStructureId, fieldName);
create unique index IX_AAE65DF2 on DEDataDefinitionFieldLink (uuid_[$COLUMN_LENGTH:75$], groupId);

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

create index IX_4C12AC61 on DEListView (companyId, groupId, DDMStructureId);
create index IX_2159723E on DEListView (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_22757040 on DEListView (uuid_[$COLUMN_LENGTH:75$], groupId);

create table DERecordQuery (
	uuid_ VARCHAR(75) null,
	recordQueryId LONG not null primary key,
	appliedFilters VARCHAR(75) null,
	fieldNames VARCHAR(75) null
);

create index IX_6D18C38 on DERecordQuery (uuid_[$COLUMN_LENGTH:75$]);