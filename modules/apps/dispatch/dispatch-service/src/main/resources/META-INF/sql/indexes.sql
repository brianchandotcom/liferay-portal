create index IX_36F4EB5F on DispatchLog (dispatchTriggerId, status);

create index IX_71D6AFE9 on DispatchTrigger (active_, dispatchTaskClusterMode);
create index IX_1B108A04 on DispatchTrigger (companyId, dispatchTaskExecutorType[$COLUMN_LENGTH:75$]);
create unique index IX_D86DCE63 on DispatchTrigger (companyId, name[$COLUMN_LENGTH:75$]);
create index IX_F6ABBDDE on DispatchTrigger (companyId, userId);