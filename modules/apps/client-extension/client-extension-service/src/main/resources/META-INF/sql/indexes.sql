create index IX_93662B82 on ClientExtensionEntry (companyId, ctCollectionId);
create unique index IX_EFD3CBF7 on ClientExtensionEntry (companyId, externalReferenceCode[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_CD38508F on ClientExtensionEntry (companyId, type_[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_9E207BD6 on ClientExtensionEntry (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_C85B970E on ClientExtensionEntry (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);

create index IX_224D5674 on ClientExtensionEntryRel (classNameId, classPK, ctCollectionId);
create index IX_20AA515D on ClientExtensionEntryRel (classNameId, classPK, type_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_465286B4 on ClientExtensionEntryRel (companyId, externalReferenceCode[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_D55E5093 on ClientExtensionEntryRel (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_74D35671 on ClientExtensionEntryRel (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);