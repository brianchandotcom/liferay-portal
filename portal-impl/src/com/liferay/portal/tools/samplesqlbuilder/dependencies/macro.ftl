<#setting number_format = "computer">
<#setting datetime_format = "yyyy-MM-dd HH:mm:ss">

<#--insert AssetEntry-->

<#macro insertAssetEntry _entry>
	<#local assetEntry = dataFactory.addAssetEntry(_entry)>

	insert into AssetEntry values (${assetEntry.entryId}, ${assetEntry.groupId}, ${assetEntry.companyId}, ${assetEntry.userId}, '${assetEntry.userName}', '${assetEntry.createDate?datetime}', '${assetEntry.modifiedDate?datetime}', ${assetEntry.classNameId}, ${assetEntry.classPK}, '${assetEntry.classUuid}', ${assetEntry.classTypeId}, ${assetEntry.visible?string}, ${assetEntry.startDate!'null'}, ${assetEntry.endDate!'null'}, ${assetEntry.publishDate!'null'}, ${assetEntry.expirationDate!'null'}, '${assetEntry.mimeType}', '${assetEntry.title}', '${assetEntry.description}', '${assetEntry.summary}', '${assetEntry.url}', '${assetEntry.layoutUuid}', ${assetEntry.height}, ${assetEntry.width}, ${assetEntry.priority}, ${assetEntry.viewCount});
</#macro>

<#--insert DDLRecord-->

<#macro insertDDLRecord _ddlRecord _ddlRecordCount _ddmStructureId>
	insert into DDLRecord values ('${_ddlRecord.uuid}', ${_ddlRecord.recordId}, ${_ddlRecord.groupId}, ${_ddlRecord.companyId}, ${_ddlRecord.userId}, '${_ddlRecord.userName}', ${_ddlRecord.versionUserId}, '${_ddlRecord.versionUserName}', '${_ddlRecord.createDate?datetime}', '${_ddlRecord.modifiedDate?datetime}', ${_ddlRecord.DDMStorageId}, ${_ddlRecord.recordSetId}, '${_ddlRecord.version}', ${_ddlRecord.displayIndex});

	<#local ddmContent = dataFactory.addDDMContent(_ddlRecord, _ddlRecordCount)>

	insert into DDMContent values ('${ddmContent.uuid}', ${ddmContent.contentId}, ${ddmContent.groupId}, ${ddmContent.companyId}, ${ddmContent.userId}, '${ddmContent.userName}', '${ddmContent.createDate?datetime}', '${ddmContent.modifiedDate?datetime}', '${ddmContent.name}', '${ddmContent.description}', '${ddmContent.xml}');

	<#local ddlRecordVersion = dataFactory.addDDLRecordVersion(_ddlRecord)>

	insert into DDLRecordVersion values (${ddlRecordVersion.recordVersionId}, ${ddlRecordVersion.groupId}, ${ddlRecordVersion.companyId}, ${ddlRecordVersion.userId}, '${ddlRecordVersion.userName}', '${ddlRecordVersion.createDate?datetime}', ${ddlRecordVersion.DDMStorageId}, ${ddlRecordVersion.recordSetId}, ${ddlRecordVersion.recordId}, '${ddlRecordVersion.version}', ${ddlRecordVersion.displayIndex}, ${ddlRecordVersion.status}, ${ddlRecordVersion.statusByUserId}, '${ddlRecordVersion.statusByUserName}', ${ddlRecordVersion.statusDate!'null'});

	<#local ddmStorageLink = dataFactory.addDDMStorageLink(ddmContent, ddmStructureId)>

	insert into DDMStorageLink values ('${ddmStorageLink.uuid}', ${ddmStorageLink.storageLinkId}, ${ddmStorageLink.classNameId}, ${ddmStorageLink.classPK}, ${ddmStorageLink.structureId});
</#macro>

<#--insert DLFile-->

