create unique index IX_D7841C37 on BatchPlannerLog (batchPlannerPlanId, batchEngineExportTaskERC[$COLUMN_LENGTH:75$]);
create unique index IX_8A6A1B66 on BatchPlannerLog (batchPlannerPlanId, batchEngineImportTaskERC[$COLUMN_LENGTH:75$]);
create unique index IX_8806EF8E on BatchPlannerLog (batchPlannerPlanId, dispatchTriggerERC[$COLUMN_LENGTH:75$]);
create index IX_ECB4A956 on BatchPlannerLog (companyId);

create unique index IX_E025DC1A on BatchPlannerMapping (batchPlannerPlanId, externalFieldName[$COLUMN_LENGTH:75$], internalFieldName[$COLUMN_LENGTH:75$]);

create index IX_18CD7477 on BatchPlannerPlan (companyId, export, template);
create unique index IX_221A54A0 on BatchPlannerPlan (companyId, name[$COLUMN_LENGTH:75$]);
create index IX_F2F05D4F on BatchPlannerPlan (companyId, template);
create index IX_874FA8DB on BatchPlannerPlan (companyId, userId);

create unique index IX_A8E0209F on BatchPlannerPolicy (batchPlannerPlanId, name[$COLUMN_LENGTH:75$]);