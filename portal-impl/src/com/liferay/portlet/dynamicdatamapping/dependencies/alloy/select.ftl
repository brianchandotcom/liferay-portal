<#include "../init.ftl">

<#assign multiple = false>

<#if fieldStructure.multiple?? && (fieldStructure.multiple == "true")>
	<#assign multiple = true>
</#if>

<#if required>
	<#assign label = label + " (" + languageUtil.get(requestedLocale, "required") + ")">
</#if>

<@aui["field-wrapper"] data=data helpMessage=escape(fieldStructure.tip)>
	<@aui.select cssClass=cssClass label=escape(label) multiple=multiple name=namespacedFieldName>
		${fieldStructure.children}
	</@aui.select>
</@>