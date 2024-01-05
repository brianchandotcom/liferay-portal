<#include "guest_user.ftl">

<#include "commerce_groups.ftl">

<#include "asset.ftl">

<#include "ddm.ftl">

<#include "segments.ftl">

<#list dataFactory.newGroupModels() as groupModel>
	<#assign groupId = groupModel.groupId />

	<#include "asset_publisher.ftl">

	<#include "blogs.ftl">

	<#include "ddl.ftl">

	<#include "journal_article.ftl">

	<#include "fragment.ftl">

	<#include "mb.ftl">

	<#include "users.ftl">

	<#include "wiki.ftl">

	<@insertDLFolder
		_ddmStructureId=dataFactory.defaultDLDDMStructureId
		_dlFolderDepth=1
		_groupModel=groupModel
		_parentDLFolderId=0
	/>

	<#assign homePageContentLayoutModels = dataFactory.newContentPageLayoutModels(groupId, "home") />

	<@insertContentPageLayout
		_fragmentEntryLinkModels=dataFactory.newFragmentEntryLinkModels(homePageContentLayoutModels)
		_layoutModels=homePageContentLayoutModels
		_templateFileName="default-homepage-layout-definition.json"
	/>

	<#list dataFactory.newGroupLayoutModels(groupId) as groupLayoutModel>
		<@insertLayout _layoutModel=groupLayoutModel />
	</#list>

	<@insertGroup _groupModel=groupModel />

	${csvFileWriter.write("repository", virtualHostModel.hostname + "," + groupModel.friendlyURL + "," + groupId + ", " + groupModel.name + "\n")}
</#list>

<#assign defaultSiteHomePageContentLayoutModels = dataFactory.newContentPageLayoutModels(guestGroupModel.groupId, "home") />

<@insertContentPageLayout
	_fragmentEntryLinkModels=dataFactory.newFragmentEntryLinkModels(defaultSiteHomePageContentLayoutModels)
	_layoutModels=defaultSiteHomePageContentLayoutModels
	_templateFileName="default-homepage-layout-definition.json"
/>

<#assign
	searchLayoutModel = dataFactory.newSearchLayoutModel(guestGroupModel.groupId, true)
	layoutPrototypeModel = dataFactory.newLayoutPrototypeModel(defaultAdminUserModel.userId)
	searchTemplateGroupModel = dataFactory.newSearchTemplateGroupModel(layoutPrototypeModel.layoutPrototypeId, defaultAdminUserModel.userId)
	searchGroupLayoutModel = dataFactory.newSearchGroupLayoutModel(searchTemplateGroupModel.groupId, searchLayoutModel)
/>

<@insertLayout _layoutModel=searchLayoutModel />

<@insertLayout _layoutModel=searchGroupLayoutModel />

${dataFactory.toInsertSQL(layoutPrototypeModel)}

<@insertGroup _groupModel=searchTemplateGroupModel />