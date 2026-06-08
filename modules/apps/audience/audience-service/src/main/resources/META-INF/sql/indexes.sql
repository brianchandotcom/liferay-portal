create unique index IX_C1DF1009 on AudienceEntry (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_D278B73 on AudienceEntry (companyId, name[$COLUMN_LENGTH:75$]);
create index IX_2305E40 on AudienceEntry (uuid_[$COLUMN_LENGTH:75$]);