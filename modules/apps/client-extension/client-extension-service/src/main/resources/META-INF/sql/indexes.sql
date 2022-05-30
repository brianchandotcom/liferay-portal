create unique index IX_CA514799 on ClientExtensionEntry (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_F8DF9578 on ClientExtensionEntry (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_A3BB58FF on ClientExtensionEntryRel (classNameId, classPK, type_[$COLUMN_LENGTH:75$]);
create unique index IX_18F1C56 on ClientExtensionEntryRel (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_96508435 on ClientExtensionEntryRel (uuid_[$COLUMN_LENGTH:75$], companyId);