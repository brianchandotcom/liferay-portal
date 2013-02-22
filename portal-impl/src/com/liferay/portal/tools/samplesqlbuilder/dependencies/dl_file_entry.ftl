<#setting number_format = "0">

<#assign createDate = dataFactory.getDateString(dlFileEntry.createDate)>
<#assign createDateLong = dataFactory.getDateLong(dlFileEntry.createDate)>

insert into DLFileEntry values ('${portalUUIDUtil.generate()}', ${dlFileEntry.fileEntryId}, ${dlFileEntry.groupId}, ${dlFileEntry.companyId}, ${dlFileEntry.userId}, '', ${dlFileEntry.userId}, '', '${createDate}', '${createDate}', 0, 0, ${dlFileEntry.repositoryId}, ${dlFileEntry.folderId}, '${dlFileEntry.name}', '${dlFileEntry.extension}', '${dlFileEntry.mimeType}', '${dlFileEntry.title}','${dlFileEntry.description}', '', 0, '1.0', '${maxDLFileEntrySize}', 1,'${dlFileEntry.smallImageId}','${dlFileEntry.largeImageId}', 0, 0, FALSE);

<#assign dlFileVersion = dataFactory.addDLFileVersion(dlFileEntry)>

insert into DLFileVersion values ('${portalUUIDUtil.generate()}', ${dlFileVersion.fileVersionId}, ${dlFileVersion.groupId}, ${dlFileVersion.companyId}, ${dlFileVersion.userId}, '', '${createDate}', '${createDate}', ${dlFileVersion.repositoryId}, ${dlFileEntry.folderId}, ${dlFileVersion.fileEntryId}, '${dlFileVersion.extension}', '${dlFileVersion.mimeType}', '${dlFileVersion.title}','${dlFileEntry.description}', '', '', 0, '1.0', '${maxDLFileEntrySize}', '', 0, ${dlFileVersion.userId}, '', '${createDate}');

<#assign dlSync = dataFactory.addDLSync(dlFileEntry.companyId, dlFileEntry.fileEntryId, dlFileEntry.groupId, dlFileEntry.folderId, false)>

insert into DLSync values (${dlSync.syncId}, ${dlSync.companyId}, '${createDateLong}', '${createDateLong}', ${dlSync.fileId}, '${dlSync.fileUuid}', ${dlSync.repositoryId}, ${dlSync.parentFolderId}, '${dlSync.name}', '${dlSync.description}', '${dlSync.event}', '${dlSync.type}', '${dlSync.version}');

<#assign assetEntry = dataFactory.addAssetEntry(dlFileEntry.groupId, dlFileEntry.userId, dataFactory.DLFileEntryClassNameId, dlFileEntry.fileEntryId, true, "text/html", dlFileEntry.title)>

insert into AssetEntry (entryId, groupId, companyId, userId, createDate, modifiedDate, classNameId, classPK, visible, mimeType, title) values (${counter.get()}, ${assetEntry.groupId}, ${companyId}, ${assetEntry.userId}, '${createDate}', '${createDate}', ${assetEntry.classNameId}, ${assetEntry.classPK}, <#if assetEntry.visible>TRUE<#else>FALSE</#if>, '${assetEntry.mimeType}', '${assetEntry.title}');

<#assign ddmContent = dataFactory.addDDMContent(dlFileEntry.groupId, dlFileEntry.companyId, dlFileEntry.userId)>

insert into DDMContent values ('${portalUUIDUtil.generate()}', ${ddmContent.contentId}, ${ddmContent.groupId}, ${ddmContent.companyId}, ${ddmContent.userId}, '', '${createDate}', '${createDate}',  'com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink', '', '<?xml version="1.0"?>\n\n<root>\n <dynamic-element name="CONTENT_TYPE">\n <dynamic-content><![CDATA[text/plain]]></dynamic-content>\n </dynamic-element>\n <dynamic-element name="CONTENT_ENCODING">\n <dynamic-content><![CDATA[ISO-8859-1]]></dynamic-content>\n </dynamic-element>\n</root>');

<#assign ddmStorageLink = dataFactory.addDDMStorageLink(dataFactory.DDMContentClassNameId, ddmContent.contentId, ddmStructure.structureId)>

insert into DDMStorageLink values ('${portalUUIDUtil.generate()}', ${ddmStorageLink.storageLinkId}, ${dataFactory.DDMContentClassNameId}, ${ddmContent.contentId}, ${ddmStructure.structureId});

${sampleSQLBuilder.insertMBDiscussion(dlFileEntry.groupId, dlFileEntry.userId, dataFactory.DLFileEntryClassNameId, dlFileEntry.fileEntryId, counter.get(), counter.get(), 0)}

<#assign socialActivity = dataFactory.addSocialActivity(dlFileEntry.groupId, dlFileEntry.companyId, dlFileEntry.userId, dataFactory.DLFileEntryClassNameId, dlFileEntry.fileEntryId)>

insert into SocialActivity values (${socialActivity.activityId}, ${socialActivity.groupId}, ${socialActivity.companyId}, ${socialActivity.userId}, ${stringUtil.valueOf(dateUtil.newTime())}, 0, 0, ${socialActivity.classNameId}, ${socialActivity.classPK}, 1, '', 0);

<#assign dlFileEntryMetadata = dataFactory.addDLFileEntryMetadata(ddmContent.contentId, ddmStructure.structureId, dlFileEntry.fileEntryId, dlFileVersion.fileVersionId)>

insert into DLFileEntryMetadata values ('${portalUUIDUtil.generate()}', ${dlFileEntryMetadata.fileEntryMetadataId}, ${ddmContent.contentId}, ${ddmStructure.structureId}, 0, ${dlFileEntryMetadata.fileEntryId}, ${dlFileEntryMetadata.fileVersionId});

<#assign ddmStructureLink = dataFactory.addDDMStructureLink(dlFileEntryMetadata.fileEntryMetadataId, ddmContent.contentId)>

insert into DDMStructureLink values (${ddmStructureLink.structureLinkId},${ ddmStructureLink.classNameId}, ${ddmStructureLink.classPK}, ${ddmStructureLink.structureId});