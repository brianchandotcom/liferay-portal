<#include "macro.ftl">

insert into DDLRecord values ('${ddlRecord.uuid}', ${ddlRecord.recordId}, ${ddlRecord.groupId}, ${ddlRecord.companyId}, ${ddlRecord.userId}, '${ddlRecord.userName}', ${ddlRecord.versionUserId}, '${ddlRecord.versionUserName}', '${dataFactory.getDateString(ddlRecord.createDate)}', '${dataFactory.getDateString(ddlRecord.modifiedDate)}', ${ddlRecord.DDMStorageId}, ${ddlRecord.recordSetId}, '${ddlRecord.version}', ${ddlRecord.displayIndex});

<#assign ddlRecordVersion = dataFactory.newDDLRecordVersion(ddlRecord)>

insert into DDLRecordVersion values (${ddlRecordVersion.recordVersionId}, ${ddlRecordVersion.groupId}, ${ddlRecordVersion.companyId}, ${ddlRecordVersion.userId}, '${ddlRecordVersion.userName}', '${dataFactory.getDateString(ddlRecordVersion.createDate)}', ${ddlRecordVersion.DDMStorageId}, ${ddlRecordVersion.recordSetId}, ${ddlRecordVersion.recordId}, '${ddlRecordVersion.version}', ${ddlRecordVersion.displayIndex}, ${ddlRecordVersion.status}, ${ddlRecordVersion.statusByUserId}, '${ddlRecordVersion.statusByUserName}', '${dataFactory.getDateString(ddlRecordVersion.statusDate)}');

<@insertDDMContent _ddmStorageLinkId = counter.get() _ddmStructureId = ddmStructureId _entry = ddlRecord _currentIndex = ddlRecordCount />