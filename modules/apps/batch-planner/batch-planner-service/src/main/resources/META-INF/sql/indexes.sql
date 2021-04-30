create unique index IX_5648B6E9 on BatchPlannerLog (batchPlannerPlanId, batchExternalReferenceCode[$COLUMN_LENGTH:75$]);
create unique index IX_C0A9DBA1 on BatchPlannerLog (batchPlannerPlanId, dispatchExternalReferenceCode[$COLUMN_LENGTH:75$]);

create unique index IX_6B9D4FF6 on BatchPlannerMapping (batchPlannerPlanId, externalName[$COLUMN_LENGTH:75$]);

create index IX_60C9F7B7 on BatchPlannerPlan (companyId, externalContentType[$COLUMN_LENGTH:75$]);
create unique index IX_221A54A0 on BatchPlannerPlan (companyId, name[$COLUMN_LENGTH:75$]);
create index IX_874FA8DB on BatchPlannerPlan (companyId, userId);

create unique index IX_A8E0209F on BatchPlannerPolicy (batchPlannerPlanId, name[$COLUMN_LENGTH:75$]);