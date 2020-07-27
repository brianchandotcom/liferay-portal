create index IX_5404B36 on AppBuilderWorkflowTaskLink (appBuilderAppId, appBuilderAppVersionId, ddmStructureLayoutId, workflowTaskName[$COLUMN_LENGTH:75$]);
create index IX_89076B7 on AppBuilderWorkflowTaskLink (appBuilderAppId, appBuilderAppVersionId, workflowTaskName[$COLUMN_LENGTH:75$]);
create index IX_C32BC76 on AppBuilderWorkflowTaskLink (appBuilderAppId, ddmStructureLayoutId, workflowTaskName[$COLUMN_LENGTH:75$]);
create index IX_8AEB57F7 on AppBuilderWorkflowTaskLink (appBuilderAppId, workflowTaskName[$COLUMN_LENGTH:75$]);