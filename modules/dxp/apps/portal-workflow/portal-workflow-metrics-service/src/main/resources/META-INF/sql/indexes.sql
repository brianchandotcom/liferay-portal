create index IX_A80FF93C on WorkflowMetricsSLACalendar (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_8A7D9EBE on WorkflowMetricsSLACalendar (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_E9612291 on WorkflowMetricsSLACondition (companyId, workflowMetricsSLADefinitionId);
create index IX_3EE5A191 on WorkflowMetricsSLACondition (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_81DB5853 on WorkflowMetricsSLACondition (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_467A6470 on WorkflowMetricsSLADefinition (companyId, name[$COLUMN_LENGTH:4000$], processId);
create index IX_62B36D4F on WorkflowMetricsSLADefinition (companyId, processId);
create index IX_F19E35D1 on WorkflowMetricsSLADefinition (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_62BFC93 on WorkflowMetricsSLADefinition (uuid_[$COLUMN_LENGTH:75$], groupId);