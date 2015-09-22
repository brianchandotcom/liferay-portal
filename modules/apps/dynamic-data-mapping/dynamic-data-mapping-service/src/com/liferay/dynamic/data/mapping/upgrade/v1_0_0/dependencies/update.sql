alter table DDMStorageLink add companyId LONG;

create index IX_DB81EB42 on DDMStorageLink (uuid_, companyId);

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

alter table DDMStructureLink add companyId LONG;

drop index IX_C803899D on DDMStructureLink;

create table DDMStructureVersion (
	structureVersionId LONG not null primary key,
	groupId LONG,
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
	statusDate DATE null,
	companyId LONG
);

alter table DDMTemplate add versionUserId LONG;
alter table DDMTemplate add versionUserName VARCHAR(75) null;
alter table DDMTemplate add resourceClassNameId LONG;
alter table DDMTemplate add version VARCHAR(75) null;

update DDMTemplate set versionUserId = userId;
update DDMTemplate set versionUserName = userName;
update DDMTemplate set version = '1.0';

create table DDMTemplateLink (
	templateLinkId LONG not null primary key,
	classNameId LONG,
	classPK LONG,
	templateId LONG,
	companyId LONG
);

create table DDMTemplateVersion (
	templateVersionId LONG not null primary key,
	groupId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	classNameId LONG,
	classPK LONG,
	templateId LONG,
	version VARCHAR(75) null,
	name STRING null,
	description TEXT null,
	language VARCHAR(75) null,
	script TEXT null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null,
	companyId LONG
);

COMMIT_TRANSACTION;