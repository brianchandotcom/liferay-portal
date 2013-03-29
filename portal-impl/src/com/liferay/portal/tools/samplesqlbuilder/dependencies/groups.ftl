<#assign layout = dataFactory.newLayout(dataFactory.guestGroup.groupId, "welcome", "58,", "47,")>

${sampleSQLBuilder.insertLayout(layout)}

${sampleSQLBuilder.insertGroup(dataFactory.guestGroup, 1)}

<#list dataFactory.groups as group>
	<#assign groupId = group.groupId>

	<#include "blogs.ftl">

	<#include "ddl.ftl">

	<#include "journal_article.ftl">

	<#include "mb.ftl">

	<#include "users.ftl">

	<#include "wiki.ftl">

	${sampleSQLBuilder.insertDLFolders(groupId, 0, 1, dataFactory.defaultDLDDMStructureId)}

	<#assign publicLayouts = dataFactory.newPublicLayouts(groupId)>

	<#list publicLayouts as publicLayout >
		${sampleSQLBuilder.insertLayout(publicLayout)}
	</#list>

	<#assign publicPageCount = publicLayouts?size + maxDDLRecordSetCount + maxJournalArticleCount>

	${sampleSQLBuilder.insertGroup(group, publicPageCount)}

	${writerRepositoryCSV.write(groupId + ", " + group.name + "\n")}
</#list>