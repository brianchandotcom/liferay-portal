create index IX_6C443ED2 on WMSLADefinition (active_, wmSLADefinitionId);
create index IX_514CB68D on WMSLADefinition (companyId, active_, processId, name[$COLUMN_LENGTH:75$]);
create index IX_A66A98D1 on WMSLADefinition (companyId, active_, processId, status, processVersion[$COLUMN_LENGTH:75$]);
create index IX_73175D43 on WMSLADefinition (companyId, status);
create index IX_B867D369 on WMSLADefinition (uuid_[$COLUMN_LENGTH:75$]);

create index IX_C95794DB on WMSLADefinitionVersion (uuid_[$COLUMN_LENGTH:75$]);
create index IX_7A303031 on WMSLADefinitionVersion (wmSLADefinitionId, version[$COLUMN_LENGTH:75$]);