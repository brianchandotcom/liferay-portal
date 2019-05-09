create index IX_467A6470 on WorkflowMetricsSLADefinition (companyId, name[$COLUMN_LENGTH:4000$], processId);
create index IX_C56FD8D2 on WorkflowMetricsSLADefinition (companyId, processId, processVersion[$COLUMN_LENGTH:75$], status);
create index IX_6C52ED35 on WorkflowMetricsSLADefinition (companyId, processId, status);
create index IX_F19E35D1 on WorkflowMetricsSLADefinition (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_62BFC93 on WorkflowMetricsSLADefinition (uuid_[$COLUMN_LENGTH:75$], groupId);