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

create index IX_31817A62 on DDMStructure (classNameId);
create index IX_4FBAC092 on DDMStructure (companyId, classNameId);
create unique index IX_C8785130 on DDMStructure (groupId, classNameId, structureKey[$COLUMN_LENGTH:75$]);
create index IX_43395316 on DDMStructure (groupId, parentStructureId);
create index IX_657899A8 on DDMStructure (parentStructureId);
create index IX_20FDE04C on DDMStructure (structureKey[$COLUMN_LENGTH:75$]);
create index IX_F9FB8D60 on DDMStructure (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_85C7EBE2 on DDMStructure (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_B7158C0A on DDMStructureLayout (structureVersionId);
create index IX_A90FF72A on DDMStructureLayout (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_C9A0402C on DDMStructureLayout (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_E43143A3 on DDMStructureLink (classNameId, classPK, structureId);
create index IX_17692B58 on DDMStructureLink (structureId);

create index IX_17B3C96C on DDMStructureVersion (structureId, status);
create unique index IX_64C3C42 on DDMStructureVersion (structureId, version[$COLUMN_LENGTH:75$]);

create index IX_B6356F93 on DDMTemplate (classNameId, classPK, type_[$COLUMN_LENGTH:75$]);
create index IX_32F83D16 on DDMTemplate (classPK);
create index IX_F0C3449 on DDMTemplate (groupId, classNameId, classPK, type_[$COLUMN_LENGTH:75$], mode_[$COLUMN_LENGTH:75$]);
create unique index IX_E6DFAB84 on DDMTemplate (groupId, classNameId, templateKey[$COLUMN_LENGTH:75$]);
create index IX_B1C33EA6 on DDMTemplate (groupId, classPK);
create index IX_33BEF579 on DDMTemplate (language[$COLUMN_LENGTH:75$]);
create index IX_127A35B0 on DDMTemplate (smallImageId);
create index IX_CAE41A28 on DDMTemplate (templateKey[$COLUMN_LENGTH:75$]);
create index IX_C4F283C8 on DDMTemplate (type_[$COLUMN_LENGTH:75$]);
create index IX_D4C2C221 on DDMTemplate (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_1AA75CE3 on DDMTemplate (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_6F3B3E9C on DDMTemplateLink (classNameId, classPK);
create index IX_85278170 on DDMTemplateLink (templateId);

create index IX_66382FC6 on DDMTemplateVersion (templateId, status);
create unique index IX_8854A128 on DDMTemplateVersion (templateId, version[$COLUMN_LENGTH:75$]);