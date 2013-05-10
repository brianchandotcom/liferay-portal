<#setting number_format = "computer">

<#macro insertAssetEntry
	_entry
	_currentIndex = -1
>
	<#local assetEntry = dataFactory.newAssetEntry(_entry)>

	insert into AssetEntry values (${assetEntry.entryId}, ${assetEntry.groupId}, ${assetEntry.companyId}, ${assetEntry.userId}, '${assetEntry.userName}', '${dataFactory.getDateString(assetEntry.createDate)}', '${dataFactory.getDateString(assetEntry.modifiedDate)}', ${assetEntry.classNameId}, ${assetEntry.classPK}, '${assetEntry.classUuid}', ${assetEntry.classTypeId}, ${assetEntry.visible?string}, '${dataFactory.getDateString(assetEntry.startDate)}', '${dataFactory.getDateString(assetEntry.endDate)}', '${dataFactory.getDateString(assetEntry.publishDate)}', '${dataFactory.getDateString(assetEntry.expirationDate)}', '${assetEntry.mimeType}', '${assetEntry.title}', '${assetEntry.description}', '${assetEntry.summary}', '${assetEntry.url}', '${assetEntry.layoutUuid}', ${assetEntry.height}, ${assetEntry.width}, ${assetEntry.priority}, ${assetEntry.viewCount});

	<#if (maxAssetCategoryCount > 0) && (_currentIndex != -1)>
		insert into AssetEntries_AssetCategories values (${assetEntry.entryId}, ${dataFactory.getAssetCategoryId(assetEntry.groupId, _currentIndex)});
	</#if>
</#macro>

<#macro insertDDMContent
	_ddmStorageLinkId
	_ddmStructureId
	_entry
	_currentIndex = -1
>
	<#if (_currentIndex = -1)>
		<#local ddmContent = dataFactory.newDDMContent(_entry)>
	<#else>
		<#local ddmContent = dataFactory.newDDMContent(_entry, _currentIndex)>
	</#if>

	insert into DDMContent values ('${ddmContent.uuid}', ${ddmContent.contentId}, ${ddmContent.groupId}, ${ddmContent.companyId}, ${ddmContent.userId}, '${ddmContent.userName}', '${dataFactory.getDateString(ddmContent.createDate)}', '${dataFactory.getDateString(ddmContent.modifiedDate)}', '${ddmContent.name}', '${ddmContent.description}', '${ddmContent.xml}');

	<#local ddmStorageLink = dataFactory.newDDMStorageLink(_ddmStorageLinkId, ddmContent, _ddmStructureId)>

	insert into DDMStorageLink values ('${ddmStorageLink.uuid}', ${ddmStorageLink.storageLinkId}, ${ddmStorageLink.classNameId}, ${ddmStorageLink.classPK}, ${ddmStorageLink.structureId});
</#macro>

<#macro insertDDMStructureLink
	_entry
>
	<#local ddmStructureLink = dataFactory.newDDMStructureLink(_entry)>

	insert into DDMStructureLink values (${ddmStructureLink.structureLinkId},${ ddmStructureLink.classNameId}, ${ddmStructureLink.classPK}, ${ddmStructureLink.structureId});
</#macro>

<#macro insertDLFolder
	_ddmStructureId
	_dlFolderDepth
	_groupId
	_parentDLFolderId
