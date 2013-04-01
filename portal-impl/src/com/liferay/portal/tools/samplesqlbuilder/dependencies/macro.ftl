<#setting number_format = "computer">

<#macro insertAssetEntry _entry>
	<#local assetEntry = dataFactory.newAssetEntry(_entry)>

	insert into AssetEntry values (${assetEntry.entryId}, ${assetEntry.groupId}, ${assetEntry.companyId}, ${assetEntry.userId}, '${assetEntry.userName}', '${dataFactory.getDateString(assetEntry.createDate)}', '${dataFactory.getDateString(assetEntry.modifiedDate)}', ${assetEntry.classNameId}, ${assetEntry.classPK}, '${assetEntry.classUuid}', ${assetEntry.classTypeId}, ${assetEntry.visible?string}, '${dataFactory.getDateString(assetEntry.startDate)}', '${dataFactory.getDateString(assetEntry.endDate)}', '${dataFactory.getDateString(assetEntry.publishDate)}', '${dataFactory.getDateString(assetEntry.expirationDate)}', '${assetEntry.mimeType}', '${assetEntry.title}', '${assetEntry.description}', '${assetEntry.summary}', '${assetEntry.url}', '${assetEntry.layoutUuid}', ${assetEntry.height}, ${assetEntry.width}, ${assetEntry.priority}, ${assetEntry.viewCount});
</#macro>

<#macro insertDDMContent _ddmStorageLinkId _ddmStructureId _entry _currentIndex = -1>
	<#if (_currentIndex = -1)>
		<#local ddmContent = dataFactory.newDDMContent(_entry)>
	<#else>
		<#local ddmContent = dataFactory.newDDMContent(_entry, _currentIndex)>
	</#if>

	insert into DDMContent values ('${ddmContent.uuid}', ${ddmContent.contentId}, ${ddmContent.groupId}, ${ddmContent.companyId}, ${ddmContent.userId}, '${ddmContent.userName}', '${dataFactory.getDateString(ddmContent.createDate)}', '${dataFactory.getDateString(ddmContent.modifiedDate)}', '${ddmContent.name}', '${ddmContent.description}', '${ddmContent.xml}');

	<#local ddmStorageLink = dataFactory.newDDMStorageLink(_ddmStorageLinkId, ddmContent, _ddmStructureId)>

	insert into DDMStorageLink values ('${ddmStorageLink.uuid}', ${ddmStorageLink.storageLinkId}, ${ddmStorageLink.classNameId}, ${ddmStorageLink.classPK}, ${ddmStorageLink.structureId});
</#macro>

<#macro insertDDMStructureLink _entry>
	<#local ddmStructureLink = dataFactory.newDDMStructureLink(_entry)>

	insert into DDMStructureLink values (${ddmStructureLink.structureLinkId},${ ddmStructureLink.classNameId}, ${ddmStructureLink.classPK}, ${ddmStructureLink.structureId});
</#macro>

<#macro insertDLFolder _ddmStructureId _dlFolderDepth _groupId _parentDLFolderId  >
	<#if (_dlFolderDepth <= maxDLFolderDepth)>
		<#list 1..maxDLFolderCount as dlFolderCount>
			<#local dlFolder = dataFactory.newDLFolder(_groupId, _parentDLFolderId, dlFolderCount)>

			insert into DLFolder values ('${dlFolder.uuid}', ${dlFolder.folderId}, ${dlFolder.groupId}, ${dlFolder.companyId}, ${dlFolder.userId}, '${dlFolder.userName}', '${dataFactory.getDateString(dlFolder.createDate)}', '${dataFactory.getDateString(dlFolder.modifiedDate)}', ${dlFolder.repositoryId}, ${dlFolder.mountPoint?string}, ${dlFolder.parentFolderId}, '${dlFolder.name}', '${dlFolder.description}', '${dataFactory.getDateString(dlFolder.lastPostDate)}', ${dlFolder.defaultFileEntryTypeId}, ${dlFolder.hidden?string}, ${dlFolder.overrideFileEntryTypes?string}, ${dlFolder.status}, ${dlFolder.statusByUserId}, '${dlFolder.statusByUserName}', '${dataFactory.getDateString(dlFolder.statusDate)}');

			<#if (maxDLFileEntryCount > 0)>
				<#list 1..maxDLFileEntryCount as dlFileEntryCount>
					<#local dlFileEntry = dataFactory.newDlFileEntry(dlFolder, dlFileEntryCount)>

					${sampleSQLBuilder.insertDLFileEntry(dlFileEntry, _ddmStructureId)}

					${writerDocumentLibraryCSV.write(dlFolder.folderId + "," + dlFileEntry.name + "," + dlFileEntry.fileEntryId + "," + dataFactory.getDateLong(dlFileEntry.createDate) + "," + dataFactory.getDateLong(dlFolder.createDate) +"\n")}
				</#list>
			</#if>

			<@insertDLFolder _ddmStructureId = _ddmStructureId _dlFolderDepth = _dlFolderDepth + 1 _groupId = groupId _parentDLFolderId = dlFolder.folderId />

			<@insertDLSync _entry = dlFolder />
		</#list>
	</#if>