<#macro insertDLFile _dlFileEntry>
	insert into DLFileEntry values ('${_dlFileEntry.uuid}', ${_dlFileEntry.fileEntryId}, ${_dlFileEntry.groupId}, ${_dlFileEntry.companyId}, ${_dlFileEntry.userId}, '${_dlFileEntry.userName}', ${_dlFileEntry.versionUserId}, '${_dlFileEntry.versionUserName}', '${_dlFileEntry.createDate?datetime}', '${_dlFileEntry.modifiedDate?datetime}', ${_dlFileEntry.classNameId}, ${_dlFileEntry.classPK}, ${_dlFileEntry.repositoryId}, ${_dlFileEntry.folderId}, '${_dlFileEntry.name}', '${_dlFileEntry.extension}', '${_dlFileEntry.mimeType}', '${_dlFileEntry.title}','${_dlFileEntry.description}', '${_dlFileEntry.extraSettings}', ${_dlFileEntry.fileEntryTypeId}, '${_dlFileEntry.version}', ${_dlFileEntry.size}, ${_dlFileEntry.readCount}, ${_dlFileEntry.smallImageId}, ${_dlFileEntry.largeImageId}, ${_dlFileEntry.custom1ImageId}, ${_dlFileEntry.custom2ImageId}, ${_dlFileEntry.manualCheckInRequired?string});

	<#local dlFileVersion = dataFactory.addDLFileVersion(_dlFileEntry)>

	insert into DLFileVersion values ('${dlFileVersion.uuid}', ${dlFileVersion.fileVersionId}, ${dlFileVersion.groupId}, ${dlFileVersion.companyId}, ${dlFileVersion.userId}, '${dlFileVersion.userName}', '${dlFileVersion.createDate?datetime}', '${dlFileVersion.modifiedDate?datetime}', ${dlFileVersion.repositoryId}, ${dlFileVersion.folderId}, ${dlFileVersion.fileEntryId}, '${dlFileVersion.extension}', '${dlFileVersion.mimeType}', '${dlFileVersion.title}','${dlFileVersion.description}', '${dlFileVersion.changeLog}', '${dlFileVersion.extraSettings}', ${dlFileVersion.fileEntryTypeId}, '${dlFileVersion.version}', ${dlFileVersion.size}, '${dlFileVersion.checksum}', ${dlFileVersion.status}, ${dlFileVersion.statusByUserId}, '${dlFileVersion.statusByUserName}', ${dlFileVersion.statusDate!'null'});

	<#local dlSync = dataFactory.addDLSync(_dlFileEntry)>

	insert into DLSync values (${dlSync.syncId}, ${dlSync.companyId}, '${dlSync.createDate?datetime}', '${dlSync.modifiedDate?datetime}', ${dlSync.fileId}, '${dlSync.fileUuid}', ${dlSync.repositoryId}, ${dlSync.parentFolderId}, '${dlSync.name}', '${dlSync.description}', '${dlSync.event}', '${dlSync.type}', '${dlSync.version}');

	<#local ddmContent = dataFactory.addDDMContent(_dlFileEntry)>

	insert into DDMContent values ('${ddmContent.uuid}', ${ddmContent.contentId}, ${ddmContent.groupId}, ${ddmContent.companyId}, ${ddmContent.userId}, '${ddmContent.userName}', '${ddmContent.createDate?datetime}', '${ddmContent.modifiedDate?datetime}', '${ddmContent.name}', '${ddmContent.description}', '${ddmContent.xml}');

	<#local ddmStorageLink = dataFactory.addDDMStorageLink(ddmContent, ddmStructureId)>

	insert into DDMStorageLink values ('${ddmStorageLink.uuid}', ${ddmStorageLink.storageLinkId}, ${ddmStorageLink.classNameId}, ${ddmStorageLink.classPK}, ${ddmStorageLink.structureId});

	<#local mbRootMessage = dataFactory.addMBMessage(_dlFileEntry)>

	<@insertMBDiscussion _entry = _dlFileEntry _maxCommentCount = 1 _mbRootMessage = mbRootMessage/>

	<#local socialActivity = dataFactory.addSocialActivity(_dlFileEntry)>

	insert into SocialActivity values (${socialActivity.activityId}, ${socialActivity.groupId}, ${socialActivity.companyId}, ${socialActivity.userId}, '${socialActivity.createDate}', ${socialActivity.mirrorActivityId}, ${socialActivity.classNameId}, ${socialActivity.classPK}, ${socialActivity.type}, '${socialActivity.extraData}', ${socialActivity.receiverUserId});

	<#local dlFileEntryMetadata = dataFactory.addDLFileEntryMetadata(ddmStorageLink, dlFileVersion)>

	insert into DLFileEntryMetadata values ('${dlFileEntryMetadata.uuid}', ${dlFileEntryMetadata.fileEntryMetadataId}, ${dlFileEntryMetadata.DDMStorageId}, ${dlFileEntryMetadata.DDMStructureId}, ${dlFileEntryMetadata.fileEntryTypeId}, ${dlFileEntryMetadata.fileEntryId}, ${dlFileEntryMetadata.fileVersionId});

	<#local ddmStructureLink = dataFactory.addDDMStructureLink(dlFileEntryMetadata)>

	insert into DDMStructureLink values (${ddmStructureLink.structureLinkId},${ ddmStructureLink.classNameId}, ${ddmStructureLink.classPK}, ${ddmStructureLink.structureId});
