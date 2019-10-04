create index IX_E3BAF436 on DDMContent (companyId);
create index IX_50BF1038 on DDMContent (groupId);
create index IX_3A9C0626 on DDMContent (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_EB9BDE28 on DDMContent (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_DB54A6E5 on DDMDataProviderInstance (companyId);
create index IX_1333A2A7 on DDMDataProviderInstance (groupId);
create index IX_C903C097 on DDMDataProviderInstance (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_B4E180D9 on DDMDataProviderInstance (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_8C878342 on DDMDataProviderInstanceLink (dataProviderInstanceId, structureId);
create index IX_CB823541 on DDMDataProviderInstanceLink (structureId);

create index IX_9E1C31FE on DDMFormInstance (groupId);
create index IX_E418320 on DDMFormInstance (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_AA9051A2 on DDMFormInstance (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_5BC982B on DDMFormInstanceRecord (companyId);
create index IX_242301EA on DDMFormInstanceRecord (formInstanceId, formInstanceVersion[$COLUMN_LENGTH:75$]);
create index IX_3C8DBDFF on DDMFormInstanceRecord (formInstanceId, userId);
create index IX_E1971FF on DDMFormInstanceRecord (userId, formInstanceId);
create index IX_CF8CF491 on DDMFormInstanceRecord (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_AA3B6B53 on DDMFormInstanceRecord (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_EAAF6D80 on DDMFormInstanceRecordVersion (formInstanceId, formInstanceVersion[$COLUMN_LENGTH:75$]);
create index IX_B5A3FAC6 on DDMFormInstanceRecordVersion (formInstanceRecordId, status);
create unique index IX_26623628 on DDMFormInstanceRecordVersion (formInstanceRecordId, version[$COLUMN_LENGTH:75$]);
create index IX_57CA016C on DDMFormInstanceRecordVersion (userId, formInstanceId, formInstanceVersion[$COLUMN_LENGTH:75$], status);

create index IX_EB92EF26 on DDMFormInstanceVersion (formInstanceId, status);
create unique index IX_AE51CDC8 on DDMFormInstanceVersion (formInstanceId, version[$COLUMN_LENGTH:75$]);

create unique index IX_702D1AD5 on DDMStorageLink (classPK);
create index IX_81776090 on DDMStorageLink (structureId);
create index IX_14DADA22 on DDMStorageLink (structureVersionId);
create index IX_DB81EB42 on DDMStorageLink (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_79BF4CC0 on DDMStructure (classNameId, ctCollectionId);
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

create unique index IX_14E638B0 on DDMStructureLayout (groupId, classNameId, structureLayoutKey[$COLUMN_LENGTH:75$]);
create index IX_C72DCE6E on DDMStructureLayout (groupId, classNameId, structureVersionId);
create index IX_4CDF64C on DDMStructureLayout (structureLayoutKey[$COLUMN_LENGTH:75$]);
create index IX_B7158C0A on DDMStructureLayout (structureVersionId);
create index IX_A90FF72A on DDMStructureLayout (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_C9A0402C on DDMStructureLayout (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_E43143A3 on DDMStructureLink (classNameId, classPK, structureId);
create index IX_17692B58 on DDMStructureLink (structureId);

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

create unique index IX_6F3B3E9C on DDMTemplateLink (classNameId, classPK);
create index IX_85278170 on DDMTemplateLink (templateId);

create index IX_6A4E3B55 on DDMTemplateVersion (ctCollectionId);
create index IX_4E55E73E on DDMTemplateVersion (templateId, ctCollectionId);
create index IX_1EC6BA24 on DDMTemplateVersion (templateId, status, ctCollectionId);
create unique index IX_64E82786 on DDMTemplateVersion (templateId, version[$COLUMN_LENGTH:75$], ctCollectionId);