create index IX_42CA0281 on AppBuilderApp (companyId, status);
create index IX_D72D2A06 on AppBuilderApp (groupId, companyId, ddmStructureId, status);
create index IX_EC1E021 on AppBuilderApp (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_65D5FAE3 on AppBuilderApp (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_C9D070F3 on AppBuilderAppDeployment (appBuilderAppId, deploymentType[$COLUMN_LENGTH:75$]);