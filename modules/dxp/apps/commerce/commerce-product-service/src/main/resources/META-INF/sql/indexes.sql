create index IX_B397F21B on CommerceProductDefinition (companyId);
create index IX_431C435D on CommerceProductDefinition (groupId);
create index IX_91058CA1 on CommerceProductDefinition (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_E61AC763 on CommerceProductDefinition (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_D56F8C13 on CommerceProductDefinitionLocalization (commerceProductDefinitionPK, languageId[$COLUMN_LENGTH:75$]);

create index IX_FBE6E1CF on CommerceProductDefinitionOptionRel (commerceProductDefinitionId);
create index IX_2F156E05 on CommerceProductDefinitionOptionRel (companyId);
create index IX_A8BBB1C7 on CommerceProductDefinitionOptionRel (groupId);

create index IX_DD7A005E on CommerceProductDefinitionOptionValueRel (companyId);
create index IX_972FABB2 on CommerceProductDefinitionOptionValueRel (definitionOptionRelId);
create index IX_73A30660 on CommerceProductDefinitionOptionValueRel (groupId);

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