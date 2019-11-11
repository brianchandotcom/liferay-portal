<#assign layoutModel = dataFactory.newLayoutModel(dataFactory.guestGroupModel.groupId, "welcome", "com_liferay_login_web_portlet_LoginPortlet,", "com_liferay_hello_world_web_portlet_HelloWorldPortlet,") />

<@insertLayout _layoutModel=layoutModel />

<@insertGroup _groupModel=dataFactory.commerceCatalogGroupModel />

<@insertGroup _groupModel=dataFactory.commerceChannelGroupModel />

<@insertGroup _groupModel=dataFactory.globalGroupModel />

<@insertGroup _groupModel=dataFactory.guestGroupModel />

<@insertGroup _groupModel=dataFactory.userPersonalSiteGroupModel />

<#list dataFactory.groupModels as groupModel>
	<#assign groupId = groupModel.groupId />

	<#include "asset_publisher.ftl">

	<#include "blogs.ftl">

	<#include "ddl.ftl">

	<#include "journal_article.ftl">

	<#include "mb.ftl">

	<#include "users.ftl">

	<#include "wiki.ftl">

	<@insertDLFolder
		_ddmStructureId=dataFactory.defaultDLDDMStructureId
		_dlFolderDepth=1
		_groupId=groupId
		_parentDLFolderId=0
	/>

	<#assign publicLayoutModels = dataFactory.newPublicLayoutModels(groupId) />

	<#list publicLayoutModels as publicLayoutModel>
		<@insertLayout _layoutModel=publicLayoutModel />
	</#list>

	<#assign contentLayoutModels = dataFactory.newContentLayoutModels(groupId) />

	<#list contentLayoutModels as contentLayoutModel>
		<@insertContentLayout _layoutModel=contentLayoutModel />
	</#list>

	<@insertGroup _groupModel=groupModel />

	${dataFactory.getCSVWriter("repository").write(groupId + ", " + groupModel.name + "\n")}
</#list>