create index IX_4E725857 on CPAttachmentFileEntry (classNameId, classPK, cdnURL[$COLUMN_LENGTH:4000$]);
create index IX_B2AFFCE5 on CPAttachmentFileEntry (classNameId, classPK, displayDate, status);
create index IX_DD114140 on CPAttachmentFileEntry (classNameId, classPK, fileEntryId);
create index IX_A6E0353A on CPAttachmentFileEntry (classNameId, classPK, type_, status);
create index IX_59F57821 on CPAttachmentFileEntry (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_A0B4C71A on CPAttachmentFileEntry (displayDate, status);
create index IX_C2C5D600 on CPAttachmentFileEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_BFCBDC82 on CPAttachmentFileEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_95975FB4 on CPDSpecificationOptionValue (CPDefinitionId, CPOptionCategoryId);
create index IX_173E8E91 on CPDSpecificationOptionValue (CPDefinitionId, CPSpecificationOptionId);
create index IX_4F4EDBA5 on CPDSpecificationOptionValue (CPOptionCategoryId);
create index IX_573BE140 on CPDSpecificationOptionValue (CPSpecificationOptionId);
create index IX_8DA57014 on CPDSpecificationOptionValue (groupId);
create index IX_508DBDCA on CPDSpecificationOptionValue (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_551F2ECC on CPDSpecificationOptionValue (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_3D5A0021 on CPDefinition (CPTaxCategoryId);
create index IX_1F4B9C67 on CPDefinition (CProductId, status);
create index IX_F1AEC8A7 on CPDefinition (CProductId, version);
create index IX_217AF702 on CPDefinition (companyId);
create index IX_A465D100 on CPDefinition (displayDate, status);
create index IX_419350EA on CPDefinition (groupId, status);
create index IX_99C4ED10 on CPDefinition (groupId, subscriptionEnabled);
create index IX_8EA585DA on CPDefinition (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_BA9BADC on CPDefinition (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_7FA4A6B on CPDefinitionLink (CPDefinitionId, CProductId, type_[$COLUMN_LENGTH:75$]);
create index IX_5572A666 on CPDefinitionLink (CPDefinitionId, type_[$COLUMN_LENGTH:75$]);
create index IX_F7B5F85A on CPDefinitionLink (CProductId, type_[$COLUMN_LENGTH:75$]);
create index IX_220CC8F4 on CPDefinitionLink (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_865AFC76 on CPDefinitionLink (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_F4B4ACB5 on CPDefinitionLocalization (CPDefinitionId, languageId[$COLUMN_LENGTH:75$]);

create unique index IX_E57A1C2A on CPDefinitionOptionRel (CPDefinitionId, CPOptionId);
create unique index IX_75456D8D on CPDefinitionOptionRel (CPDefinitionId, key_[$COLUMN_LENGTH:75$]);
create index IX_BDB8420C on CPDefinitionOptionRel (CPDefinitionId, required);
create index IX_749E99EB on CPDefinitionOptionRel (CPDefinitionId, skuContributor);
create index IX_449BFCFE on CPDefinitionOptionRel (companyId);
create index IX_A65BAB00 on CPDefinitionOptionRel (groupId);
create index IX_7BED0C5E on CPDefinitionOptionRel (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_EB691260 on CPDefinitionOptionRel (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_8FDF08C0 on CPDefinitionOptionValueRel (CPDefinitionOptionRelId, key_[$COLUMN_LENGTH:75$]);
create index IX_4A77D282 on CPDefinitionOptionValueRel (CPDefinitionOptionRelId, preselected);
create index IX_3EB86274 on CPDefinitionOptionValueRel (CPInstanceUuid[$COLUMN_LENGTH:75$]);
create index IX_44C2E505 on CPDefinitionOptionValueRel (companyId);
create index IX_695AE8C7 on CPDefinitionOptionValueRel (groupId);
create index IX_2434CAD7 on CPDefinitionOptionValueRel (key_[$COLUMN_LENGTH:75$]);
create index IX_CD95E77 on CPDefinitionOptionValueRel (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_34516B9 on CPDefinitionOptionValueRel (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_290BF7BA on CPDisplayLayout (classNameId, classPK);
create unique index IX_CA4A67BA on CPDisplayLayout (groupId, classNameId, classPK);
create index IX_381B82DE on CPDisplayLayout (groupId, layoutUuid[$COLUMN_LENGTH:75$]);
create index IX_EEFA81D9 on CPDisplayLayout (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_68BBAA9B on CPDisplayLayout (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_25BEB828 on CPInstance (CPDefinitionId, CPInstanceUuid[$COLUMN_LENGTH:75$]);
create index IX_C399720F on CPInstance (CPDefinitionId, displayDate, status);
create unique index IX_7E830576 on CPInstance (CPDefinitionId, sku[$COLUMN_LENGTH:75$]);
create index IX_F4C9CDD on CPInstance (CPDefinitionId, status);
create index IX_34763899 on CPInstance (CPInstanceUuid[$COLUMN_LENGTH:75$]);
create index IX_E2C3A97D on CPInstance (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_7C65903E on CPInstance (displayDate, status);
create index IX_FF605F28 on CPInstance (groupId, status);
create index IX_8A7A3F5C on CPInstance (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_F902ECDE on CPInstance (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_4BFAB7E7 on CPInstanceOptionValueRel (CPDefinitionOptionRelId, CPDefinitionOptionValueRelId, CPInstanceId);
create index IX_F63695BB on CPInstanceOptionValueRel (CPDefinitionOptionRelId, CPInstanceId);
create index IX_1F153982 on CPInstanceOptionValueRel (CPDefinitionOptionValueRelId, CPInstanceId);
create index IX_2C714896 on CPInstanceOptionValueRel (CPInstanceId);
create index IX_F6E24C79 on CPInstanceOptionValueRel (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_AF559D3B on CPInstanceOptionValueRel (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_BCD82992 on CPMeasurementUnit (companyId, key_[$COLUMN_LENGTH:75$], type_);
create index IX_107489F5 on CPMeasurementUnit (companyId, primary_, type_);
create index IX_1C2742A6 on CPMeasurementUnit (companyId, type_);
create index IX_E244DBED on CPMeasurementUnit (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_753D79AF on CPMeasurementUnit (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_25450C9D on CPOption (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create unique index IX_BDFC3674 on CPOption (companyId, key_[$COLUMN_LENGTH:75$]);
create index IX_C565E27C on CPOption (uuid_[$COLUMN_LENGTH:75$], companyId);

create unique index IX_33CA3696 on CPOptionCategory (companyId, key_[$COLUMN_LENGTH:75$]);
create index IX_957E69A on CPOptionCategory (uuid_[$COLUMN_LENGTH:75$], companyId);

create unique index IX_F4C57C5A on CPOptionValue (CPOptionId, key_[$COLUMN_LENGTH:75$]);
create index IX_5547362A on CPOptionValue (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_17FEC609 on CPOptionValue (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_421ED80 on CPSpecificationOption (CPOptionCategoryId);
create unique index IX_1DA76D6B on CPSpecificationOption (companyId, key_[$COLUMN_LENGTH:75$]);
create index IX_5B218A65 on CPSpecificationOption (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_E91CFF77 on CPTaxCategory (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);

create index IX_7BB74B87 on CProduct (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_77F5B8F8 on CProduct (groupId);
create index IX_39773566 on CProduct (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_9F9DD68 on CProduct (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_1198BFF9 on CommerceCatalog (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_65864AFC on CommerceCatalog (companyId, system_);

create index IX_690E2FE3 on CommerceChannel (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_E1ECD95 on CommerceChannel (siteGroupId);

create unique index IX_3B0D3DC7 on CommerceChannelRel (classNameId, classPK, commerceChannelId);
create index IX_48F8F6FC on CommerceChannelRel (commerceChannelId);