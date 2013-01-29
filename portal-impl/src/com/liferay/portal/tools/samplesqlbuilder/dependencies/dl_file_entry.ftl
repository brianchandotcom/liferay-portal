<#setting number_format = "0">

insert into DLFileEntry values ('${dlFileEntry.uuid}', ${dlFileEntry.fileEntryId}, ${dlFileEntry.groupId}, ${dlFileEntry.companyId}, ${dlFileEntry.userId}, '${dlFileEntry.userName}', ${dlFileEntry.versionUserId}, '${dlFileEntry.versionUserName}', '${dlFileEntry.createDate?datetime}', '${dlFileEntry.modifiedDate?datetime}', ${dlFileEntry.classNameId}, ${dlFileEntry.classPK}, ${dlFileEntry.repositoryId}, ${dlFileEntry.folderId}, '${dlFileEntry.name}', '${dlFileEntry.extension}', '${dlFileEntry.mimeType}', '${dlFileEntry.title}','${dlFileEntry.description}', '${dlFileEntry.extraSettings}', ${dlFileEntry.fileEntryTypeId}, '${dlFileEntry.version}', ${dlFileEntry.size}, ${dlFileEntry.readCount}, ${dlFileEntry.smallImageId}, ${dlFileEntry.largeImageId}, ${dlFileEntry.custom1ImageId}, ${dlFileEntry.custom2ImageId}, ${dlFileEntry.manualCheckInRequired?string});

<#assign dlFileVersion = dataFactory.addDLFileVersion(dlFileEntry)>

insert into DLFileVersion values ('${dlFileVersion.uuid}', ${dlFileVersion.fileVersionId}, ${dlFileVersion.groupId}, ${dlFileVersion.companyId}, ${dlFileVersion.userId}, '${dlFileVersion.userName}', '${dlFileVersion.createDate?datetime}', '${dlFileVersion.modifiedDate?datetime}', ${dlFileVersion.repositoryId}, ${dlFileVersion.folderId}, ${dlFileVersion.fileEntryId}, '${dlFileVersion.extension}', '${dlFileVersion.mimeType}', '${dlFileVersion.title}','${dlFileVersion.description}', '${dlFileVersion.changeLog}', '${dlFileVersion.extraSettings}', ${dlFileVersion.fileEntryTypeId}, '${dlFileVersion.version}', ${dlFileVersion.size}, '${dlFileVersion.checksum}', ${dlFileVersion.status}, ${dlFileVersion.statusByUserId}, '${dlFileVersion.statusByUserName}', ${dlFileVersion.statusDate!'null'});

<#assign dlSync = dataFactory.addDLSync(dlFileEntry)>

insert into DLSync values (${dlSync.syncId}, ${dlSync.companyId}, '${dlSync.createDate?datetime}', '${dlSync.modifiedDate?datetime}', ${dlSync.fileId}, '${dlSync.fileUuid}', ${dlSync.repositoryId}, ${dlSync.parentFolderId}, '${dlSync.name}', '${dlSync.description}', '${dlSync.event}', '${dlSync.type}', '${dlSync.version}');

<#assign assetEntry = dataFactory.addAssetEntry(dlFileEntry)>

insert into AssetEntry values (${assetEntry.entryId}, ${assetEntry.groupId}, ${assetEntry.companyId}, ${assetEntry.userId}, '${assetEntry.userName}', '${assetEntry.createDate?datetime}', '${assetEntry.modifiedDate?datetime}', ${assetEntry.classNameId}, ${assetEntry.classPK}, '${assetEntry.classUuid}', ${assetEntry.classTypeId}, ${assetEntry.visible?string}, ${assetEntry.startDate!'null'}, ${assetEntry.endDate!'null'}, ${assetEntry.publishDate!'null'}, ${assetEntry.expirationDate!'null'}, '${assetEntry.mimeType}', '${assetEntry.title}', '${assetEntry.description}', '${assetEntry.summary}', '${assetEntry.url}', '${assetEntry.layoutUuid}', ${assetEntry.height}, ${assetEntry.width}, ${assetEntry.priority}, ${assetEntry.viewCount});

<#assign ddmContent = dataFactory.addDDMContent(dlFileEntry)>

insert into DDMContent values ('${ddmContent.uuid}', ${ddmContent.contentId}, ${ddmContent.groupId}, ${ddmContent.companyId}, ${ddmContent.userId}, '${ddmContent.userName}', '${ddmContent.createDate?datetime}', '${ddmContent.modifiedDate?datetime}', '${ddmContent.name}', '${ddmContent.description}', '${ddmContent.xml}');

<#assign ddmStorageLink = dataFactory.addDDMStorageLink(ddmContent, ddmStructureId)>

insert into DDMStorageLink values ('${ddmStorageLink.uuid}', ${ddmStorageLink.storageLinkId}, ${ddmStorageLink.classNameId}, ${ddmStorageLink.classPK}, ${ddmStorageLink.structureId});

<#assign mbMessage = dataFactory.addMBMessage(dlFileEntry)>

${sampleSQLBuilder.insertMBMessage(mbMessage)}

<#assign mbThread = dataFactory.addMBThread(mbMessage, 1)>

insert into MBThread values (${mbThread.threadId}, ${mbThread.groupId}, ${mbThread.companyId}, ${mbThread.categoryId}, ${mbThread.rootMessageId}, ${mbThread.rootMessageUserId}, ${mbThread.messageCount}, ${mbThread.viewCount}, ${mbThread.lastPostByUserId}, '${mbThread.lastPostDate?datetime}', ${mbThread.priority}, ${mbThread.question?string}, ${mbThread.status}, ${mbThread.statusByUserId}, '${mbThread.statusByUserName}', ${mbThread.statusDate!'null'});

<#assign mbDiscussion = dataFactory.addMBDiscussion(dlFileEntry, mbThread.threadId)>

insert into MBDiscussion values (${mbDiscussion.discussionId}, ${mbDiscussion.classNameId}, ${mbDiscussion.classPK}, ${mbDiscussion.threadId});

<#assign socialActivity = dataFactory.addSocialActivity(dlFileEntry)>

insert into SocialActivity values (${socialActivity.activityId}, ${socialActivity.groupId}, ${socialActivity.companyId}, ${socialActivity.userId}, '${socialActivity.createDate}', ${socialActivity.mirrorActivityId}, ${socialActivity.classNameId}, ${socialActivity.classPK}, ${socialActivity.type}, '${socialActivity.extraData}', ${socialActivity.receiverUserId});

<#assign dlFileEntryMetadata = dataFactory.addDLFileEntryMetadata(ddmStorageLink, dlFileVersion)>

insert into DLFileEntryMetadata values ('${dlFileEntryMetadata.uuid}', ${dlFileEntryMetadata.fileEntryMetadataId}, ${dlFileEntryMetadata.DDMStorageId}, ${dlFileEntryMetadata.DDMStructureId}, ${dlFileEntryMetadata.fileEntryTypeId}, ${dlFileEntryMetadata.fileEntryId}, ${dlFileEntryMetadata.fileVersionId});

<#assign ddmStructureLink = dataFactory.addDDMStructureLink(dlFileEntryMetadata)>

insert into DDMStructureLink values (${ddmStructureLink.structureLinkId},${ ddmStructureLink.classNameId}, ${ddmStructureLink.classPK}, ${ddmStructureLink.structureId});