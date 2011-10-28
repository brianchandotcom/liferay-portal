<#setting number_format = "0">

insert into DLFileEntry values ('${portalUUIDUtil.generate()}', ${dlFileEntry.fileEntryId}, ${dlFileEntry.groupId}, ${dlFileEntry.companyId}, ${dlFileEntry.userId}, '', ${dlFileEntry.userId}, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ${dlFileEntry.repositoryId}, ${dlFileEntry.folderId}, '${dlFileEntry.name}', '${dlFileEntry.extension}', '${dlFileEntry.mimeType}', '${dlFileEntry.title}','${dlFileEntry.description}', '', 0, '1.0', '${dlFileEntrySize}', 1,'${dlFileEntry.smallImageId}','${dlFileEntry.largeImageId}', 0, 0);

${sampleSQLBuilder.insertSecurity("com.liferay.portlet.documentlibrary.model.DLFileEntry", dlFileEntry.fileEntryId)}

<#assign dlFileRank = dataFactory.addDLFileRank(dlFileEntry.groupId, dlFileEntry.companyId, dlFileEntry.userId, dlFileEntry.fileEntryId)>

insert into DLFileRank values (${dlFileRank.fileRankId}, ${dlFileRank.groupId}, ${dlFileRank.companyId}, ${dlFileRank.userId}, CURRENT_TIMESTAMP, ${dlFileRank.fileEntryId});

<#assign dlFileVersion = dataFactory.addDLFileVersion(dlFileEntry)>

insert into DLFileVersion values (${dlFileVersion.fileVersionId}, ${dlFileVersion.groupId}, ${dlFileVersion.companyId}, ${dlFileVersion.userId}, '', CURRENT_TIMESTAMP, ${dlFileVersion.repositoryId}, ${dlFileEntry.folderId}, ${dlFileVersion.fileEntryId}, '${dlFileVersion.extension}', '${dlFileVersion.mimeType}', '${dlFileVersion.title}','${dlFileEntry.description}', '', '', 0, '1.0', '${dlFileEntrySize}', 0, ${dlFileVersion.userId}, '', CURRENT_TIMESTAMP);

<#assign assetEntry = dataFactory.addAssetEntry(dlFileEntry.groupId, dlFileEntry.userId, dataFactory.DLFileEntryClassName.classNameId, dlFileEntry.fileEntryId, true, "text/html", dlFileEntry.title)>

insert into AssetEntry (entryId, groupId, companyId, userId, createDate, modifiedDate, classNameId, classPK, visible, mimeType, title) values (${counter.get()}, ${assetEntry.groupId}, ${companyId}, ${assetEntry.userId}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ${assetEntry.classNameId}, ${assetEntry.classPK}, <#if assetEntry.visible>TRUE<#else>FALSE</#if>, '${assetEntry.mimeType}', '${assetEntry.title}');

<#assign ddmContent = dataFactory.addDDMContent(dlFileEntry.groupId, dlFileEntry.companyId, dlFileEntry.userId)>

insert into DDMContent values ('${portalUUIDUtil.generate()}', ${ddmContent.contentId}, ${ddmContent.groupId}, ${ddmContent.companyId}, ${ddmContent.userId}, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink', '', '<?xml version="1.0"?>\n\n<root>\n <dynamic-element name="CONTENT_TYPE">\n <dynamic-content><![CDATA[text/plain]]></dynamic-content>\n </dynamic-element>\n <dynamic-element name="CONTENT_ENCODING">\n <dynamic-content><![CDATA[ISO-8859-1]]></dynamic-content>\n </dynamic-element>\n</root>');

<#assign ddmStorageLink = dataFactory.addDDMStorageLink(dataFactory.DDMContentClassName.classNameId, ddmContent.contentId, ddmStructure.structureId)>

insert into DDMStorageLink values ('${portalUUIDUtil.generate()}', ${ddmStorageLink.storageLinkId}, ${dataFactory.DDMContentClassName.classNameId}, ${ddmContent.contentId}, ${ddmStructure.structureId});

<#assign mbDiscussion = dataFactory.addMBDiscussion(dataFactory.DLFileEntryClassName.classNameId, dlFileEntry.fileEntryId, counter.get())>

insert into MBDiscussion values (${mbDiscussion.discussionId}, ${mbDiscussion.classNameId}, ${mbDiscussion.classPK}, ${mbDiscussion.threadId});

<#assign mbMessage = dataFactory.addMBMessage(counter.get(), 0, dlFileEntry.userId, dataFactory.DLFileEntryClassName.classNameId, dlFileEntry.fileEntryId, 0, mbDiscussion.threadId, 0, 0, stringUtil.valueOf(dlFileEntry.fileEntryId), stringUtil.valueOf(dlFileEntry.fileEntryId))>

${sampleSQLBuilder.insertMBMessage(mbMessage)}

<#assign mbThread = dataFactory.addMBThread(mbDiscussion.threadId, mbMessage.groupId, companyId, mbMessage.categoryId, mbMessage.messageId, 1, mbMessage.userId)>

insert into MBThread values (${mbThread.threadId}, ${mbThread.groupId}, ${mbThread.companyId}, ${mbThread.categoryId}, ${mbThread.rootMessageId}, ${mbThread.rootMessageUserId}, ${mbThread.messageCount}, 0, ${mbThread.lastPostByUserId}, CURRENT_TIMESTAMP, 0, FALSE, 0, ${mbThread.lastPostByUserId}, '', CURRENT_TIMESTAMP);

<#assign socialActivity = dataFactory.addSocialActivity(dlFileEntry.groupId, dlFileEntry.companyId, dlFileEntry.userId, dataFactory.DLFileEntryClassName.classNameId, dlFileEntry.fileEntryId)>

insert into SocialActivity values (${socialActivity.activityId}, ${socialActivity.groupId}, ${socialActivity.companyId}, ${socialActivity.userId}, ${stringUtil.valueOf(dateUtil.newTime())}, 0, ${socialActivity.classNameId}, ${socialActivity.classPK}, 1, '', 0);

<#assign dlFileEntryMetadata = dataFactory.addDLFileEntryMetadata(ddmContent.contentId, ddmStructure.structureId, dlFileEntry.fileEntryId, dlFileVersion.fileVersionId)>

insert into DLFileEntryMetadata values ('${portalUUIDUtil.generate()}', ${dlFileEntryMetadata.fileEntryMetadataId}, ${ddmContent.contentId}, ${ddmStructure.structureId}, 0, ${dlFileEntryMetadata.fileEntryId}, ${dlFileEntryMetadata.fileVersionId});