<#assign
	globalGroupModel = dataFactory.newGlobalGroupModel()
	guestGroupModel = dataFactory.newGuestGroupModel()
/>

<#include "guest_user.ftl">

<#include "commerce_groups.ftl">

<@insertGroup _groupModel=globalGroupModel />

<@insertGroup _groupModel=guestGroupModel />

<@insertGroup _groupModel=dataFactory.newUserPersonalSiteGroupModel() />

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
		_groupId=groupId
		_parentDLFolderId=0
	/>

	<#assign homePageContentLayoutModels = dataFactory.newContentPageLayoutModels(groupId, "welcome") />

	<@insertContentPageLayout
		_fragmentEntryLinkModels=dataFactory.newFragmentEntryLinkModels(homePageContentLayoutModels)
		_layoutModels=homePageContentLayoutModels
		_templateFileName="default-homepage-layout-definition.json"
	/>

	<#list dataFactory.newGroupLayoutModels(groupId) as groupLayoutModel>
		<@insertLayout _layoutModel=groupLayoutModel />
	</#list>

	<@insertGroup _groupModel=groupModel />

	${csvFileWriter.write("repository", groupId + ", " + groupModel.name + "\n")}
</#list>

<#assign defaultSiteHomePageContentLayoutModels = dataFactory.newContentPageLayoutModels(guestGroupModel.groupId, "welcome") />

<@insertContentPageLayout
	_fragmentEntryLinkModels=dataFactory.newFragmentEntryLinkModels(defaultSiteHomePageContentLayoutModels)
	_layoutModels=defaultSiteHomePageContentLayoutModels
	_templateFileName="default-homepage-layout-definition.json"
/>