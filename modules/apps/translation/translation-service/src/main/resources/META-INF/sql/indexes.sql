create unique index IX_3EF04143 on TranslationEntry (classNameId, classPK, languageId[$COLUMN_LENGTH:75$]);
create index IX_581ACBC7 on TranslationEntry (companyId, status);
create index IX_A04D7489 on TranslationEntry (groupId, status);
create index IX_72FD61B on TranslationEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_A35D275D on TranslationEntry (uuid_[$COLUMN_LENGTH:75$], groupId);