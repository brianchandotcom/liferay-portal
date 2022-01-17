create index IX_E2B5E483 on CommerceTermEntry (companyId, active_, type_[$COLUMN_LENGTH:75$]);
create index IX_B241C786 on CommerceTermEntry (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_9281F61E on CommerceTermEntry (companyId, type_[$COLUMN_LENGTH:75$]);
create index IX_E4825795 on CommerceTermEntry (displayDate, status);
create index IX_539427C8 on CommerceTermEntry (expirationDate, status);