</#macro>

<#--insert DLFolders-->

<#macro insertDLFolders _parentDLFolderId _dlFolderDepth>
	<#if (_dlFolderDepth <= maxDLFolderDepth)>
		<#list 1..maxDLFolderCount as dlFolderCount>
			<#local dlFolder = dataFactory.addDLFolder(groupId, _parentDLFolderId, dlFolderCount)>

			insert into DLFolder values ('${dlFolder.uuid}', ${dlFolder.folderId}, ${dlFolder.groupId}, ${dlFolder.companyId}, ${dlFolder.userId}, '${dlFolder.userName}', '${dlFolder.createDate?datetime}', '${dlFolder.modifiedDate?datetime}', ${dlFolder.repositoryId}, ${dlFolder.mountPoint?string}, ${dlFolder.parentFolderId}, '${dlFolder.name}', '${dlFolder.description}', '${dlFolder.lastPostDate?datetime}', ${dlFolder.defaultFileEntryTypeId}, ${dlFolder.hidden?string}, ${dlFolder.overrideFileEntryTypes?string}, ${dlFolder.status}, ${dlFolder.statusByUserId}, '${dlFolder.statusByUserName}', ${dlFolder.statusDate!'null'});

			<#local dlSync = dataFactory.addDLSync(dlFolder)>

			insert into DLSync values (${dlSync.syncId}, ${dlSync.companyId}, '${dlSync.createDate?datetime}', '${dlSync.modifiedDate?datetime}', ${dlSync.fileId}, '${dlSync.fileUuid}', ${dlSync.repositoryId}, ${dlSync.parentFolderId}, '${dlSync.name}', '${dlSync.description}', '${dlSync.event}', '${dlSync.type}', '${dlSync.version}');

			<#if (maxDLFileEntryCount > 0)>
				<#list 1..maxDLFileEntryCount as dlFileEntryCount>
					<#local dlFileEntry = dataFactory.addDlFileEntry(dlFolder, dlFileEntryCount)>

					<@insertDLFile _dlFileEntry = dlFileEntry/>

					${writerDocumentLibraryCSV.write(dlFolder.folderId + "," + dlFileEntry.name + "," + dlFileEntry.fileEntryId + "," + dataFactory.getDateLong(dlFileEntry.createDate) + "," + dataFactory.getDateLong(dlFolder.createDate) +"\n")}
				</#list>
			</#if>

			<@insertDLFolders _parentDLFolderId = dlFolder.folderId _dlFolderDepth = _dlFolderDepth + 1/>
		</#list>
	</#if>
</#macro>

<#--insert Group-->

