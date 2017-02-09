create table DDMDataProviderInstance (
	uuid_ VARCHAR(75) null,
	dataProviderInstanceId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name STRING null,
	description TEXT null,
	definition TEXT null,
	type_ VARCHAR(75) null
);

create index IX_DB54A6E5 on DDMDataProviderInstance (companyId);
create index IX_1333A2A7 on DDMDataProviderInstance (groupId);
create index IX_C903C097 on DDMDataProviderInstance (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_B4E180D9 on DDMDataProviderInstance (uuid_[$COLUMN_LENGTH:75$], groupId);

create table DDMDataProviderInstanceLink (
	dataProviderInstanceLinkId LONG not null primary key,
	companyId LONG,
	dataProviderInstanceId LONG,
	structureId LONG
);

create unique index IX_8C878342 on DDMDataProviderInstanceLink (dataProviderInstanceId, structureId);
create index IX_CB823541 on DDMDataProviderInstanceLink (structureId);

alter table DDMStructure add versionUserId LONG;
alter table DDMStructure add versionUserName VARCHAR(75) null;
alter table DDMStructure add version VARCHAR(75) null;

update DDMStructure set versionUserId = userId;
update DDMStructure set versionUserName = userName;
update DDMStructure set version = '1.0';

create table DDMStructureLayout (
	uuid_ VARCHAR(75) null,
	structureLayoutId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	structureVersionId LONG,
	definition TEXT null
);

create unique index IX_B7158C0A on DDMStructureLayout (structureVersionId);
create index IX_A90FF72A on DDMStructureLayout (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_C9A0402C on DDMStructureLayout (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_E43143A3 on DDMStructureLink (classNameId, classPK, structureId);

create table DDMStructureVersion (
	structureVersionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	structureId LONG,
	version VARCHAR(75) null,
	parentStructureId LONG,
	name STRING null,
	description TEXT null,
	definition TEXT null,
	storageType VARCHAR(75) null,
	type_ INTEGER,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create index IX_17B3C96C on DDMStructureVersion (structureId, status);
create unique index IX_64C3C42 on DDMStructureVersion (structureId, version[$COLUMN_LENGTH:75$]);

alter table DDMTemplate add versionUserId LONG;
alter table DDMTemplate add versionUserName VARCHAR(75) null;
alter table DDMTemplate add resourceClassNameId LONG;
alter table DDMTemplate add version VARCHAR(75) null;

update DDMTemplate set versionUserId = userId;
update DDMTemplate set versionUserName = userName;
update DDMTemplate set version = '1.0';

create table DDMTemplateLink (
	templateLinkId LONG not null primary key,
	companyId LONG,
	classNameId LONG,
	classPK LONG,
	templateId LONG
);

create unique index IX_6F3B3E9C on DDMTemplateLink (classNameId, classPK);
create index IX_85278170 on DDMTemplateLink (templateId);

create table DDMTemplateVersion (
	templateVersionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	classNameId LONG,
	classPK LONG,
	templateId LONG,
	version VARCHAR(75) null,
	name TEXT null,
	description TEXT null,
	language VARCHAR(75) null,
	script TEXT null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create index IX_66382FC6 on DDMTemplateVersion (templateId, status);
create unique index IX_8854A128 on DDMTemplateVersion (templateId, version[$COLUMN_LENGTH:75$]);

COMMIT_TRANSACTION;