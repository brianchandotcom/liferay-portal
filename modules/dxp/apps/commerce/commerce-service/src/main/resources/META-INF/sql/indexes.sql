create index IX_7BB13C80 on CommerceCountry (groupId, active_);
create index IX_91EA24D5 on CommerceCountry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_7EFDC97 on CommerceCountry (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_49C93338 on CommerceRegion (commerceCountryId, active_);
create index IX_3BC85C89 on CommerceRegion (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_DBA0714B on CommerceRegion (uuid_[$COLUMN_LENGTH:75$], groupId);