<#macro insertGroup _group _publicLayouts = []>
	insert into Group_ values (${_group.groupId}, ${_group.companyId}, ${_group.creatorUserId}, ${_group.classNameId}, ${_group.classPK}, ${_group.parentGroupId}, ${_group.liveGroupId}, '${_group.treePath}', '${_group.name}', '${_group.description}', ${_group.type}, '${_group.typeSettings}', '${_group.friendlyURL}', ${_group.site?string}, ${_group.active?string});

	<#local layoutSize = _publicLayouts?size>

	<#local layoutSets = dataFactory.addLayoutSets(_group, layoutSize)>

	<#list layoutSets as layoutSet>
		insert into LayoutSet values (${layoutSet.layoutSetId}, ${layoutSet.groupId}, ${layoutSet.companyId}, '${layoutSet.createDate?datetime}', '${layoutSet.modifiedDate?datetime}', ${layoutSet.privateLayout?string}, ${layoutSet.logo?string}, ${layoutSet.logoId}, '${layoutSet.themeId}', '${layoutSet.colorSchemeId}', '${layoutSet.wapThemeId}', '${layoutSet.wapColorSchemeId}', '${layoutSet.css}', ${layoutSet.pageCount}, '${layoutSet.settings}', '${layoutSet.layoutSetPrototypeUuid}', ${layoutSet.layoutSetPrototypeLinkEnabled?string});
	</#list>

	<#list _publicLayouts as layout>
		insert into Layout values ('${layout.uuid}', ${layout.plid}, ${layout.groupId}, ${layout.companyId}, '${layout.createDate?datetime}', '${layout.modifiedDate?datetime}', ${layout.privateLayout?string}, ${layout.layoutId}, ${layout.parentLayoutId}, '${layout.name}', '${layout.title}', '${layout.description}', '${layout.keywords}', '${layout.robots}', '${layout.type}', '${layout.typeSettings}', ${layout.hidden?string}, '${layout.friendlyURL}', ${layout.iconImage?string}, ${layout.iconImageId}, '${layout.themeId}', '${layout.colorSchemeId}', '${layout.wapThemeId}', '${layout.wapColorSchemeId}', '${layout.css}', ${layout.priority}, '${layout.layoutPrototypeUuid}', ${layout.layoutPrototypeLinkEnabled?string}, '${layout.sourcePrototypeLayoutUuid}');

		<@insertResourcePermission _resourceName = "com.liferay.portal.model.Layout" _resourcePrimkey = layout.plid/>
	</#list>
</#macro>

<#--insert MBCategory-->

<#macro insertMBCategory _mbCategory>
	insert into MBCategory values ('${_mbCategory.uuid}', ${_mbCategory.categoryId}, ${_mbCategory.groupId}, ${_mbCategory.companyId}, ${_mbCategory.userId}, '${_mbCategory.userName}', '${_mbCategory.createDate?datetime}', '${_mbCategory.modifiedDate?datetime}', ${_mbCategory.parentCategoryId}, '${_mbCategory.name}', '${_mbCategory.description}', '${_mbCategory.displayStyle}', ${_mbCategory.threadCount}, ${_mbCategory.messageCount}, '${_mbCategory.lastPostDate?datetime}', ${_mbCategory.status}, ${_mbCategory.statusByUserId}, '${_mbCategory.statusByUserName}', ${_mbCategory.statusDate!'null'});

	<#if (_mbCategory.categoryId != 0)>
		<#local mbMailingList = dataFactory.addMBMailingList(_mbCategory)>

		insert into MBMailingList values ('${mbMailingList.uuid}', ${mbMailingList.mailingListId}, ${mbMailingList.groupId}, ${mbMailingList.companyId}, ${mbMailingList.userId}, '${mbMailingList.userName}', '${mbMailingList.createDate?datetime}', '${mbMailingList.modifiedDate?datetime}', ${mbMailingList.categoryId}, '${mbMailingList.emailAddress}', '${mbMailingList.inProtocol}', '${mbMailingList.inServerName}', ${mbMailingList.inServerPort}, ${mbMailingList.inUseSSL?string}, '${mbMailingList.inUserName}', '${mbMailingList.inPassword}', ${mbMailingList.inReadInterval}, '${mbMailingList.outEmailAddress}', ${mbMailingList.outCustom?string}, '${mbMailingList.outServerName}', ${mbMailingList.outServerPort}, ${mbMailingList.outUseSSL?string}, '${mbMailingList.outUserName}', '${mbMailingList.outPassword}', ${mbMailingList.allowAnonymous?string}, ${mbMailingList.active?string});
	</#if>
