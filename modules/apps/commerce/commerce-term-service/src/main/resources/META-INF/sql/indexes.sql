create unique index IX_B197F41B on CTermEntryLocalization (commerceTermEntryId, languageId[$COLUMN_LENGTH:75$]);

create index IX_E2B5E483 on CommerceTermEntry (companyId, active_, type_[$COLUMN_LENGTH:75$]);
create unique index IX_B241C786 on CommerceTermEntry (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create unique index IX_2AB59656 on CommerceTermEntry (companyId, name[$COLUMN_LENGTH:75$]);
create unique index IX_CEAD7846 on CommerceTermEntry (companyId, priority, type_[$COLUMN_LENGTH:75$]);
create index IX_9281F61E on CommerceTermEntry (companyId, type_[$COLUMN_LENGTH:75$]);
create index IX_E4825795 on CommerceTermEntry (displayDate, status);
create index IX_539427C8 on CommerceTermEntry (expirationDate, status);
create index IX_589F8F65 on CommerceTermEntry (uuid_[$COLUMN_LENGTH:75$], companyId);

create unique index IX_6C84800D on CommerceTermEntryRel (classNameId, classPK, commerceTermEntryId);
create index IX_E74B1E9C on CommerceTermEntryRel (classNameId, commerceTermEntryId);
create index IX_2B8BBC42 on CommerceTermEntryRel (commerceTermEntryId);