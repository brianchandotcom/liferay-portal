<#setting number_format = "0">

<#assign mbMessageCounter = dataFactory.newInteger()>
<#assign wikiPageCounter = dataFactory.newInteger()>

<#assign privateLayouts = []>

<#list dataFactory.groups as group>
	<#assign publicLayouts = [
		dataFactory.addLayout(1, "Welcome", "/welcome", "58,", "47,")
	]>

	${sampleSQLBuilder.insertGroup(group, privateLayouts, publicLayouts)}
</#list>

<#list 1..maxGroupCount as groupCount>
	<#assign groupId = groupCount>

	<#assign group = dataFactory.addGroup(groupId, dataFactory.groupClassName.classNameId, groupId, "Community " + groupCount, "/community" + groupCount, true)>

	<#assign publicLayouts = [
		dataFactory.addLayout(1, "Welcome", "/welcome", "58,", "47,"),
		dataFactory.addLayout(2, "Blogs", "/blogs", "", "33,")
		dataFactory.addLayout(3, "Document Library", "/document_library", "", "20,")
		dataFactory.addLayout(4, "Forums", "/forums", "", "19,")
		dataFactory.addLayout(5, "Wiki", "/wiki", "", "36,")
	]>

	${sampleSQLBuilder.insertGroup(group, privateLayouts, publicLayouts)}

	<#include "users.ftl">

	<#include "blogs.ftl">

	<#include "dl.ftl">

	<#include "mb.ftl">

	<#include "wiki.ftl">
</#list>