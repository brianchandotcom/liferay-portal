<#setting number_format = "0">

insert into DDLRecord values ('${ddlRecord.uuid}', ${ddlRecord.recordId}, ${ddlRecord.groupId}, ${ddlRecord.companyId}, ${ddlRecord.userId}, '${ddlRecord.userName}', ${ddlRecord.versionUserId}, '${ddlRecord.versionUserName}', '${ddlRecord.createDate?datetime}', '${ddlRecord.modifiedDate?datetime}', ${ddlRecord.DDMStorageId}, ${ddlRecord.recordSetId}, '${ddlRecord.version}', ${ddlRecord.displayIndex});

<#assign ddmContent = dataFactory.addDDMContent(ddlRecord, ddlRecordCount)>

insert into DDMContent values ('${ddmContent.uuid}', ${ddmContent.contentId}, ${ddmContent.groupId}, ${ddmContent.companyId}, ${ddmContent.userId}, '${ddmContent.userName}', '${ddmContent.createDate?datetime}', '${ddmContent.modifiedDate?datetime}', '${ddmContent.name}', '${ddmContent.description}', '${ddmContent.xml}');

<#assign ddlRecordVersion = dataFactory.addDDLRecordVersion(ddlRecord)>

insert into DDLRecordVersion values (${ddlRecordVersion.recordVersionId}, ${ddlRecordVersion.groupId}, ${ddlRecordVersion.companyId}, ${ddlRecordVersion.userId}, '${ddlRecordVersion.userName}', '${ddlRecordVersion.createDate?datetime}', ${ddlRecordVersion.DDMStorageId}, ${ddlRecordVersion.recordSetId}, ${ddlRecordVersion.recordId}, '${ddlRecordVersion.version}', ${ddlRecordVersion.displayIndex}, ${ddlRecordVersion.status}, ${ddlRecordVersion.statusByUserId}, '${ddlRecordVersion.statusByUserName}', ${ddlRecordVersion.statusDate!'null'});

<#assign ddmStorageLink = dataFactory.addDDMStorageLink(ddmContent, ddmStructureId)>

insert into DDMStorageLink values ('${ddmStorageLink.uuid}', ${ddmStorageLink.storageLinkId}, ${ddmStorageLink.classNameId}, ${ddmStorageLink.classPK}, ${ddmStorageLink.structureId});