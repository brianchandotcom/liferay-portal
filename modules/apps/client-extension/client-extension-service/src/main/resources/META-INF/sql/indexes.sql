create index IX_32C1FC31 on ClientExtensionEntry (companyId, type_[$COLUMN_LENGTH:75$]);
create index IX_D52A6C39 on ClientExtensionEntry (externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_526820B0 on ClientExtensionEntry (uuid_[$COLUMN_LENGTH:75$]);

create index IX_A3BB58FF on ClientExtensionEntryRel (classNameId, classPK, type_[$COLUMN_LENGTH:75$]);
create index IX_44C5316 on ClientExtensionEntryRel (companyId, cetExternalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_81A22B9C on ClientExtensionEntryRel (externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_39990613 on ClientExtensionEntryRel (uuid_[$COLUMN_LENGTH:75$]);