<#include "../init.ftl">

<#if required>
	<#assign label = label + " (" + languageUtil.get(locale, "required") + ")">
</#if>

<@aui["field-wrapper"] data=data helpMessage=escape(fieldStructure.tip) label=escape(label)>
	${fieldStructure.children}
</@>