>
	<#if (_dlFolderDepth <= maxDLFolderDepth)>
		<#list 1..maxDLFolderCount as dlFolderCount>
			<#local dlFolder = dataFactory.newDLFolder(_groupId, _parentDLFolderId, dlFolderCount)>

			insert into DLFolder values ('${dlFolder.uuid}', ${dlFolder.folderId}, ${dlFolder.groupId}, ${dlFolder.companyId}, ${dlFolder.userId}, '${dlFolder.userName}', '${dataFactory.getDateString(dlFolder.createDate)}', '${dataFactory.getDateString(dlFolder.modifiedDate)}', ${dlFolder.repositoryId}, ${dlFolder.mountPoint?string}, ${dlFolder.parentFolderId}, '${dlFolder.name}', '${dlFolder.description}', '${dataFactory.getDateString(dlFolder.lastPostDate)}', ${dlFolder.defaultFileEntryTypeId}, ${dlFolder.hidden?string}, ${dlFolder.overrideFileEntryTypes?string}, ${dlFolder.status}, ${dlFolder.statusByUserId}, '${dlFolder.statusByUserName}', '${dataFactory.getDateString(dlFolder.statusDate)}');

			<@insertResourcePermissions
				_entry = dlFolder
			/>

			<@insertAssetEntry
				_entry = dlFolder
			/>

			<#if (maxDLFileEntryCount > 0)>
				<#list 1..maxDLFileEntryCount as dlFileEntryCount>
					<#local dlFileEntry = dataFactory.newDlFileEntry(dlFolder, dlFileEntryCount)>

					insert into DLFileEntry values ('${dlFileEntry.uuid}', ${dlFileEntry.fileEntryId}, ${dlFileEntry.groupId}, ${dlFileEntry.companyId}, ${dlFileEntry.userId}, '${dlFileEntry.userName}', ${dlFileEntry.versionUserId}, '${dlFileEntry.versionUserName}', '${dataFactory.getDateString(dlFileEntry.createDate)}', '${dataFactory.getDateString(dlFileEntry.modifiedDate)}', ${dlFileEntry.classNameId}, ${dlFileEntry.classPK}, ${dlFileEntry.repositoryId}, ${dlFileEntry.folderId}, '${dlFileEntry.name}', '${dlFileEntry.extension}', '${dlFileEntry.mimeType}', '${dlFileEntry.title}','${dlFileEntry.description}', '${dlFileEntry.extraSettings}', ${dlFileEntry.fileEntryTypeId}, '${dlFileEntry.version}', ${dlFileEntry.size}, ${dlFileEntry.readCount}, ${dlFileEntry.smallImageId}, ${dlFileEntry.largeImageId}, ${dlFileEntry.custom1ImageId}, ${dlFileEntry.custom2ImageId}, ${dlFileEntry.manualCheckInRequired?string});

					<#local dlFileVersion = dataFactory.newDLFileVersion(dlFileEntry)>

					insert into DLFileVersion values ('${dlFileVersion.uuid}', ${dlFileVersion.fileVersionId}, ${dlFileVersion.groupId}, ${dlFileVersion.companyId}, ${dlFileVersion.userId}, '${dlFileVersion.userName}', '${dataFactory.getDateString(dlFileVersion.createDate)}', '${dataFactory.getDateString(dlFileVersion.modifiedDate)}', ${dlFileVersion.repositoryId}, ${dlFileVersion.folderId}, ${dlFileVersion.fileEntryId}, '${dlFileVersion.extension}', '${dlFileVersion.mimeType}', '${dlFileVersion.title}','${dlFileVersion.description}', '${dlFileVersion.changeLog}', '${dlFileVersion.extraSettings}', ${dlFileVersion.fileEntryTypeId}, '${dlFileVersion.version}', ${dlFileVersion.size}, '${dlFileVersion.checksum}', ${dlFileVersion.status}, ${dlFileVersion.statusByUserId}, '${dlFileVersion.statusByUserName}', ${dlFileVersion.statusDate!'null'});

					<@insertResourcePermissions
						_entry = dlFileEntry
					/>

					<@insertAssetEntry
						_entry = dlFileEntry
						_currentIndex = dlFolderCount * maxDLFileEntryCount + dlFileEntryCount
					/>

					<#local ddmStorageLinkId = counter.get()>

					<@insertDDMContent
						_ddmStorageLinkId = ddmStorageLinkId
						_ddmStructureId = _ddmStructureId
						_entry = dlFileEntry
					/>

					<@insertMBDiscussion
						_classNameId = dataFactory.DLFileEntryClassNameId
						_classPK = dlFileEntry.fileEntryId
						_groupId = dlFileEntry.groupId
						_maxCommentCount = 0
						_mbRootMessageId = counter.get()
						_mbThreadId = counter.get()
					/>

					<@insertSocialActivity
						_entry = dlFileEntry
					/>

					<#local dlFileEntryMetadata = dataFactory.newDLFileEntryMetadata(ddmStorageLinkId, _ddmStructureId, dlFileVersion)>

					insert into DLFileEntryMetadata values ('${dlFileEntryMetadata.uuid}', ${dlFileEntryMetadata.fileEntryMetadataId}, ${dlFileEntryMetadata.DDMStorageId}, ${dlFileEntryMetadata.DDMStructureId}, ${dlFileEntryMetadata.fileEntryTypeId}, ${dlFileEntryMetadata.fileEntryId}, ${dlFileEntryMetadata.fileVersionId});

					<@insertDDMStructureLink
						_entry = dlFileEntryMetadata
					/>

					${writerDocumentLibraryCSV.write(dlFolder.folderId + "," + dlFileEntry.name + "," + dlFileEntry.fileEntryId + "," + dataFactory.getDateLong(dlFileEntry.createDate) + "," + dataFactory.getDateLong(dlFolder.createDate) + "\n")}
				</#list>
			</#if>

			<@insertDLFolder
				_ddmStructureId = _ddmStructureId
				_dlFolderDepth = _dlFolderDepth + 1
				_groupId = groupId
				_parentDLFolderId = dlFolder.folderId
			/>
		</#list>
	</#if>
