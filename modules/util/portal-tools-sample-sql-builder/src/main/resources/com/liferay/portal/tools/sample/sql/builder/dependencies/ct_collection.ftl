<#assign ctSchemaVersionModel = dataFactory.newCTSchemaVersionModel() />

${dataFactory.toInsertSQL(ctSchemaVersionModel)}

<#--Create a Publications specific group-->

<#assign
	groupModel = dataFactory.newPublicationGroupModel()
	groupId = groupModel.groupId
/>

<#include "ddl.ftl">

<@insertDLFolder
	_ddmStructureId = dataFactory.defaultDLDDMStructureId
	_dlFolderDepth = 1
	_groupModel = groupModel
	_parentDLFolderId = 0
/>

<#assign homePageContentLayoutModels = dataFactory.newContentPageLayoutModels(groupId, "home") />

<@insertContentPageLayout
	_fragmentEntryLinkModels = dataFactory.newFragmentEntryLinkModels(homePageContentLayoutModels)
	_layoutModels = homePageContentLayoutModels
	_templateFileName = "default-homepage-layout-definition.json"
/>

<#list dataFactory.newGroupLayoutModels(groupId) as groupLayoutModel>
	<@insertLayout _layoutModel = groupLayoutModel />
</#list>

<@insertGroup _groupModel = groupModel />

${csvFileWriter.write("repository", virtualHostModel.hostname + "," + groupModel.friendlyURL + "," + groupId + ", " + groupModel.name + "\n")}

<#--CTCollection data creation-->

<#list dataFactory.newCTCollectionModels(ctSchemaVersionModel) as ctCollectionModel>
	${dataFactory.toInsertSQL(ctCollectionModel)}

	<#list dataFactory.newResourcePermissionModels("com.liferay.change.tracking.model.CTCollection", ctCollectionModel.ctCollectionId?c, defaultAdminUserModel.userId) as resourcePermissionModel>
		${dataFactory.toInsertSQL(resourcePermissionModel)}
	</#list>

	<#assign
		ctCollectionId = ctCollectionModel.ctCollectionId
		ctCollectionIdString = ctCollectionModel.ctCollectionId?c
		publicationGroupId = groupId
	/>

	<#include "ct_ddm.ftl">

	<#include "ct_journal_article.ftl">

	<#include "ct_layout.ftl">
</#list>