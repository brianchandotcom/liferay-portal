<#setting number_format = "0">

insert into DLFileEntry values ('${portalUUIDUtil.generate()}', ${dlFileEntry.fileEntryId}, ${dlFileEntry.groupId}, ${dlFileEntry.companyId}, ${dlFileEntry.userId}, '', ${dlFileEntry.userId}, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ${dlFileEntry.repositoryId}, ${dlFileEntry.folderId}, '${dlFileEntry.name}', '${dlFileEntry.extension}', '${dlFileEntry.mimeType}', '${dlFileEntry.title}','${dlFileEntry.description}', '', 0, '1.0', '${dlFileEntrySize}', 1,'${dlFileEntry.smallImageId}','${dlFileEntry.largeImageId}', 0, 0);

${sampleSQLBuilder.insertSecurity("com.liferay.portlet.documentlibrary.model.DLFileEntry", dlFileEntry.fileEntryId)}

update DLFolder set modifiedDate=CURRENT_TIMESTAMP, lastPostDate=CURRENT_TIMESTAMP where folderId='${dlFileEntry.folderId}';

<#assign assetEntry = dataFactory.addAssetEntry(dlFileEntry.groupId, dlFileEntry.userId, dataFactory.dlFileEntryClassName.classNameId, dlFileEntry.fileEntryId, true, "text/html", dlFileEntry.title)>
${sampleSQLBuilder.insertAssetEntry(assetEntry)}

<#assign dlFileVersion = dataFactory.addDLFileVersion(dlFileEntry)>
insert into DLFileVersion values (${dlFileVersion.fileVersionId}, ${dlFileVersion.groupId}, ${dlFileVersion.companyId}, ${dlFileVersion.userId}, '', CURRENT_TIMESTAMP, ${dlFileVersion.repositoryId}, ${dlFileVersion.fileEntryId}, '${dlFileVersion.extension}', '${dlFileVersion.mimeType}', '${dlFileVersion.title}','${dlFileEntry.description}', '', '', 0, '1.0', '${dlFileEntrySize}', ${dlFileVersion.smallImageId}, ${dlFileVersion.largeImageId}, ${dlFileVersion.custom1ImageId}, ${dlFileVersion.custom2ImageId}, 0, ${dlFileVersion.userId}, '', CURRENT_TIMESTAMP);

<#assign mbCompanyId = 0>
<#assign mbGroupId = 0>
<#assign mbUserId = dlFileEntry.userId>
<#assign mbCategoryId = 0>
<#assign mbThreadId = counter.get()>

<#assign mbRootMessage = dataFactory.addMBMessage(counter.get(), mbGroupId, mbUserId, dataFactory.dlFileEntryClassName.classNameId, dlFileEntry.fileEntryId, mbCategoryId, mbThreadId, 0, 0, stringUtil.valueOf(dlFileEntry.fileEntryId), stringUtil.valueOf(dlFileEntry.fileEntryId))>
${sampleSQLBuilder.insertMBMessage(mbRootMessage)}

<#assign mbThread = dataFactory.addMBThread(mbThreadId, mbGroupId, companyId, mbCategoryId, mbRootMessage.messageId, 1, mbUserId)>
${sampleSQLBuilder.insertMBThread(mbThread)}

<#assign mbDiscussion = dataFactory.addMBDiscussion(dataFactory.dlFileEntryClassName.classNameId, dlFileEntry.fileEntryId, mbThreadId)>
${sampleSQLBuilder.insertMBDiscussion(mbDiscussion)}

<#assign socialActivity = dataFactory.addSocialActivity(dlFileEntry.groupId, dlFileEntry.companyId, dlFileEntry.userId, dataFactory.dlFileEntryClassName.classNameId, dlFileEntry.fileEntryId)>
insert into SocialActivity values (${socialActivity.activityId}, ${socialActivity.groupId}, ${socialActivity.companyId}, ${socialActivity.userId}, CURRENT_TIMESTAMP, 0, ${socialActivity.classNameId}, ${socialActivity.classPK}, 1, '', 0);

<#assign dlFileRank = dataFactory.addDLFileRank(dlFileEntry.groupId, dlFileEntry.companyId, dlFileEntry.userId, dlFileEntry.fileEntryId)>
insert into DLFileRank values (${dlFileRank.fileRankId}, ${dlFileRank.groupId}, ${dlFileRank.companyId}, ${dlFileRank.userId}, CURRENT_TIMESTAMP, ${dlFileRank.fileEntryId});

<#assign ddmContent = dataFactory.addDDMContent(dlFileEntry.groupId, dlFileEntry.companyId, dlFileEntry.userId)>
insert into DDMContent values ('${portalUUIDUtil.generate()}', ${ddmContent.contentId}, ${ddmContent.groupId}, ${ddmContent.companyId}, ${ddmContent.userId}, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink', '', '<?xml version="1.0"?>\n\n<root>\n <dynamic-element name="CONTENT_TYPE">\n <dynamic-content><![CDATA[text/plain]]></dynamic-content>\n </dynamic-element>\n <dynamic-element name="CONTENT_ENCODING">\n <dynamic-content><![CDATA[ISO-8859-1]]></dynamic-content>\n </dynamic-element>\n</root>');

<#assign ddmStorageLink = dataFactory.addDDMStorageLink(dataFactory.ddmContentClassName.classNameId, ddmContent.contentId, ddmStructure.structureId)>
insert into DDMStorageLink values ('${portalUUIDUtil.generate()}',${ddmStorageLink.storageLinkId}, ${dataFactory.ddmContentClassName.classNameId}, ${ddmContent.contentId}, ${ddmStructure.structureId});

<#assign dlFileEntryMetadata = dataFactory.addDLFileEntryMetadata(ddmContent.contentId, ddmStructure.structureId, dlFileEntry.fileEntryId, dlFileVersion.fileVersionId)>
insert into DLFileEntryMetadata values ('${portalUUIDUtil.generate()}', ${dlFileEntryMetadata.fileEntryMetadataId}, ${ddmContent.contentId}, ${ddmStructure.structureId}, 0, ${dlFileEntryMetadata.fileEntryId}, ${dlFileEntryMetadata.fileVersionId});