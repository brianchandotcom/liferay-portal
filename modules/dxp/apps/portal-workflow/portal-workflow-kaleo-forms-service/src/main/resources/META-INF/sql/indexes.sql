create index IX_65CA6CC9 on KaleoProcess (DDLRecordSetId);
create index IX_A29A06D5 on KaleoProcess (groupId);
create index IX_C1C03029 on KaleoProcess (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_9EF0A65D on KaleoProcess (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);

create unique index IX_7073D858 on KaleoProcessLink (kaleoProcessId, workflowTaskName[$COLUMN_LENGTH:75$], companyId);