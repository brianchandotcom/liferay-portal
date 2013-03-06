<#setting number_format = "0">

<#assign layout = dataFactory.newLayout(dataFactory.guestGroup.groupId, "welcome", "58,", "47,")>

${sampleSQLBuilder.insertLayout(layout)}

${sampleSQLBuilder.insertGroup(dataFactory.guestGroup, 1)}

<#list dataFactory.groups as group>
	<#assign groupId = group.groupId>

	<#assign publicLayouts = dataFactory.newPublicLayouts(groupId)>

	<#list publicLayouts as publicLayout >
		${sampleSQLBuilder.insertLayout(publicLayout)}
	</#list>

	<#include "users.ftl">

	<#include "blogs.ftl">

	<#include "ddl.ftl">

	<#include "dl.ftl">

	<#include "journal_article.ftl">

	<#include "mb.ftl">

	<#include "wiki.ftl">

	<#assign publicPageCount = publicLayouts?size + maxDDLRecordSetCount + maxJournalArticleCount>

	${sampleSQLBuilder.insertGroup(group, publicPageCount)}
</#list>