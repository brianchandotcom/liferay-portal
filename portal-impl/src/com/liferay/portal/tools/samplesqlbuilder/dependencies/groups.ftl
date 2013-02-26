<#setting number_format = "0">

${sampleSQLBuilder.insertGroup(dataFactory.guestGroup, [dataFactory.addLayout(1, "Welcome", "/welcome", "58,", "47,")])}

<#list dataFactory.groups as group>
	<#assign groupId = group.groupId>

	<#assign publicLayouts = [
		dataFactory.addLayout(1, "Welcome", "/welcome", "58,", "47,"),
		dataFactory.addLayout(2, "Blogs", "/blogs", "", "33,")
		dataFactory.addLayout(3, "Document Library", "/document_library", "", "20,")
		dataFactory.addLayout(4, "Forums", "/forums", "", "19,")
		dataFactory.addLayout(5, "Wiki", "/wiki", "", "36,")
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