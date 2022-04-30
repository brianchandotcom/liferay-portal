create index IX_CA514799 on ClientExtensionEntry (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_F8DF9578 on ClientExtensionEntry (uuid_[$COLUMN_LENGTH:75$], companyId);