</#macro>

<#macro insertGroup
	_group
	_publicPageCount
>
	insert into Group_ values ('${_group.uuid}', ${_group.groupId}, ${_group.companyId}, ${_group.creatorUserId}, ${_group.classNameId}, ${_group.classPK}, ${_group.parentGroupId}, ${_group.liveGroupId}, '${_group.treePath}', '${_group.name}', '${_group.description}', ${_group.type}, '${_group.typeSettings}', '${_group.friendlyURL}', ${_group.site?string}, ${_group.active?string});

	<#local layoutSets = dataFactory.newLayoutSets(_group.groupId, _publicPageCount)>

	<#list layoutSets as layoutSet>
		insert into LayoutSet values (${layoutSet.layoutSetId}, ${layoutSet.groupId}, ${layoutSet.companyId}, '${dataFactory.getDateString(layoutSet.createDate)}', '${dataFactory.getDateString(layoutSet.modifiedDate)}', ${layoutSet.privateLayout?string}, ${layoutSet.logo?string}, ${layoutSet.logoId}, '${layoutSet.themeId}', '${layoutSet.colorSchemeId}', '${layoutSet.wapThemeId}', '${layoutSet.wapColorSchemeId}', '${layoutSet.css}', ${layoutSet.pageCount}, '${layoutSet.settings}', '${layoutSet.layoutSetPrototypeUuid}', ${layoutSet.layoutSetPrototypeLinkEnabled?string});
	</#list>
</#macro>

<#macro insertLayout
	_layout
>
	insert into Layout values ('${_layout.uuid}', ${_layout.plid}, ${_layout.groupId}, ${_layout.companyId}, ${_layout.userId}, '${_layout.userName}', '${dataFactory.getDateString(_layout.createDate)}', '${dataFactory.getDateString(_layout.modifiedDate)}', ${_layout.privateLayout?string}, ${_layout.layoutId}, ${_layout.parentLayoutId}, '${_layout.name}', '${_layout.title}', '${_layout.description}', '${_layout.keywords}', '${_layout.robots}', '${_layout.type}', '${_layout.typeSettings}', ${_layout.hidden?string}, '${_layout.friendlyURL}', ${_layout.iconImage?string}, ${_layout.iconImageId}, '${_layout.themeId}', '${_layout.colorSchemeId}', '${_layout.wapThemeId}', '${_layout.wapColorSchemeId}', '${_layout.css}', ${_layout.priority}, '${_layout.layoutPrototypeUuid}', ${_layout.layoutPrototypeLinkEnabled?string}, '${_layout.sourcePrototypeLayoutUuid}');

	<@insertResourcePermissions
		_entry = _layout
	/>
</#macro>

<#macro insertMBDiscussion
	_classNameId
	_classPK
	_groupId
	_maxCommentCount
	_mbRootMessageId
	_mbThreadId
