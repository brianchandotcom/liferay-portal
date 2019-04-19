create index IX_506DD4FF on BatchFileImport (batchJobExecutionId);
create index IX_7D2451E7 on BatchFileImport (status[$COLUMN_LENGTH:75$]);
create index IX_1C0987B5 on BatchFileImport (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_DA6389E2 on BatchJobExecution (batchJobInstanceId);
create index IX_2FD59E81 on BatchJobExecution (status[$COLUMN_LENGTH:75$]);
create index IX_5C1F5BCF on BatchJobExecution (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_6A2C6370 on BatchJobInstance (jobName[$COLUMN_LENGTH:75$], jobKey[$COLUMN_LENGTH:75$]);
create index IX_3BE96832 on BatchJobInstance (uuid_[$COLUMN_LENGTH:75$], companyId);