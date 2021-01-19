create index IX_24C6B95A on TranslationEntry (classNameId, classPK, ctCollectionId);
create unique index IX_322E31A1 on TranslationEntry (classNameId, classPK, languageId[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_581ACBC7 on TranslationEntry (companyId, status);
create index IX_A04D7489 on TranslationEntry (groupId, status);
create index IX_E0191679 on TranslationEntry (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_7859444B on TranslationEntry (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_D4FA3BB on TranslationEntry (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);