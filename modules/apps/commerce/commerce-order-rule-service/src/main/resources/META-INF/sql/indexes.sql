create index IX_E0154022 on COREntry (companyId, active_, type_[$COLUMN_LENGTH:75$]);
create index IX_4BD0EB07 on COREntry (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_FDA23B9F on COREntry (companyId, type_[$COLUMN_LENGTH:75$]);
create index IX_44FA9674 on COREntry (displayDate, status);
create index IX_9CB08889 on COREntry (expirationDate, status);

create index IX_F1A53EE on COREntryRel (COREntryId);
create index IX_27438A76 on COREntryRel (classNameId, COREntryId);
create unique index IX_25C71E83 on COREntryRel (classNameId, classPK, COREntryId);