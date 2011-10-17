<#include "../init.ftl">

<#if required>
	<#assign label = label + " (" + languageUtil.get(locale, "required") + ")">
</#if>

<@aui.select cssClass=cssClass helpMessage=fieldStructure.tip label=label name=namespacedFieldName>
	${fieldStructure.children}
</@aui.select>