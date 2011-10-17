<#include "../init.ftl">

<#if required>
	<#assign label = label + " (" + languageUtil.get(locale, "required") + ")">
</#if>

<@aui["field-wrapper"] helpMessage=fieldStructure.tip label=label>
	${fieldStructure.children}
</@>