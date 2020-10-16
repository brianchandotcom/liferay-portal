create index IX_36F4EB5F on DispatchLog (dispatchTriggerId, status);

create unique index IX_D86DCE63 on DispatchTrigger (companyId, name[$COLUMN_LENGTH:75$]);
create index IX_73C1B9EA on DispatchTrigger (companyId, taskExecutorType[$COLUMN_LENGTH:75$]);
create index IX_F6ABBDDE on DispatchTrigger (companyId, userId);