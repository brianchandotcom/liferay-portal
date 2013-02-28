<#setting number_format = "0">

${sampleSQLBuilder.insertGroup(dataFactory.guestGroup, [dataFactory.newLayout(1, "Welcome", "/welcome", "58,", "47,")])}

<#list dataFactory.groups as group>
	<#assign groupId = group.groupId>

	<#assign publicLayouts = [
		dataFactory.newLayout(1, "Welcome", "/welcome", "58,", "47,"),
		dataFactory.newLayout(2, "Blogs", "/blogs", "", "33,")
		dataFactory.newLayout(3, "Document Library", "/document_library", "", "20,")
		dataFactory.newLayout(4, "Forums", "/forums", "", "19,")
		dataFactory.newLayout(5, "Wiki", "/wiki", "", "36,")
	]>

	<#include "users.ftl">

	<#include "blogs.ftl">

	<#include "ddl.ftl">

	<#include "dl.ftl">

	<#include "journal_article.ftl">

	<#include "mb.ftl">

	<#include "wiki.ftl">

	${sampleSQLBuilder.insertGroup(group, publicLayouts)}
</#list>