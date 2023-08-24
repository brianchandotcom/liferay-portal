create index IX_B0929E94 on DDMContent (companyId, ctCollectionId);
create index IX_CB327696 on DDMContent (groupId, ctCollectionId);
create index IX_C71D084 on DDMContent (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_83D06320 on DDMContent (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_D4156486 on DDMContent (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_96A71343 on DDMDataProviderInstance (companyId, ctCollectionId);
create index IX_FFEA4B05 on DDMDataProviderInstance (groupId, ctCollectionId);
create index IX_F32088F5 on DDMDataProviderInstance (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_400874F on DDMDataProviderInstance (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_B7498537 on DDMDataProviderInstance (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_D8EDC33A on DDMDataProviderInstanceLink (dataProviderInstanceId, ctCollectionId);
create unique index IX_EC5795A0 on DDMDataProviderInstanceLink (dataProviderInstanceId, structureId, ctCollectionId);
create index IX_C304699F on DDMDataProviderInstanceLink (structureId, ctCollectionId);

create index IX_5378BAAD on DDMField (companyId, fieldType[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_582EBFF1 on DDMField (storageId, ctCollectionId);
create unique index IX_1BB20E75 on DDMField (storageId, instanceId[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_5C0B8AE5 on DDMField (structureVersionId, ctCollectionId);

create index IX_52703248 on DDMFieldAttribute (attributeName[$COLUMN_LENGTH:255$], smallAttributeValue[$COLUMN_LENGTH:255$], ctCollectionId);
create unique index IX_22EEBF0C on DDMFieldAttribute (fieldId, attributeName[$COLUMN_LENGTH:255$], languageId[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_36E78464 on DDMFieldAttribute (storageId, attributeName[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_EC62446F on DDMFieldAttribute (storageId, ctCollectionId);
create index IX_1E90C536 on DDMFieldAttribute (storageId, languageId[$COLUMN_LENGTH:75$], ctCollectionId);

create index IX_78F4C5C on DDMFormInstance (groupId, ctCollectionId);
create index IX_382197E on DDMFormInstance (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_72D3F266 on DDMFormInstance (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_EAB7A400 on DDMFormInstance (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_B7FB889 on DDMFormInstanceRecord (companyId, ctCollectionId);
create index IX_F0AFA723 on DDMFormInstanceRecord (formInstanceId, ctCollectionId);
create index IX_78B0C448 on DDMFormInstanceRecord (formInstanceId, formInstanceVersion[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_3C8DBDFF on DDMFormInstanceRecord (formInstanceId, userId);
create index IX_A5B66A5D on DDMFormInstanceRecord (userId, formInstanceId, ctCollectionId);
create index IX_64C688EF on DDMFormInstanceRecord (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_5CDABF95 on DDMFormInstanceRecord (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_90833BB1 on DDMFormInstanceRecord (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_EA8F43DE on DDMFormInstanceRecordVersion (formInstanceId, formInstanceVersion[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_46C6B23E on DDMFormInstanceRecordVersion (formInstanceRecordId, ctCollectionId);
create index IX_EB3C8524 on DDMFormInstanceRecordVersion (formInstanceRecordId, status, ctCollectionId);
create unique index IX_272BBC86 on DDMFormInstanceRecordVersion (formInstanceRecordId, version[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_F4924F07 on DDMFormInstanceRecordVersion (userId, formInstanceId, ctCollectionId);
create index IX_CABC7FCA on DDMFormInstanceRecordVersion (userId, formInstanceId, formInstanceVersion[$COLUMN_LENGTH:75$], status, ctCollectionId);

create index IX_4F6D9746 on DDMFormInstanceReport (formInstanceId, ctCollectionId);

create index IX_822B469E on DDMFormInstanceVersion (formInstanceId, ctCollectionId);
create index IX_AC76B984 on DDMFormInstanceVersion (formInstanceId, status, ctCollectionId);
create unique index IX_8D381426 on DDMFormInstanceVersion (formInstanceId, version[$COLUMN_LENGTH:75$], ctCollectionId);

create unique index IX_6979A733 on DDMStorageLink (classPK, ctCollectionId);
create index IX_5BAF16EE on DDMStorageLink (structureId, ctCollectionId);
create index IX_13E12C80 on DDMStorageLink (structureVersionId, ctCollectionId);
create index IX_981FDA0 on DDMStorageLink (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_9F994F84 on DDMStorageLink (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);

create index IX_988632F0 on DDMStructure (companyId, classNameId, ctCollectionId);
create index IX_B44FCCCA on DDMStructure (ctCollectionId);
create index IX_66F194AE on DDMStructure (groupId, classNameId, ctCollectionId);
create unique index IX_4CFAC78E on DDMStructure (groupId, classNameId, structureKey[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_1AA13A1C on DDMStructure (groupId, ctCollectionId);
create index IX_48373D74 on DDMStructure (groupId, parentStructureId, ctCollectionId);
create index IX_800B2006 on DDMStructure (parentStructureId, ctCollectionId);
create index IX_B1169EAA on DDMStructure (structureKey[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_85FA3BE on DDMStructure (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_49065026 on DDMStructure (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_D1F2BE40 on DDMStructure (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_71693578 on DDMStructureLayout (groupId, classNameId, ctCollectionId);
create unique index IX_BBA9AF0E on DDMStructureLayout (groupId, classNameId, structureLayoutKey[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_BC3A08CC on DDMStructureLayout (groupId, classNameId, structureVersionId, ctCollectionId);
create index IX_60221912 on DDMStructureLayout (groupId, ctCollectionId);
create index IX_8DFAB4AA on DDMStructureLayout (structureLayoutKey[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_8E8B0E68 on DDMStructureLayout (structureVersionId, ctCollectionId);
create index IX_618B3988 on DDMStructureLayout (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_6BFE749C on DDMStructureLayout (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_73BD3E8A on DDMStructureLayout (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_4C181B39 on DDMStructureLink (classNameId, classPK, ctCollectionId);
create unique index IX_C8DE7401 on DDMStructureLink (classNameId, classPK, structureId, ctCollectionId);
create index IX_A2D51B64 on DDMStructureLink (ctCollectionId);
create index IX_FD8251B6 on DDMStructureLink (structureId, ctCollectionId);

create index IX_9B5D9F76 on DDMStructureVersion (ctCollectionId);
create index IX_5F887AE4 on DDMStructureVersion (structureId, ctCollectionId);
create index IX_4E1647CA on DDMStructureVersion (structureId, status, ctCollectionId);
create unique index IX_1F8A4EA0 on DDMStructureVersion (structureId, version[$COLUMN_LENGTH:75$], ctCollectionId);

create index IX_3A6CBFF1 on DDMTemplate (classNameId, classPK, type_[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_40E22774 on DDMTemplate (classPK, ctCollectionId);
create index IX_492F3DCB on DDMTemplate (ctCollectionId);
create index IX_2A228ED0 on DDMTemplate (groupId, classNameId, classPK, ctCollectionId);
create index IX_25F23981 on DDMTemplate (groupId, classNameId, classPK, type_[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_8DFF58A7 on DDMTemplate (groupId, classNameId, classPK, type_[$COLUMN_LENGTH:75$], mode_[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_A967DEEF on DDMTemplate (groupId, classNameId, ctCollectionId);
create unique index IX_ED2AF9E2 on DDMTemplate (groupId, classNameId, templateKey[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_D3180904 on DDMTemplate (groupId, classPK, ctCollectionId);
create index IX_EFDA5A3B on DDMTemplate (groupId, ctCollectionId);
create index IX_3EE9B9D7 on DDMTemplate (language[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_13A3AC0E on DDMTemplate (smallImageId, ctCollectionId);
create index IX_F365A086 on DDMTemplate (templateKey[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_27ACCA26 on DDMTemplate (type_[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_C64F367F on DDMTemplate (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_17F6EC05 on DDMTemplate (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_147A0D41 on DDMTemplate (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create unique index IX_79ED5CFA on DDMTemplateLink (classNameId, classPK, ctCollectionId);
create index IX_E8A223E5 on DDMTemplateLink (ctCollectionId);
create index IX_CFC177CE on DDMTemplateLink (templateId, ctCollectionId);

create index IX_6A4E3B55 on DDMTemplateVersion (ctCollectionId);
create index IX_4E55E73E on DDMTemplateVersion (templateId, ctCollectionId);
create index IX_1EC6BA24 on DDMTemplateVersion (templateId, status, ctCollectionId);
create unique index IX_64E82786 on DDMTemplateVersion (templateId, version[$COLUMN_LENGTH:75$], ctCollectionId);