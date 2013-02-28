<#setting number_format = "0">

${sampleSQLBuilder.insertGroup(dataFactory.guestGroup, [dataFactory.newLayout(dataFactory.guestGroup.groupId, "welcome", "58,", "47,")])}

<#list dataFactory.groups as group>
	<#assign groupId = group.groupId>

	<#assign publicLayouts = dataFactory.newCommonLayouts(groupId)>

	<#include "users.ftl">

	<#include "blogs.ftl">

	<#include "ddl.ftl">

	<#include "dl.ftl">

	<#include "journal_article.ftl">

	<#include "mb.ftl">

	<#include "wiki.ftl">

	${sampleSQLBuilder.insertGroup(group, publicLayouts)}
</#list>