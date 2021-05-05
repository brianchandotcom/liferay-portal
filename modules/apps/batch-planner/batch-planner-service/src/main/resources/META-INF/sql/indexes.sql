create unique index IX_4E3F0B8B on BatchPlannerLog (batchPlannerPlanId, batchEngineTaskERC[$COLUMN_LENGTH:75$]);
create unique index IX_8806EF8E on BatchPlannerLog (batchPlannerPlanId, dispatchTriggerERC[$COLUMN_LENGTH:75$]);

create unique index IX_C9E92E75 on BatchPlannerMapping (batchPlannerPlanId, contentFieldName[$COLUMN_LENGTH:75$], openAPIFieldName[$COLUMN_LENGTH:75$]);

create unique index IX_221A54A0 on BatchPlannerPlan (companyId, name[$COLUMN_LENGTH:75$]);
create index IX_874FA8DB on BatchPlannerPlan (companyId, userId);

create unique index IX_A8E0209F on BatchPlannerPolicy (batchPlannerPlanId, name[$COLUMN_LENGTH:75$]);