create table ExportImportReportEntry (
	mvccVersion LONG default 0 not null,
	exportImportReportEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	classExternalReferenceCode VARCHAR(75) null,
	classNameId LONG,
	classPK LONG,
	exportImportConfigurationId LONG,
	errorMessage TEXT null,
	errorStacktrace TEXT null,
	modelNameLanguageKey VARCHAR(75) null,
	origin INTEGER,
	type_ INTEGER,
	status INTEGER
);