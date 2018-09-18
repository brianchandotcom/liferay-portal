create table LayoutPageTemplateStructure (
	uuid_ VARCHAR(75) null,
	layoutPageTemplateStructureId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	data_ STRING null
);

create unique index IX_87B60D9 on LayoutPageTemplateStructure (groupId, classNameId, classPK);
create index IX_6DB0225A on LayoutPageTemplateStructure (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_4DB1775C on LayoutPageTemplateStructure (uuid_[$COLUMN_LENGTH:75$], groupId);

COMMIT_TRANSACTION;