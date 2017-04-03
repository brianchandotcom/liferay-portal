create index IX_B397F21B on CommerceProductDefinition (companyId);
create index IX_431C435D on CommerceProductDefinition (groupId);
create index IX_91058CA1 on CommerceProductDefinition (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_E61AC763 on CommerceProductDefinition (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_C91CF2C8 on CommerceProductDefintionOptionRel (companyId);
create index IX_9538B4A on CommerceProductDefintionOptionRel (groupId);

create index IX_FC45FD7B on CommerceProductDefintionOptionValueRel (companyId);
create index IX_495A6BD on CommerceProductDefintionOptionValueRel (groupId);

create unique index IX_3FFDEE0A on CommerceProductInstance (commerceProductDefinitionId, sku[$COLUMN_LENGTH:75$]);
create index IX_17E9F919 on CommerceProductInstance (companyId);
create index IX_399FF1DB on CommerceProductInstance (groupId);
create index IX_64EB4BE3 on CommerceProductInstance (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_538B6F25 on CommerceProductInstance (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_4E8F1C39 on CommerceProductOption (companyId);
create index IX_97735CFB on CommerceProductOption (groupId);

create index IX_48B2A66 on CommerceProductOptionValue (commerceProductOptionId);
create index IX_7AE165BA on CommerceProductOptionValue (companyId);
create index IX_B1C12BC on CommerceProductOptionValue (groupId);