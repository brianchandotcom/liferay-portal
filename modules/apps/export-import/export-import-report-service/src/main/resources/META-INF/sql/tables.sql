create table ImportReportEntry (
	mvccVersion LONG default 0 not null,
	importReportEntryId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	entityClassNameId LONG,
	entityExternalReferenceCode VARCHAR(75) null,
	error TEXT null,
	resolved BOOLEAN,
	type_ INTEGER
);