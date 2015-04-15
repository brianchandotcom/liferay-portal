<#assign layoutModel = dataFactory.newLayoutModel(dataFactory.guestGroupModel.groupId, "welcome", "58,", "47,")>

<@insertLayout
	_layoutModel = layoutModel
/>

<@insertGroup
	_groupModel = dataFactory.globalGroupModel
	_publicPageCount = 1
/>

<@insertGroup
	_groupModel = dataFactory.guestGroupModel
	_pageCount = 1
/>

<#list dataFactory.groupModels as groupModel>
	<#assign groupId = groupModel.groupId>

	<#include "asset_publisher.ftl">

	<#include "blogs.ftl">

	<#include "ddl.ftl">

	<#include "journal_article.ftl">

	<#include "mb.ftl">

	<#include "users.ftl">

	<#include "wiki.ftl">

	<@insertDLFolder
		_ddmStructureId = dataFactory.defaultDLDDMStructureId
		_dlFolderDepth = 1
		_groupId = groupId
		_parentDLFolderId = 0
	/>

	<#assign layoutModels = dataFactory.newLayoutModels(groupId)>

	<#list layoutModels as layoutModel >
		<@insertLayout
			_layoutModel = layoutModel
		/>
	</#list>

	<#assign pageCount = layoutModels?size + dataFactory.maxDDLRecordSetCount + dataFactory.maxJournalArticleCount>

	<@insertGroup
		_groupModel = groupModel
		_pageCount = pageCount
	/>

	${repositoryCSVWriter.write(groupId + ", " + groupModel.name + "\n")}
</#list>