</#macro>

<#macro insertDLSync _entry>
	<#local dlSync = dataFactory.newDLSync(_entry)>

	insert into DLSync values (${dlSync.syncId}, ${dlSync.companyId}, ${dlSync.createDate}, ${dlSync.modifiedDate}, ${dlSync.fileId}, '${dlSync.fileUuid}', ${dlSync.repositoryId}, ${dlSync.parentFolderId}, '${dlSync.name}', '${dlSync.description}', '${dlSync.event}', '${dlSync.type}', '${dlSync.version}');
</#macro>

<#macro insertGroup _group _publicPageCount>
	insert into Group_ values (${_group.groupId}, ${_group.companyId}, ${_group.creatorUserId}, ${_group.classNameId}, ${_group.classPK}, ${_group.parentGroupId}, ${_group.liveGroupId}, '${_group.treePath}', '${_group.name}', '${_group.description}', ${_group.type}, '${_group.typeSettings}', '${_group.friendlyURL}', ${_group.site?string}, ${_group.active?string});

	<#local layoutSets = dataFactory.newLayoutSets(_group.groupId, _publicPageCount)>

	<#list layoutSets as layoutSet>
		insert into LayoutSet values (${layoutSet.layoutSetId}, ${layoutSet.groupId}, ${layoutSet.companyId}, '${dataFactory.getDateString(layoutSet.createDate)}', '${dataFactory.getDateString(layoutSet.modifiedDate)}', ${layoutSet.privateLayout?string}, ${layoutSet.logo?string}, ${layoutSet.logoId}, '${layoutSet.themeId}', '${layoutSet.colorSchemeId}', '${layoutSet.wapThemeId}', '${layoutSet.wapColorSchemeId}', '${layoutSet.css}', ${layoutSet.pageCount}, '${layoutSet.settings}', '${layoutSet.layoutSetPrototypeUuid}', ${layoutSet.layoutSetPrototypeLinkEnabled?string});
	</#list>
</#macro>

<#macro insertLayout _layout>
	insert into Layout values ('${_layout.uuid}', ${_layout.plid}, ${_layout.groupId}, ${_layout.companyId}, '${dataFactory.getDateString(_layout.createDate)}', '${dataFactory.getDateString(_layout.modifiedDate)}', ${_layout.privateLayout?string}, ${_layout.layoutId}, ${_layout.parentLayoutId}, '${_layout.name}', '${_layout.title}', '${_layout.description}', '${_layout.keywords}', '${_layout.robots}', '${_layout.type}', '${_layout.typeSettings}', ${_layout.hidden?string}, '${_layout.friendlyURL}', ${_layout.iconImage?string}, ${_layout.iconImageId}, '${_layout.themeId}', '${_layout.colorSchemeId}', '${_layout.wapThemeId}', '${_layout.wapColorSchemeId}', '${_layout.css}', ${_layout.priority}, '${_layout.layoutPrototypeUuid}', ${_layout.layoutPrototypeLinkEnabled?string}, '${_layout.sourcePrototypeLayoutUuid}');

	${sampleSQLBuilder.insertResourcePermission("com.liferay.portal.model.Layout", stringUtil.valueOf(_layout.plid))}

	<@insertMBDiscussion _classNameId = dataFactory.layoutClassNameId _classPK = _layout.plid _groupId = _layout.groupId _maxCommentCount = 0 _mbRootMessageId = counter.get() _mbThreadId = counter.get() />
</#macro>

