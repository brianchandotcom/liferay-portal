<#setting number_format = "0">

<#assign mbMessageCounter = dataFactory.newInteger()>
<#assign wikiPageCounter = dataFactory.newInteger()>

<#list dataFactory.groups as group>
	<#include "groups_guest_private_layouts.ftl">
	<#include "groups_guest_public_layouts.ftl">

	${sampleSQLBuilder.insertGroup(group, privateLayouts, publicLayouts)}
</#list>

<#list 1..maxGroupCount as groupCount>
	<#assign groupId = groupCount>

	<#assign group = dataFactory.addGroup(groupId, dataFactory.groupClassName.classNameId, groupId, "Community " + groupCount, "/community" + groupCount, true)>

	<#include "groups_group_private_layouts.ftl">
	<#include "groups_group_public_layouts.ftl">

	${sampleSQLBuilder.insertGroup(group, privateLayouts, publicLayouts)}

	<#include "users.ftl">

	<#include "blogs.ftl">

	<#include "mb.ftl">

	<#include "wiki.ftl">

	<#include "dl.ftl">
</#list>