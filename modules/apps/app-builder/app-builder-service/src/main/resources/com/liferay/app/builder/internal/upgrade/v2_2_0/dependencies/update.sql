create table AppBuilderAppDataRecordLink (
	appBuilderAppDataRecordLinkId LONG not null primary key,
	companyId LONG,
	appBuilderAppId LONG,
	ddlRecordId LONG
);

create index IX_22C0B8B0 on AppBuilderAppDataRecordLink (appBuilderAppId);
create index IX_CF7CED86 on AppBuilderAppDataRecordLink (ddlRecordId);

COMMIT_TRANSACTION;