</#macro>

<#--insert MBDiscussion-->

<#macro insertMBDiscussion _entry _maxCommentCount _mbRootMessage>
	<@insertAssetEntry _entry = _entry/>

	<@insertMBMessage _mbMessage = _mbRootMessage/>

	<#local mbThread = dataFactory.addMBThread(_mbRootMessage, _maxCommentCount)>

	insert into MBThread values (${mbThread.threadId}, ${mbThread.groupId}, ${mbThread.companyId}, ${mbThread.categoryId}, ${mbThread.rootMessageId}, ${mbThread.rootMessageUserId}, ${mbThread.messageCount}, ${mbThread.viewCount}, ${mbThread.lastPostByUserId}, '${mbThread.lastPostDate?datetime}', ${mbThread.priority}, ${mbThread.question?string}, ${mbThread.status}, ${mbThread.statusByUserId}, '${mbThread.statusByUserName}', ${mbThread.statusDate!'null'});

	<#local mbDiscussion = dataFactory.addMBDiscussion(_entry, mbThread.threadId)>

	insert into MBDiscussion values (${mbDiscussion.discussionId}, ${mbDiscussion.classNameId}, ${mbDiscussion.classPK}, ${mbDiscussion.threadId});

	<#if (_maxCommentCount > 1)>
		<#list 1.._maxCommentCount as commentCount>
			<#local mbMessage = dataFactory.addMBMessage(_entry, _mbRootMessage, commentCount)>

			<@insertMBMessage _mbMessage = mbMessage/>
		</#list>
	</#if>
</#macro>

<#--insert MBMessage-->

<#macro insertMBMessage _mbMessage>
	insert into MBMessage values ('${_mbMessage.uuid}', ${_mbMessage.messageId}, ${_mbMessage.groupId}, ${_mbMessage.companyId}, ${_mbMessage.userId}, '${_mbMessage.userName}', '${_mbMessage.createDate?datetime}', '${_mbMessage.modifiedDate?datetime}', ${_mbMessage.classNameId}, ${_mbMessage.classPK}, ${_mbMessage.categoryId}, ${_mbMessage.threadId}, ${_mbMessage.rootMessageId}, ${_mbMessage.parentMessageId}, '${_mbMessage.subject}', '${_mbMessage.body}', '${_mbMessage.format}', ${_mbMessage.anonymous?string}, ${_mbMessage.priority}, ${_mbMessage.allowPingbacks?string}, ${_mbMessage.answer?string}, ${_mbMessage.status}, ${_mbMessage.statusByUserId}, '${_mbMessage.statusByUserName}', ${_mbMessage.statusDate!'null'});

	<@insertAssetEntry _entry = _mbMessage/>
</#macro>

<#--insert User-->

