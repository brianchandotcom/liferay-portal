create index IX_65CA6CC9 on KaleoProcess (DDLRecordSetId);
create unique index IX_9471B93F on KaleoProcess (groupId, uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_10E0E9D0 on KaleoProcessLink (kaleoProcessId, workflowTaskName[$COLUMN_LENGTH:75$]);