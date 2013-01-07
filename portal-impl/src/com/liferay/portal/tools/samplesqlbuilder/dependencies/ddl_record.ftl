<#setting number_format = "0">

<#assign createDate = dataFactory.getDateString(ddlRecord.createDate)>

<#assign ddmContent = dataFactory.addDDMContent(ddlRecord.groupId, ddlRecord.companyId, ddlRecord.userId)>

insert into DDMContent values ('${portalUUIDUtil.generate()}', ${ddmContent.contentId}, ${ddmContent.groupId}, ${ddmContent.companyId}, ${ddmContent.userId}, '', '${createDate}', '${createDate}', 'com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink', '', '<?xml version="1.0"?><root><dynamic-element name="text2102"><dynamic-content><![CDATA[Test Record ${ddlRecordCount}]]></dynamic-content></dynamic-element></root>');

insert into DDLRecord values ('${portalUUIDUtil.generate()}', ${ddlRecord.recordId}, ${ddlRecord.groupId}, ${ddlRecord.companyId}, ${ddlRecord.userId}, '', ${ddlRecord.userId}, '', '${createDate}', '${createDate}', ${ddmContent.contentId}, ${ddlRecord.recordSetId}, '1.0', 0);

<#assign ddlRecordVersion = dataFactory.addDDLRecordVersion(ddlRecord)>

insert into DDLRecordVersion values (${ddlRecordVersion.recordVersionId}, ${ddlRecordVersion.groupId}, ${ddlRecordVersion.companyId}, ${ddlRecordVersion.userId}, '', '${createDate}', ${ddmContent.contentId}, ${ddlRecordVersion.recordSetId}, ${ddlRecordVersion.recordId}, '1.0', 0, 0, ${ddlRecordVersion.userId}, '', '${createDate}');

<#assign ddmStorageLink = dataFactory.addDDMStorageLink(dataFactory.DDMContentClassName.classNameId, ddmContent.contentId, ddlRecordSet.DDMStructureId)>

insert into DDMStorageLink values ('${portalUUIDUtil.generate()}', ${ddmStorageLink.storageLinkId}, ${dataFactory.DDMContentClassName.classNameId}, ${ddmContent.contentId}, ${ddmStorageLink.structureId});