create table ExportImportReportEntry (
	mvccVersion LONG default 0 not null,
	exportImportReportEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	classExternalReferenceCode VARCHAR(75) null,
	classNameId LONG,
	error TEXT null,
	errorStacktrace TEXT null,
	exportImportConfigurationId LONG,
	resolved BOOLEAN,
	type_ INTEGER
);