<#assign layout = dataFactory.newLayout(dataFactory.guestGroup.groupId, "welcome", "58,", "47,")>

<@insertLayout _layout = layout />

${sampleSQLBuilder.insertGroup(dataFactory.guestGroup, 1)}

<#list dataFactory.groups as group>
	<#assign groupId = group.groupId>

	<#include "blogs.ftl">

	<#include "ddl.ftl">

	<#include "journal_article.ftl">

	<#include "mb.ftl">

	<#include "users.ftl">

	<#include "wiki.ftl">

	<@insertDLFolder _ddmStructureId = dataFactory.defaultDLDDMStructureId _dlFolderDepth = 1 _groupId = groupId _parentDLFolderId = 0 />

	<#assign publicLayouts = dataFactory.newPublicLayouts(groupId)>

	<#list publicLayouts as publicLayout >
		<@insertLayout _layout = publicLayout />
	</#list>

	<#assign publicPageCount = publicLayouts?size + maxDDLRecordSetCount + maxJournalArticleCount>

	${sampleSQLBuilder.insertGroup(group, publicPageCount)}

	${writerRepositoryCSV.write(groupId + ", " + group.name + "\n")}
</#list>