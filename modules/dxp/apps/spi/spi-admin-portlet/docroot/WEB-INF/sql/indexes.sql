create index IX_C24AAC82 on SPIDefinition (companyId, name[$COLUMN_LENGTH:200$]);
create index IX_3B4A76E9 on SPIDefinition (companyId, status);
create index IX_1214B4BE on SPIDefinition (connectorAddress[$COLUMN_LENGTH:200$], connectorPort);