<#macro insertUser _user _roleIds = [] _groupIds = []>
	insert into User_ values ('${_user.uuid}', ${_user.userId}, ${_user.companyId}, '${_user.createDate?datetime}', '${_user.modifiedDate?datetime}', ${_user.defaultUser?string}, ${_user.contactId}, '${_user.password}', ${_user.passwordEncrypted?string}, ${_user.passwordReset?string}, ${_user.passwordModifiedDate!'null'}, '${_user.digest}', '${_user.reminderQueryQuestion}', '${_user.reminderQueryAnswer}', ${_user.graceLoginCount}, '${_user.screenName}', '${_user.emailAddress}', ${_user.facebookId}, ${_user.ldapServerId}, '${_user.openId}', ${_user.portraitId}, '${_user.languageId}', '${_user.timeZoneId}', '${_user.greeting}', '${_user.comments}', '${_user.firstName}', '${_user.middleName}', '${_user.lastName}', '${_user.jobTitle}', '${_user.loginDate?datetime}', '${_user.loginIP}', '${_user.lastLoginDate?datetime}', '${_user.lastLoginIP}',  ${_user.lastFailedLoginDate!'null'}, ${_user.failedLoginAttempts}, ${_user.lockout?string}, ${_user.lockoutDate!'null'}, ${_user.agreedToTermsOfUse?string}, ${_user.emailAddressVerified?string}, '${_user.status}');

	<#local contact = dataFactory.addContact(_user)>

	insert into Contact_ values (${contact.contactId}, ${contact.companyId}, ${contact.userId}, '${contact.userName}', '${contact.createDate?datetime}', '${contact.modifiedDate?datetime}', ${contact.classNameId}, ${contact.classPK}, ${contact.accountId}, ${contact.parentContactId}, '${contact.emailAddress}', '${contact.firstName}', '${contact.middleName}', '${contact.lastName}', ${contact.prefixId}, ${contact.suffixId}, ${contact.male?string}, ${contact.birthday!'null'}, '${contact.smsSn}', '${contact.aimSn}', '${contact.facebookSn}', '${contact.icqSn}', '${contact.jabberSn}', '${contact.msnSn}', '${contact.mySpaceSn}', '${contact.skypeSn}', '${contact.twitterSn}', '${contact.ymSn}', '${contact.employeeStatusId}', '${contact.employeeNumber}', '${contact.jobTitle}', '${contact.jobClass}', '${contact.hoursOfOperation}');

	<#if !_user.defaultUser>
		<#list _roleIds as _roleId>
			insert into Users_Roles values (${_user.userId}, ${_roleId});
		</#list>

		<#list _groupIds as _groupId>
			insert into Users_Groups values (${_user.userId}, ${_groupId});
		</#list>

		<#local group = dataFactory.addGroup(_user)>

		<#if _user.userId != dataFactory.guestUser.userId>
			<#local publicLayouts = [dataFactory.addLayout(group.groupId, "home", "", "33,")]>
		</#if>

		<@insertGroup _group = group _publicLayouts = publicLayouts/>
	</#if>
</#macro>

<#--insert ResourcePermission-->

<#macro insertResourcePermission _resourceName _resourcePrimkey>
	<#local resourcePermissions = dataFactory.addResourcePermissions(_resourceName, _resourcePrimkey)>

	<#list resourcePermissions as resourcePermission>
		insert into ResourcePermission values (${resourcePermission.resourcePermissionId}, ${resourcePermission.companyId}, '${resourcePermission.name}', ${resourcePermission.scope}, '${resourcePermission.primKey}', ${resourcePermission.roleId}, ${resourcePermission.ownerId}, ${resourcePermission.actionIds});
	</#list>
</#macro>

<#--insert WikiPage-->

<#macro insertWikiPage _wikiPage>
	insert into WikiPage values ('${_wikiPage.uuid}', ${_wikiPage.pageId}, ${_wikiPage.resourcePrimKey}, ${_wikiPage.groupId}, ${_wikiPage.companyId}, ${_wikiPage.userId}, '${_wikiPage.userName}', '${_wikiPage.createDate?datetime}', '${_wikiPage.modifiedDate?datetime}', ${_wikiPage.nodeId}, '${_wikiPage.title}', ${_wikiPage.version}, ${_wikiPage.minorEdit?string}, '${_wikiPage.content}', '${_wikiPage.summary}', '${_wikiPage.format}', ${_wikiPage.head?string}, '${_wikiPage.parentTitle}', '${_wikiPage.redirectTitle}', ${_wikiPage.status}, ${_wikiPage.statusByUserId}, '${_wikiPage.statusByUserName}', ${_wikiPage.statusDate!'null'});

	<#local wikiPageResource = dataFactory.addWikiPageResource(_wikiPage)>

	insert into WikiPageResource values ('${wikiPageResource.uuid}', ${wikiPageResource.resourcePrimKey}, ${wikiPageResource.nodeId}, '${wikiPageResource.title}');
</#macro>