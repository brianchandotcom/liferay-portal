create index IX_8DE7A0B5 on CPAttachmentFileEntry (classNameId, classPK, cdnURL[$COLUMN_LENGTH:4000$], ctCollectionId);
create index IX_E53AD4BF on CPAttachmentFileEntry (classNameId, classPK, ctCollectionId);
create index IX_68966943 on CPAttachmentFileEntry (classNameId, classPK, displayDate, status, ctCollectionId);
create index IX_7C51979E on CPAttachmentFileEntry (classNameId, classPK, fileEntryId, ctCollectionId);
create index IX_EDBD5798 on CPAttachmentFileEntry (classNameId, classPK, type_, status, ctCollectionId);
create index IX_6B55EC7F on CPAttachmentFileEntry (companyId, externalReferenceCode[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_8AF22978 on CPAttachmentFileEntry (displayDate, status, ctCollectionId);
create index IX_2DC4AC5E on CPAttachmentFileEntry (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_5C706B86 on CPAttachmentFileEntry (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_50416EE0 on CPAttachmentFileEntry (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_DDD44E12 on CPDSpecificationOptionValue (CPDefinitionId, CPOptionCategoryId, ctCollectionId);
create index IX_5A0422EF on CPDSpecificationOptionValue (CPDefinitionId, CPSpecificationOptionId, ctCollectionId);
create index IX_DC39F343 on CPDSpecificationOptionValue (CPDefinitionId, ctCollectionId);
create index IX_D71FC803 on CPDSpecificationOptionValue (CPOptionCategoryId, ctCollectionId);
create index IX_8D3C379E on CPDSpecificationOptionValue (CPSpecificationOptionId, ctCollectionId);
create index IX_D7759E72 on CPDSpecificationOptionValue (groupId, ctCollectionId);
create index IX_3AE7C028 on CPDSpecificationOptionValue (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_DB33D1FC on CPDSpecificationOptionValue (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_99CAED2A on CPDSpecificationOptionValue (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_44AA747F on CPDefinition (CPTaxCategoryId, ctCollectionId);
create index IX_B656C4C5 on CPDefinition (CProductId, status, ctCollectionId);
create index IX_BF597105 on CPDefinition (CProductId, version, ctCollectionId);
create index IX_96B8960 on CPDefinition (companyId, ctCollectionId);
create index IX_7E0EA75E on CPDefinition (displayDate, status, ctCollectionId);
create index IX_4A9FA462 on CPDefinition (groupId, ctCollectionId);
create index IX_21A31348 on CPDefinition (groupId, status, ctCollectionId);
create index IX_7453A36E on CPDefinition (groupId, subscriptionEnabled, ctCollectionId);
create index IX_2B0D6838 on CPDefinition (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_380693EC on CPDefinition (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_EC5B593A on CPDefinition (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create unique index IX_3A90EAC9 on CPDefinitionLink (CPDefinitionId, CProductId, type_[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_CF092CED on CPDefinitionLink (CPDefinitionId, ctCollectionId);
create index IX_107FF0C4 on CPDefinitionLink (CPDefinitionId, type_[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_1AF0CD79 on CPDefinitionLink (CProductId, ctCollectionId);
create index IX_6DE8DAB8 on CPDefinitionLink (CProductId, type_[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_B6CF3752 on CPDefinitionLink (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_2E0F3112 on CPDefinitionLink (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_8C9A26D4 on CPDefinitionLink (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_42A41BCC on CPDefinitionLocalization (CPDefinitionId, ctCollectionId);
create unique index IX_CB617913 on CPDefinitionLocalization (CPDefinitionId, languageId[$COLUMN_LENGTH:75$], ctCollectionId);

create unique index IX_170B5E88 on CPDefinitionOptionRel (CPDefinitionId, CPOptionId, ctCollectionId);
create index IX_2922ED7 on CPDefinitionOptionRel (CPDefinitionId, ctCollectionId);
create unique index IX_A1C589EB on CPDefinitionOptionRel (CPDefinitionId, key_[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_7655806A on CPDefinitionOptionRel (CPDefinitionId, required, ctCollectionId);
create index IX_9FA63A49 on CPDefinitionOptionRel (CPDefinitionId, skuContributor, ctCollectionId);
create index IX_8319175C on CPDefinitionOptionRel (companyId, ctCollectionId);
create index IX_9D10815E on CPDefinitionOptionRel (groupId, ctCollectionId);
create index IX_2C1F66BC on CPDefinitionOptionRel (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_9C27FE8 on CPDefinitionOptionRel (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_C02328BE on CPDefinitionOptionRel (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_93B5A64A on CPDefinitionOptionValueRel (CPDefinitionOptionRelId, ctCollectionId);
create unique index IX_55A05F1E on CPDefinitionOptionValueRel (CPDefinitionOptionRelId, key_[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_604164E0 on CPDefinitionOptionValueRel (CPDefinitionOptionRelId, preselected, ctCollectionId);
create index IX_F817D0D2 on CPDefinitionOptionValueRel (CPInstanceUuid[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_9D151163 on CPDefinitionOptionValueRel (companyId, ctCollectionId);
create index IX_B0015125 on CPDefinitionOptionValueRel (groupId, ctCollectionId);
create index IX_99751335 on CPDefinitionOptionValueRel (key_[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_AEBE66D5 on CPDefinitionOptionValueRel (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_4462956F on CPDefinitionOptionValueRel (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_52855B17 on CPDefinitionOptionValueRel (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_C9141A18 on CPDisplayLayout (classNameId, classPK, ctCollectionId);
create unique index IX_7F728A18 on CPDisplayLayout (groupId, classNameId, classPK, ctCollectionId);
create index IX_AF3A92A7 on CPDisplayLayout (groupId, classNameId, ctCollectionId);
create index IX_E415FB83 on CPDisplayLayout (groupId, ctCollectionId);
create index IX_6D90DD3C on CPDisplayLayout (groupId, layoutUuid[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_49408637 on CPDisplayLayout (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_EFBA3F4D on CPDisplayLayout (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_CC6BEAF9 on CPDisplayLayout (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create unique index IX_31443E86 on CPInstance (CPDefinitionId, CPInstanceUuid[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_18D34D55 on CPInstance (CPDefinitionId, ctCollectionId);
create index IX_65D44A6D on CPInstance (CPDefinitionId, displayDate, status, ctCollectionId);
create unique index IX_2D902FD4 on CPInstance (CPDefinitionId, sku[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_B2A4193B on CPInstance (CPDefinitionId, status, ctCollectionId);
create index IX_7BF6BCF7 on CPInstance (CPInstanceUuid[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_3C22621E on CPInstance (companyId, ctCollectionId);
create index IX_18ADE5DB on CPInstance (companyId, externalReferenceCode[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_3B8BB0AB on CPInstance (companyId, sku[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_BDD42A9C on CPInstance (displayDate, status, ctCollectionId);
create index IX_921194A0 on CPInstance (groupId, ctCollectionId);
create index IX_78B7E586 on CPInstance (groupId, status, ctCollectionId);
create index IX_845761A5 on CPInstance (sku[$COLUMN_LENGTH:75$]);
create index IX_B692DDBA on CPInstance (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_F137BAA on CPInstance (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_D664473C on CPInstance (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create unique index IX_E45EE045 on CPInstanceOptionValueRel (CPDefinitionOptionRelId, CPDefinitionOptionValueRelId, CPInstanceId, ctCollectionId);
create index IX_29EC9619 on CPInstanceOptionValueRel (CPDefinitionOptionRelId, CPInstanceId, ctCollectionId);
create index IX_4B841608 on CPInstanceOptionValueRel (CPDefinitionOptionRelId, ctCollectionId);
create index IX_B330CBE0 on CPInstanceOptionValueRel (CPDefinitionOptionValueRelId, CPInstanceId, ctCollectionId);
create index IX_C9D432F4 on CPInstanceOptionValueRel (CPInstanceId, ctCollectionId);
create index IX_167F10D7 on CPInstanceOptionValueRel (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_842818AD on CPInstanceOptionValueRel (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_BF0C9D99 on CPInstanceOptionValueRel (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_262058AD on CPMeasurementUnit (companyId, ctCollectionId);
create unique index IX_7AC67B41 on CPMeasurementUnit (companyId, key_[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_583ED653 on CPMeasurementUnit (companyId, primary_, type_, ctCollectionId);
create index IX_9CF40D04 on CPMeasurementUnit (companyId, type_, ctCollectionId);
create index IX_6DDC384B on CPMeasurementUnit (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_30F911B9 on CPMeasurementUnit (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_5EB5120D on CPMeasurementUnit (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_65CC42FE on CPOption (companyId, ctCollectionId);
create index IX_FF4508FB on CPOption (companyId, externalReferenceCode[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_3D33A4D2 on CPOption (companyId, key_[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_F91440DA on CPOption (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_4B90CC8A on CPOption (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);

create index IX_387A00A0 on CPOptionCategory (companyId, ctCollectionId);
create unique index IX_D9120F4 on CPOptionCategory (companyId, key_[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_346648F8 on CPOptionCategory (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_B572AB2C on CPOptionCategory (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);

create index IX_DEFBE164 on CPOptionValue (CPOptionId, ctCollectionId);
create unique index IX_3705EB8 on CPOptionValue (CPOptionId, key_[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_29F60E11 on CPOptionValue (companyId, ctCollectionId);
create index IX_8D647888 on CPOptionValue (companyId, externalReferenceCode[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_29166A67 on CPOptionValue (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_24A9191D on CPOptionValue (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);

create index IX_CF01C3DE on CPSpecificationOption (CPOptionCategoryId, ctCollectionId);
create index IX_52147135 on CPSpecificationOption (companyId, ctCollectionId);
create unique index IX_8F980DC9 on CPSpecificationOption (companyId, key_[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_F33CF6C3 on CPSpecificationOption (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_999EAE41 on CPSpecificationOption (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);

create index IX_ADBC7164 on CPTaxCategory (companyId, ctCollectionId);
create index IX_79A007D5 on CPTaxCategory (companyId, externalReferenceCode[$COLUMN_LENGTH:75$], ctCollectionId);

create index IX_10C033E5 on CProduct (companyId, externalReferenceCode[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_BF7F9F56 on CProduct (groupId, ctCollectionId);
create index IX_4F867FC4 on CProduct (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_36D0BBE0 on CProduct (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_F70CE3C6 on CProduct (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_7D2B5F22 on CommerceCatalog (companyId, ctCollectionId);
create index IX_A8DE8457 on CommerceCatalog (companyId, externalReferenceCode[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_B053A95A on CommerceCatalog (companyId, system_, ctCollectionId);

create index IX_6A59A278 on CommerceChannel (companyId, ctCollectionId);
create index IX_D8DAE041 on CommerceChannel (companyId, externalReferenceCode[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_A12DD9F3 on CommerceChannel (siteGroupId, ctCollectionId);

create unique index IX_4469A625 on CommerceChannelRel (classNameId, classPK, commerceChannelId, ctCollectionId);
create index IX_CFD266EA on CommerceChannelRel (classNameId, classPK, ctCollectionId);
create index IX_90EE555A on CommerceChannelRel (commerceChannelId, ctCollectionId);