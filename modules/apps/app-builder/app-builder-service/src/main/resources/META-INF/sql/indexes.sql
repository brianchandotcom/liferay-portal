create index IX_F1A2CD76 on AppBuilderApp (companyId, active_, scope[$COLUMN_LENGTH:75$]);
create index IX_EE2A6D09 on AppBuilderApp (companyId, scope[$COLUMN_LENGTH:75$]);
create index IX_4F325E62 on AppBuilderApp (ddmStructureId);
create index IX_319C0B20 on AppBuilderApp (groupId, companyId, ddmStructureId);
create index IX_AB656D87 on AppBuilderApp (groupId, scope[$COLUMN_LENGTH:75$]);
create index IX_EC1E021 on AppBuilderApp (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_65D5FAE3 on AppBuilderApp (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_22C0B8B0 on AppBuilderAppDataRecordLink (appBuilderAppId);
create index IX_CF7CED86 on AppBuilderAppDataRecordLink (ddlRecordId);

create index IX_28C98E35 on AppBuilderAppDeployment (appBuilderAppId, type_[$COLUMN_LENGTH:75$]);