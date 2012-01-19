<#include "../init.ftl">

<#assign fileName = "">
<#assign recordId = "">

<#if (fields??) && (fieldValue != "")>
	<#assign fileJSONObject = getFileJSONObject(fieldRawValue)>

	<#assign fileName = fileJSONObject.getString("name")>
	<#assign className = fileJSONObject.getString("className")>
	<#assign classPK = fileJSONObject.getString("classPK")>
</#if>

<@aui["field-wrapper"] label=label>
	<a href="/documents/ddm/${className}/${classPK}/${fieldName}">${fileName}</a>
</@>