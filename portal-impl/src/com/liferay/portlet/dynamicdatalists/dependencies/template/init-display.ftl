<#include "init.ftl">

<#assign themeDisplayVariable = "themeDisplay">

<#if language == "vm">
	<#assign fieldType = "$" + fieldType>

	<#assign fieldValue = "$" + fieldValue>

	<#assign themeDisplayVariable = "$" + themeDisplayVariable>
</#if>

<#assign displayFieldValue = "ddmUtil.getDisplayFieldValue(" + fieldValue + ", " + fieldType + ", " + themeDisplayVariable + ")">