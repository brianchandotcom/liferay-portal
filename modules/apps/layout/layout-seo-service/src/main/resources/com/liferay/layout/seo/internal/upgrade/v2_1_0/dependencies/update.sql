create table LayoutSEOSite (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	layoutSEOSiteId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	openGraphEnabled BOOLEAN,
	openGraphImageFileEntryId LONG
);

create unique index IX_6E43ACCA on LayoutSEOSite (groupId);
create index IX_24696DD4 on LayoutSEOSite (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_C75B5956 on LayoutSEOSite (uuid_[$COLUMN_LENGTH:75$], groupId);

COMMIT_TRANSACTION;