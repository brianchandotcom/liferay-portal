<#include "../init.ftl">

<#if required>
	<#assign label = label + " (" + languageUtil.get(locale, "required") + ")">
</#if>

<@aui.select cssClass=cssClass helpMessage=field.tip label=label name=namespacedFieldName>
	${field.children}
</@aui.select>