<#assign
	globalGroupModel = dataFactory.newGlobalGroupModel()
	guestGroupModel = dataFactory.newGuestGroupModel()

	commerceCurrencyModel = dataFactory.newCommerceCurrencyModel()
	countryModel = dataFactory.newCountryModel()
/>

${dataFactory.toInsertSQL(commerceCurrencyModel)}

${dataFactory.toInsertSQL(countryModel)}

<#include "default_user.ftl">

<#include "segments.ftl">

<#include "commerce_groups.ftl">

<@insertLayout _layoutModel=dataFactory.newLayoutModel(guestGroupModel.groupId, "welcome", "com_liferay_login_web_portlet_LoginPortlet,", "com_liferay_hello_world_web_portlet_HelloWorldPortlet,") />

<@insertGroup _groupModel=globalGroupModel />

<@insertGroup _groupModel=guestGroupModel />

<@insertGroup _groupModel=dataFactory.newUserPersonalSiteGroupModel() />

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

	<#assign
		homePageContentLayoutModels = dataFactory.newContentPageLayoutModels(groupId, "welcome")

		groupLayoutModels = dataFactory.newGroupLayoutModels(groupId)
	/>

	<@insertContentPageLayout
		_fragmentEntryLinkModels=dataFactory.newFragmentEntryLinkModels(homePageContentLayoutModels)
		_layoutModels=homePageContentLayoutModels
		_templateFileName="default-homepage-layout-definition.json"
	/>

	<#list groupLayoutModels as groupLayoutModel>
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