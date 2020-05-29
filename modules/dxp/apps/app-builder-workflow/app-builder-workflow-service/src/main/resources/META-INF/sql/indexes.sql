create index IX_67E8BB07 on AppBuilderAppWorkflowTaskLink (appBuilderAppId);

create index IX_C32BC76 on AppBuilderWorkflowTaskLink (appBuilderAppId, ddmStructureLayoutId, workflowTaskName[$COLUMN_LENGTH:75$]);
create index IX_8AEB57F7 on AppBuilderWorkflowTaskLink (appBuilderAppId, workflowTaskName[$COLUMN_LENGTH:75$]);