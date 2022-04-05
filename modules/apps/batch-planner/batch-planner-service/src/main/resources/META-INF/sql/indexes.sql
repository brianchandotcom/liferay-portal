create unique index IX_E025DC1A on BatchPlannerMapping (batchPlannerPlanId, externalFieldName[$COLUMN_LENGTH:75$], internalFieldName[$COLUMN_LENGTH:75$]);

create index IX_18CD7477 on BatchPlannerPlan (companyId, export, template);
create unique index IX_221A54A0 on BatchPlannerPlan (companyId, name[$COLUMN_LENGTH:75$]);
create index IX_F2F05D4F on BatchPlannerPlan (companyId, template);
create index IX_874FA8DB on BatchPlannerPlan (companyId, userId);

create unique index IX_A8E0209F on BatchPlannerPolicy (batchPlannerPlanId, name[$COLUMN_LENGTH:75$]);