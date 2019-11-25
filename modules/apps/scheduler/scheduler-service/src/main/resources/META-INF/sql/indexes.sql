create unique index IX_D0C48D0D on SchedulerProcess (companyId, name[$COLUMN_LENGTH:75$]);
create index IX_AE51D647 on SchedulerProcess (companyId, type_[$COLUMN_LENGTH:75$]);

create index IX_FB0C321F on SchedulerProcessLog (schedulerProcessId, status);