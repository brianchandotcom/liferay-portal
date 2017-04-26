create index IX_B397F21B on CommerceProductDefinition (companyId);
create index IX_431C435D on CommerceProductDefinition (groupId);
create index IX_91058CA1 on CommerceProductDefinition (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_E61AC763 on CommerceProductDefinition (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_D56F8C13 on CommerceProductDefinitionLocalization (commerceProductDefinitionPK, languageId[$COLUMN_LENGTH:75$]);

create index IX_FCD6A541 on CommerceProductDefinitionOptionRel (commerceProductDefinitionId, skuContributor);
create index IX_2F156E05 on CommerceProductDefinitionOptionRel (companyId);
create index IX_A8BBB1C7 on CommerceProductDefinitionOptionRel (groupId);
create index IX_C34AB577 on CommerceProductDefinitionOptionRel (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_84202DB9 on CommerceProductDefinitionOptionRel (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_DD7A005E on CommerceProductDefinitionOptionValueRel (companyId);
create index IX_972FABB2 on CommerceProductDefinitionOptionValueRel (definitionOptionRelId);
create index IX_73A30660 on CommerceProductDefinitionOptionValueRel (groupId);
create index IX_65467CFE on CommerceProductDefinitionOptionValueRel (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_EFA62B00 on CommerceProductDefinitionOptionValueRel (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_3FFDEE0A on CommerceProductInstance (commerceProductDefinitionId, sku[$COLUMN_LENGTH:75$]);
create index IX_17E9F919 on CommerceProductInstance (companyId);
create index IX_399FF1DB on CommerceProductInstance (groupId);
create index IX_64EB4BE3 on CommerceProductInstance (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_538B6F25 on CommerceProductInstance (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_4E8F1C39 on CommerceProductOption (companyId);
create index IX_97735CFB on CommerceProductOption (groupId);
create index IX_1FEE64C3 on CommerceProductOption (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_6C3F4005 on CommerceProductOption (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_48B2A66 on CommerceProductOptionValue (commerceProductOptionId);
create index IX_7AE165BA on CommerceProductOptionValue (companyId);
create index IX_B1C12BC on CommerceProductOptionValue (groupId);
create index IX_619F9822 on CommerceProductOptionValue (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_9113BF24 on CommerceProductOptionValue (uuid_[$COLUMN_LENGTH:75$], groupId);