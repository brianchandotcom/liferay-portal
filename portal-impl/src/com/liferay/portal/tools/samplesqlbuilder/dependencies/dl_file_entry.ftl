<#setting number_format = "0">

<#assign createDate = dataFactory.getDateString(dlFileEntry.createDate)>
<#assign createDateLong = dataFactory.getDateLong(dlFileEntry.createDate)>

insert into DLFileEntry values ('${dlFileEntry.uuid}', ${dlFileEntry.fileEntryId}, ${dlFileEntry.groupId}, ${dlFileEntry.companyId}, ${dlFileEntry.userId}, '${dlFileEntry.userName}', ${dlFileEntry.versionUserId}, '${dlFileEntry.versionUserName}', '${dataFactory.getDateString(dlFileEntry.createDate)}', '${dataFactory.getDateString(dlFileEntry.modifiedDate)}', ${dlFileEntry.classNameId}, ${dlFileEntry.classPK}, ${dlFileEntry.repositoryId}, ${dlFileEntry.folderId}, '${dlFileEntry.name}', '${dlFileEntry.extension}', '${dlFileEntry.mimeType}', '${dlFileEntry.title}','${dlFileEntry.description}', '${dlFileEntry.extraSettings}', ${dlFileEntry.fileEntryTypeId}, '${dlFileEntry.version}', ${dlFileEntry.size}, ${dlFileEntry.readCount}, ${dlFileEntry.smallImageId}, ${dlFileEntry.largeImageId}, ${dlFileEntry.custom1ImageId}, ${dlFileEntry.custom2ImageId}, ${dlFileEntry.manualCheckInRequired?string});

<#assign dlFileVersion = dataFactory.newDLFileVersion(dlFileEntry)>

insert into DLFileVersion values ('${portalUUIDUtil.generate()}', ${dlFileVersion.fileVersionId}, ${dlFileVersion.groupId}, ${dlFileVersion.companyId}, ${dlFileVersion.userId}, '', '${createDate}', '${createDate}', ${dlFileVersion.repositoryId}, ${dlFileEntry.folderId}, ${dlFileVersion.fileEntryId}, '${dlFileVersion.extension}', '${dlFileVersion.mimeType}', '${dlFileVersion.title}','${dlFileEntry.description}', '', '', 0, '1.0', '${maxDLFileEntrySize}', '', 0, ${dlFileVersion.userId}, '', '${createDate}');

<#assign dlSync = dataFactory.newDLSync(dlFileEntry.companyId, dlFileEntry.fileEntryId, dlFileEntry.groupId, dlFileEntry.folderId, false)>

insert into DLSync values (${dlSync.syncId}, ${dlSync.companyId}, '${createDateLong}', '${createDateLong}', ${dlSync.fileId}, '${dlSync.fileUuid}', ${dlSync.repositoryId}, ${dlSync.parentFolderId}, '${dlSync.name}', '${dlSync.description}', '${dlSync.event}', '${dlSync.type}', '${dlSync.version}');

<#assign assetEntry = dataFactory.newAssetEntry(dlFileEntry)>

insert into AssetEntry values (${assetEntry.entryId}, ${assetEntry.groupId}, ${assetEntry.companyId}, ${assetEntry.userId}, '${assetEntry.userName}', '${dataFactory.getDateString(assetEntry.createDate)}', '${dataFactory.getDateString(assetEntry.modifiedDate)}', ${assetEntry.classNameId}, ${assetEntry.classPK}, '${assetEntry.classUuid}', ${assetEntry.classTypeId}, ${assetEntry.visible?string}, '${dataFactory.getDateString(assetEntry.startDate)}', '${dataFactory.getDateString(assetEntry.endDate)}', '${dataFactory.getDateString(assetEntry.publishDate)}', '${dataFactory.getDateString(assetEntry.expirationDate)}', '${assetEntry.mimeType}', '${assetEntry.title}', '${assetEntry.description}', '${assetEntry.summary}', '${assetEntry.url}', '${assetEntry.layoutUuid}', ${assetEntry.height}, ${assetEntry.width}, ${assetEntry.priority}, ${assetEntry.viewCount});

<#assign ddmContent = dataFactory.newDDMContent(dlFileEntry.groupId, dlFileEntry.companyId, dlFileEntry.userId)>

insert into DDMContent values ('${portalUUIDUtil.generate()}', ${ddmContent.contentId}, ${ddmContent.groupId}, ${ddmContent.companyId}, ${ddmContent.userId}, '', '${createDate}', '${createDate}',  'com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink', '', '<?xml version="1.0"?>\n\n<root>\n <dynamic-element name="CONTENT_TYPE">\n <dynamic-content><![CDATA[text/plain]]></dynamic-content>\n </dynamic-element>\n <dynamic-element name="CONTENT_ENCODING">\n <dynamic-content><![CDATA[ISO-8859-1]]></dynamic-content>\n </dynamic-element>\n</root>');

<#assign ddmStorageLink = dataFactory.newDDMStorageLink(dataFactory.DDMContentClassNameId, ddmContent.contentId, ddmStructureId)>

insert into DDMStorageLink values ('${portalUUIDUtil.generate()}', ${ddmStorageLink.storageLinkId}, ${dataFactory.DDMContentClassNameId}, ${ddmContent.contentId}, ${ddmStructureId});

${sampleSQLBuilder.insertMBDiscussion(dlFileEntry.groupId, dataFactory.DLFileEntryClassNameId, dlFileEntry.fileEntryId, counter.get(), counter.get(), 0)}

<#assign socialActivity = dataFactory.newSocialActivity(dlFileEntry)>

insert into SocialActivity values (${socialActivity.activityId}, ${socialActivity.groupId}, ${socialActivity.companyId}, ${socialActivity.userId}, ${socialActivity.createDate}, ${socialActivity.activitySetId}, ${socialActivity.mirrorActivityId}, ${socialActivity.classNameId}, ${socialActivity.classPK}, ${socialActivity.type}, '${socialActivity.extraData}', ${socialActivity.receiverUserId});

<#assign dlFileEntryMetadata = dataFactory.newDLFileEntryMetadata(ddmContent.contentId, ddmStructureId, dlFileEntry.fileEntryId, dlFileVersion.fileVersionId)>

insert into DLFileEntryMetadata values ('${portalUUIDUtil.generate()}', ${dlFileEntryMetadata.fileEntryMetadataId}, ${ddmContent.contentId}, ${ddmStructureId}, 0, ${dlFileEntryMetadata.fileEntryId}, ${dlFileEntryMetadata.fileVersionId});

<#assign ddmStructureLink = dataFactory.newDDMStructureLink(dlFileEntryMetadata.fileEntryMetadataId, ddmContent.contentId)>

insert into DDMStructureLink values (${ddmStructureLink.structureLinkId},${ ddmStructureLink.classNameId}, ${ddmStructureLink.classPK}, ${ddmStructureLink.structureId});