>
	<#local mbThread = dataFactory.newMBThread(_mbThreadId, _groupId, _mbRootMessageId, _maxCommentCount)>

	insert into MBThread values ('${mbThread.uuid}', ${mbThread.threadId}, ${mbThread.groupId}, ${mbThread.companyId}, ${mbThread.userId}, '${mbThread.userName}', '${dataFactory.getDateString(mbThread.createDate)}', '${dataFactory.getDateString(mbThread.modifiedDate)}', ${mbThread.categoryId}, ${mbThread.rootMessageId}, ${mbThread.rootMessageUserId}, ${mbThread.messageCount}, ${mbThread.viewCount}, ${mbThread.lastPostByUserId}, '${dataFactory.getDateString(mbThread.lastPostDate)}', ${mbThread.priority}, ${mbThread.question?string}, ${mbThread.status}, ${mbThread.statusByUserId}, '${mbThread.statusByUserName}', '${dataFactory.getDateString(mbThread.statusDate)}');

	<#local mbRootMessage = dataFactory.newMBMessage(mbThread, _classNameId, _classPK, 0)>

	<@insertMBMessage
		_mbMessage = mbRootMessage
	/>

	<#if (_maxCommentCount > 0)>
		<#list 1.._maxCommentCount as commentCount>
			<#local mbMessage = dataFactory.newMBMessage(mbThread, _classNameId, _classPK, commentCount)>

			<@insertMBMessage
				_mbMessage = mbMessage
			/>

			<@insertSocialActivity
				_entry = mbMessage
			/>
		</#list>
	</#if>

	<#local mbDiscussion = dataFactory.newMBDiscussion(_groupId, _classNameId, _classPK, _mbThreadId)>

	insert into MBDiscussion values ('${mbDiscussion.uuid}', ${mbDiscussion.discussionId}, ${mbDiscussion.groupId}, ${mbDiscussion.companyId}, ${mbDiscussion.userId}, '${mbDiscussion.userName}', '${dataFactory.getDateString(mbDiscussion.createDate)}', '${dataFactory.getDateString(mbDiscussion.modifiedDate)}', ${mbDiscussion.classNameId}, ${mbDiscussion.classPK}, ${mbDiscussion.threadId});
</#macro>

<#macro insertMBMessage
	_mbMessage
>
	insert into MBMessage values ('${_mbMessage.uuid}', ${_mbMessage.messageId}, ${_mbMessage.groupId}, ${_mbMessage.companyId}, ${_mbMessage.userId}, '${_mbMessage.userName}', '${dataFactory.getDateString(_mbMessage.createDate)}', '${dataFactory.getDateString(_mbMessage.modifiedDate)}', ${_mbMessage.classNameId}, ${_mbMessage.classPK}, ${_mbMessage.categoryId}, ${_mbMessage.threadId}, ${_mbMessage.rootMessageId}, ${_mbMessage.parentMessageId}, '${_mbMessage.subject}', '${_mbMessage.body}', '${_mbMessage.format}', ${_mbMessage.anonymous?string}, ${_mbMessage.priority}, ${_mbMessage.allowPingbacks?string}, ${_mbMessage.answer?string}, ${_mbMessage.status}, ${_mbMessage.statusByUserId}, '${_mbMessage.statusByUserName}', '${dataFactory.getDateString(_mbMessage.statusDate)}');

	<@insertAssetEntry
		_entry = _mbMessage
	/>
</#macro>

<#macro insertPortletPreferences
	_portletPreferences
>
	insert into PortletPreferences values (${_portletPreferences.portletPreferencesId}, ${_portletPreferences.ownerId}, ${_portletPreferences.ownerType}, ${_portletPreferences.plid}, '${_portletPreferences.portletId}', '${_portletPreferences.preferences}');

	<@insertResourcePermissions
		_entry = _portletPreferences
	/>
</#macro>

<#macro insertResourcePermissions
	_entry
>
	<#local resourcePermissions = dataFactory.newResourcePermissions(_entry)>

	<#list resourcePermissions as resourcePermission>
		insert into ResourcePermission values (${resourcePermission.resourcePermissionId}, ${resourcePermission.companyId}, '${resourcePermission.name}', ${resourcePermission.scope}, '${resourcePermission.primKey}', ${resourcePermission.roleId}, ${resourcePermission.ownerId}, ${resourcePermission.actionIds});
	</#list>
