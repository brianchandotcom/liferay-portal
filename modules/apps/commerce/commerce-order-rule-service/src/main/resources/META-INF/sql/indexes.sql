create index IX_E0154022 on COREntry (companyId, active_, type_[$COLUMN_LENGTH:75$]);
create unique index IX_4BD0EB07 on COREntry (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_FDA23B9F on COREntry (companyId, type_[$COLUMN_LENGTH:75$]);
create index IX_8599BE68 on COREntry (status, displayDate);
create index IX_4AFDBD89 on COREntry (status, expirationDate);
create index IX_DD753A02 on COREntry (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_EA6EFFC3 on COREntryRel (COREntryId, classNameId, classPK);