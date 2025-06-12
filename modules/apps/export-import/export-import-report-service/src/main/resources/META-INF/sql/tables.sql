create table ExportImportReportEntry (
	mvccVersion LONG default 0 not null,
	exportImportReportEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	entityClassNameId LONG,
	entityExternalReferenceCode VARCHAR(75) null,
	error VARCHAR(75) null,
	errorStacktrace VARCHAR(75) null,
	exportImportConfigurationId LONG,
	resolved BOOLEAN,
	type_ INTEGER
);