<#assign dlFileEntryType = dataFactory.defaultDLFileEntryType>

insert into DLFileEntryType values ('${dlFileEntryType.uuid}', ${dlFileEntryType.fileEntryTypeId}, ${dlFileEntryType.groupId}, ${dlFileEntryType.companyId}, ${dlFileEntryType.userId}, '${dlFileEntryType.userName}', '${dataFactory.getDateString(dlFileEntryType.createDate)}', '${dataFactory.getDateString(dlFileEntryType.modifiedDate)}', '${dlFileEntryType.fileEntryTypeKey}', '${dlFileEntryType.name}', '${dlFileEntryType.description}');

<#assign ddmStructure = dataFactory.defaultDLDDMStructure>

insert into DDMStructure values ('${ddmStructure.uuid}', ${ddmStructure.structureId}, ${ddmStructure.groupId}, ${ddmStructure.companyId}, ${ddmStructure.userId}, '${ddmStructure.userName}', '${dataFactory.getDateString(ddmStructure.createDate)}', '${dataFactory.getDateString(ddmStructure.modifiedDate)}', ${ddmStructure.parentStructureId}, ${ddmStructure.classNameId}, '${ddmStructure.structureKey}', '${ddmStructure.name}', '${ddmStructure.description}', '${ddmStructure.xsd}', '${ddmStructure.storageType}', ${ddmStructure.type});