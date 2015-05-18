<#include "init.ftl">

<#assign themeDisplayVariable = "themeDisplay">

<#if language == "vm">
	<#assign themeDisplayVariable = "$" + themeDisplayVariable>

	<#assign fieldType = "$" + fieldType>

	<#assign fieldValue = "$" + fieldValue>
</#if>

<#assign displayFieldValue = "ddmUtil.getDisplayFieldValue(" + themeDisplayVariable + ", " + fieldValue + ", " + fieldType + ")">