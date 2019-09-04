create table LayoutSEOEntry (
	uuid_ VARCHAR(75) null,
	layoutSEOEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	canonicalURL STRING null,
	enabled BOOLEAN,
	privateLayout BOOLEAN,
	lastPublishDate DATE null,
	layoutId LONG
);