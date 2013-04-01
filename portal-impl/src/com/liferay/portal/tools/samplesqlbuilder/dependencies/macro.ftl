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

<#macro insertDLSync _entry>
	<#local dlSync = dataFactory.newDLSync(_entry)>

	insert into DLSync values (${dlSync.syncId}, ${dlSync.companyId}, ${dlSync.createDate}, ${dlSync.modifiedDate}, ${dlSync.fileId}, '${dlSync.fileUuid}', ${dlSync.repositoryId}, ${dlSync.parentFolderId}, '${dlSync.name}', '${dlSync.description}', '${dlSync.event}', '${dlSync.type}', '${dlSync.version}');
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