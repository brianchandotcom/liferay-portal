create unique index IX_B197F41B on CTermEntryLocalization (commerceTermEntryId, languageId[$COLUMN_LENGTH:75$]);

create index IX_E73B0D12 on CommerceTermEntry (companyId, active_);
create unique index IX_B241C786 on CommerceTermEntry (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create unique index IX_2AB59656 on CommerceTermEntry (companyId, name[$COLUMN_LENGTH:75$]);
create index IX_E90D7AAB on CommerceTermEntry (companyId, type_[$COLUMN_LENGTH:75$], active_);
create unique index IX_F91A2436 on CommerceTermEntry (companyId, type_[$COLUMN_LENGTH:75$], priority);
create index IX_25217F89 on CommerceTermEntry (status, displayDate);
create index IX_1E15CC8 on CommerceTermEntry (status, expirationDate);
create index IX_7C4118E3 on CommerceTermEntry (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_2AA8B117 on CommerceTermEntryRel (commerceTermEntryId, classNameId, classPK);