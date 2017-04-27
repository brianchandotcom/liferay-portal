create index IX_217AF702 on CPDefinition (companyId);
create index IX_73F95604 on CPDefinition (groupId);
create index IX_8EA585DA on CPDefinition (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_BA9BADC on CPDefinition (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_9D876175 on CPDefinitionLocalization (cpDefinitionPK, languageId[$COLUMN_LENGTH:75$]);

create index IX_749E99EB on CPDefinitionOptionRel (CPDefinitionId, skuContributor);
create index IX_449BFCFE on CPDefinitionOptionRel (companyId);
create index IX_A65BAB00 on CPDefinitionOptionRel (groupId);
create index IX_7BED0C5E on CPDefinitionOptionRel (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_EB691260 on CPDefinitionOptionRel (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_80E027EC on CPDefinitionOptionValueRel (CPDefinitionOptionRelId);
create index IX_44C2E505 on CPDefinitionOptionValueRel (companyId);
create index IX_695AE8C7 on CPDefinitionOptionValueRel (groupId);
create index IX_CD95E77 on CPDefinitionOptionValueRel (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_34516B9 on CPDefinitionOptionValueRel (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_7E830576 on CPInstance (CPDefinitionId, sku[$COLUMN_LENGTH:75$]);
create index IX_48C70BC0 on CPInstance (companyId);
create index IX_C1F8242 on CPInstance (groupId);
create index IX_8A7A3F5C on CPInstance (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_F902ECDE on CPInstance (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_210EACA0 on CPOption (companyId);
create index IX_5999DB22 on CPOption (groupId);
create index IX_C565E27C on CPOption (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_BABCD7FE on CPOption (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_6223D706 on CPOptionValue (CPOptionId);
create index IX_C95EFDB3 on CPOptionValue (companyId);
create index IX_F5E154F5 on CPOptionValue (groupId);
create index IX_17FEC609 on CPOptionValue (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_1D633ACB on CPOptionValue (uuid_[$COLUMN_LENGTH:75$], groupId);