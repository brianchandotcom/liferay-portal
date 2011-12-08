<#include "../init.ftl">

<#-- Multiple -->

<#assign multiple = false>

<#if fieldStructure.multiple?? && (fieldStructure.multiple == "true")>
	<#assign multiple = true>
</#if>

<#if required>
	<#assign label = label + " (" + languageUtil.get(locale, "required") + ")">
</#if>

<@aui.select cssClass=cssClass helpMessage=fieldStructure.tip label=label multiple=multiple name=namespacedFieldName>
	${fieldStructure.children}
</@aui.select>