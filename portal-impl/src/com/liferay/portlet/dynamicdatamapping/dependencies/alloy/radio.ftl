<#include "../init.ftl">

<#if required>
	<#assign label = label + " (" + languageUtil.get(locale, "required") + ")">
</#if>

<@aui["field-wrapper"] helpMessage=field.tip label=label>
	${field.children}
</@>