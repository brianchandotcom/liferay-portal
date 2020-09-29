create index IX_36F4EB5F on DispatchLog (dispatchTriggerId, status);

create unique index IX_D86DCE63 on DispatchTrigger (companyId, name[$COLUMN_LENGTH:75$]);
create index IX_E9AD7A37 on DispatchTrigger (companyId, taskType[$COLUMN_LENGTH:75$]);