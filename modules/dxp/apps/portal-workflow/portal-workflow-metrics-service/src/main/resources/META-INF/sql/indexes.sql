create index IX_6C443ED2 on WMSLADefinition (active_, wmSLADefinitionId);
create index IX_514CB68D on WMSLADefinition (companyId, active_, processId, name[$COLUMN_LENGTH:75$]);
create index IX_A66A98D1 on WMSLADefinition (companyId, active_, processId, status, processVersion[$COLUMN_LENGTH:75$]);
create index IX_73175D43 on WMSLADefinition (companyId, status);
create unique index IX_285A6761 on WMSLADefinition (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_DB48262F on WMSLADefinitionVersion (uuid_[$COLUMN_LENGTH:75$], groupId);
create index IX_7A303031 on WMSLADefinitionVersion (wmSLADefinitionId, version[$COLUMN_LENGTH:75$]);