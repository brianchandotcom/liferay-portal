<#setting number_format = "0">

insert into DDLRecord values ('${ddlRecord.uuid}', ${ddlRecord.recordId}, ${ddlRecord.groupId}, ${ddlRecord.companyId}, ${ddlRecord.userId}, '${ddlRecord.userName}', ${ddlRecord.versionUserId}, '${ddlRecord.versionUserName}', '${dataFactory.getDateString(ddlRecord.createDate)}', '${dataFactory.getDateString(ddlRecord.modifiedDate)}', ${ddlRecord.DDMStorageId}, ${ddlRecord.recordSetId}, '${ddlRecord.version}', ${ddlRecord.displayIndex});

<#assign ddlRecordVersion = dataFactory.newDDLRecordVersion(ddlRecord)>

insert into DDLRecordVersion values (${ddlRecordVersion.recordVersionId}, ${ddlRecordVersion.groupId}, ${ddlRecordVersion.companyId}, ${ddlRecordVersion.userId}, '${ddlRecordVersion.userName}', '${dataFactory.getDateString(ddlRecordVersion.createDate)}', ${ddlRecordVersion.DDMStorageId}, ${ddlRecordVersion.recordSetId}, ${ddlRecordVersion.recordId}, '${ddlRecordVersion.version}', ${ddlRecordVersion.displayIndex}, ${ddlRecordVersion.status}, ${ddlRecordVersion.statusByUserId}, '${ddlRecordVersion.statusByUserName}', '${dataFactory.getDateString(ddlRecordVersion.statusDate)}');

<#assign ddmContent = dataFactory.newDDMContent(ddlRecord, ddlRecordCount)>

insert into DDMContent values ('${ddmContent.uuid}', ${ddmContent.contentId}, ${ddmContent.groupId}, ${ddmContent.companyId}, ${ddmContent.userId}, '${ddmContent.userName}', '${dataFactory.getDateString(ddmContent.createDate)}', '${dataFactory.getDateString(ddmContent.modifiedDate)}', '${ddmContent.name}', '${ddmContent.description}', '${ddmContent.xml}');

<#assign ddmStorageLink = dataFactory.newDDMStorageLink(ddmContent, ddmStructureId)>

insert into DDMStorageLink values ('${ddmStorageLink.uuid}', ${ddmStorageLink.storageLinkId}, ${ddmStorageLink.classNameId}, ${ddmStorageLink.classPK}, ${ddmStorageLink.structureId});