<#macro insertMBDiscussion _classNameId _classPK _groupId _maxCommentCount _mbRootMessageId _mbThreadId>
	<#local mbThread = dataFactory.newMBThread(_mbThreadId, _groupId, _mbRootMessageId, _maxCommentCount)>

	insert into MBThread values ('${mbThread.uuid}', ${mbThread.threadId}, ${mbThread.groupId}, ${mbThread.companyId}, ${mbThread.userId}, '${mbThread.userName}', '${dataFactory.getDateString(mbThread.createDate)}', '${dataFactory.getDateString(mbThread.modifiedDate)}', ${mbThread.categoryId}, ${mbThread.rootMessageId}, ${mbThread.rootMessageUserId}, ${mbThread.messageCount}, ${mbThread.viewCount}, ${mbThread.lastPostByUserId}, '${dataFactory.getDateString(mbThread.lastPostDate)}', ${mbThread.priority}, ${mbThread.question?string}, ${mbThread.status}, ${mbThread.statusByUserId}, '${mbThread.statusByUserName}', '${dataFactory.getDateString(mbThread.statusDate)}');

	<#local mbRootMessage = dataFactory.newMBMessage(mbThread, _classNameId, _classPK, 0)>

	<@insertMBMessage _mbMessage = mbRootMessage />

	<#if (_maxCommentCount > 0)>
		<#list 1.._maxCommentCount as commentCount>
			<#local mbMessage = dataFactory.newMBMessage(mbThread, _classNameId, _classPK, commentCount)>

			<@insertMBMessage _mbMessage = mbMessage />
		</#list>
	</#if>

	<#local mbDiscussion = dataFactory.newMBDiscussion(_groupId, _classNameId, _classPK, _mbThreadId)>

	insert into MBDiscussion values ('${mbDiscussion.uuid}', ${mbDiscussion.discussionId}, ${mbDiscussion.groupId}, ${mbDiscussion.companyId}, ${mbDiscussion.userId}, '${mbDiscussion.userName}', '${dataFactory.getDateString(mbDiscussion.createDate)}', '${dataFactory.getDateString(mbDiscussion.modifiedDate)}', ${mbDiscussion.classNameId}, ${mbDiscussion.classPK}, ${mbDiscussion.threadId});
</#macro>

<#macro insertMBMessage _mbMessage>
	insert into MBMessage values ('${_mbMessage.uuid}', ${_mbMessage.messageId}, ${_mbMessage.groupId}, ${_mbMessage.companyId}, ${_mbMessage.userId}, '${_mbMessage.userName}', '${dataFactory.getDateString(_mbMessage.createDate)}', '${dataFactory.getDateString(_mbMessage.modifiedDate)}', ${_mbMessage.classNameId}, ${_mbMessage.classPK}, ${_mbMessage.categoryId}, ${_mbMessage.threadId}, ${_mbMessage.rootMessageId}, ${_mbMessage.parentMessageId}, '${_mbMessage.subject}', '${_mbMessage.body}', '${_mbMessage.format}', ${_mbMessage.anonymous?string}, ${_mbMessage.priority}, ${_mbMessage.allowPingbacks?string}, ${_mbMessage.answer?string}, ${_mbMessage.status}, ${_mbMessage.statusByUserId}, '${_mbMessage.statusByUserName}', '${dataFactory.getDateString(_mbMessage.statusDate)}');

	<@insertAssetEntry _entry = _mbMessage />
</#macro>

<#macro insertPortletPreferences _entry _plid _portletId = 'null'>
	<#if (_portletId = 'null')>
		<#local portletPreferencesList = dataFactory.newPortletPreferences(_plid, _entry)>
	<#else>
		<#local portletPreferencesList = dataFactory.newPortletPreferences(_plid, _portletId, _entry)>
	</#if>

	<#list portletPreferencesList as portletPreferences>
		insert into PortletPreferences values (${portletPreferences.portletPreferencesId}, ${portletPreferences.ownerId}, ${portletPreferences.ownerType}, ${portletPreferences.plid}, '${portletPreferences.portletId}', '${portletPreferences.preferences}');

		<#local primKey = dataFactory.getPortletPermissionPrimaryKey(layout.plid, portletPreferences.portletId)>

		${sampleSQLBuilder.insertResourcePermission(portletPreferences.portletId, primKey)}
	</#list>
</#macro>

<#macro insertSocialActivity _entry>
	<#local socialActivity = dataFactory.newSocialActivity(_entry)>

	insert into SocialActivity values (${socialActivity.activityId}, ${socialActivity.groupId}, ${socialActivity.companyId}, ${socialActivity.userId}, ${socialActivity.createDate}, ${socialActivity.activitySetId}, ${socialActivity.mirrorActivityId}, ${socialActivity.classNameId}, ${socialActivity.classPK}, ${socialActivity.type}, '${socialActivity.extraData}', ${socialActivity.receiverUserId});
</#macro>