</#macro>

<#macro insertSocialActivity
	_entry
>
	<#local socialActivity = dataFactory.newSocialActivity(_entry)>

	insert into SocialActivity values (${socialActivity.activityId}, ${socialActivity.groupId}, ${socialActivity.companyId}, ${socialActivity.userId}, ${socialActivity.createDate}, ${socialActivity.activitySetId}, ${socialActivity.mirrorActivityId}, ${socialActivity.classNameId}, ${socialActivity.classPK}, ${socialActivity.type}, '${socialActivity.extraData}', ${socialActivity.receiverUserId});
</#macro>

<#macro insertSubscription
	_entry
>
	<#local subscription = dataFactory.newSubscription(_entry)>

	insert into Subscription values (${subscription.subscriptionId}, ${subscription.companyId}, ${subscription.userId}, '${subscription.userName}', '${dataFactory.getDateString(subscription.createDate)}', '${dataFactory.getDateString(subscription.modifiedDate)}', '${subscription.classNameId}', ${subscription.classPK}, '${subscription.frequency}');
</#macro>

<#macro insertUser
	_user
	_groupIds = []
	_roleIds = []
>
	insert into User_ values ('${_user.uuid}', ${_user.userId}, ${_user.companyId}, '${dataFactory.getDateString(_user.createDate)}', '${dataFactory.getDateString(_user.modifiedDate)}', ${_user.defaultUser?string}, ${_user.contactId}, '${_user.password}', ${_user.passwordEncrypted?string}, ${_user.passwordReset?string}, '${dataFactory.getDateString(_user.passwordModifiedDate)}', '${_user.digest}', '${_user.reminderQueryQuestion}', '${_user.reminderQueryAnswer}', ${_user.graceLoginCount}, '${_user.screenName}', '${_user.emailAddress}', ${_user.facebookId}, ${_user.ldapServerId}, '${_user.openId}', ${_user.portraitId}, '${_user.languageId}', '${_user.timeZoneId}', '${_user.greeting}', '${_user.comments}', '${_user.firstName}', '${_user.middleName}', '${_user.lastName}', '${_user.jobTitle}', '${dataFactory.getDateString(_user.loginDate)}', '${_user.loginIP}', '${dataFactory.getDateString(_user.lastLoginDate)}', '${_user.lastLoginIP}', '${dataFactory.getDateString(_user.lastFailedLoginDate)}', ${_user.failedLoginAttempts}, ${_user.lockout?string}, '${dataFactory.getDateString(_user.lockoutDate)}', ${_user.agreedToTermsOfUse?string}, ${_user.emailAddressVerified?string}, '${_user.status}');

	<#local contact = dataFactory.newContact(_user)>

	insert into Contact_ values (${contact.contactId}, ${contact.companyId}, ${contact.userId}, '${contact.userName}', '${dataFactory.getDateString(contact.createDate)}', '${dataFactory.getDateString(contact.modifiedDate)}', ${contact.classNameId}, ${contact.classPK}, ${contact.accountId}, ${contact.parentContactId}, '${contact.emailAddress}', '${contact.firstName}', '${contact.middleName}', '${contact.lastName}', ${contact.prefixId}, ${contact.suffixId}, ${contact.male?string}, '${dataFactory.getDateString(contact.birthday)}', '${contact.smsSn}', '${contact.aimSn}', '${contact.facebookSn}', '${contact.icqSn}', '${contact.jabberSn}', '${contact.msnSn}', '${contact.mySpaceSn}', '${contact.skypeSn}', '${contact.twitterSn}', '${contact.ymSn}', '${contact.employeeStatusId}', '${contact.employeeNumber}', '${contact.jobTitle}', '${contact.jobClass}', '${contact.hoursOfOperation}');

	<#list _roleIds as roleId>
		insert into Users_Roles values (${_user.userId}, ${roleId});
	</#list>

	<#list _groupIds as groupId>
		insert into Users_Groups values (${_user.userId}, ${groupId});
	</#list>
</#macro>