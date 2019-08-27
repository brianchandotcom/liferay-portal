create index IX_C1C90A3E on WMSLADefinition (companyId, name[$COLUMN_LENGTH:4000$], processId);
create index IX_9959E720 on WMSLADefinition (companyId, processId, processVersion[$COLUMN_LENGTH:75$], status);
create index IX_4E8F5783 on WMSLADefinition (companyId, processId, status);
create index IX_73175D43 on WMSLADefinition (companyId, status);
create index IX_41DD251F on WMSLADefinition (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_285A6761 on WMSLADefinition (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_F056686D on WMSLADefinitionVersion (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_DB48262F on WMSLADefinitionVersion (uuid_[$COLUMN_LENGTH:75$], groupId);
create index IX_7A303031 on WMSLADefinitionVersion (wmSLADefinitionId, version[$COLUMN_LENGTH:75$]);