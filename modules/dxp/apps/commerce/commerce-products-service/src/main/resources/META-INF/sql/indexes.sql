create index IX_B397F21B on CommerceProductDefinition (companyId);
create index IX_431C435D on CommerceProductDefinition (groupId);
create index IX_91058CA1 on CommerceProductDefinition (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_E61AC763 on CommerceProductDefinition (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_3E2BF9EA on CommerceProductInstance (commerceProductDefinitionId, SKU[$COLUMN_LENGTH:75$]);
create index IX_17E9F919 on CommerceProductInstance (companyId);
create index IX_399FF1DB on CommerceProductInstance (groupId);
create index IX_64EB4BE3 on CommerceProductInstance (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_538B6F25 on CommerceProductInstance (uuid_[$COLUMN_LENGTH:75$], groupId);