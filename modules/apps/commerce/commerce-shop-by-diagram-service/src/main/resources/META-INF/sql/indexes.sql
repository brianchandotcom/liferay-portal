create unique index IX_C8D3E29 on CSDiagramEntry (CPDefinitionId, sequence[$COLUMN_LENGTH:75$]);

create index IX_B0DD2127 on CSDiagramPin (CPDefinitionId);

create unique index IX_13D84BD2 on CSDiagramSetting (CPDefinitionId);
create index IX_E5371A47 on CSDiagramSetting (uuid_[$COLUMN_LENGTH:75$], companyId);