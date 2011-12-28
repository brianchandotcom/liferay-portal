<#include "../init.ftl">

<#assign fileName = "">
<#assign recordId = "">

<#if (fields??) && (fieldValue != "")>
	<#assign fileJSONObject = getFileJSONObject(fieldRawValue)>

	<#assign fileName = fileJSONObject.getString("name")>
	<#assign recordId = fileJSONObject.getString("recordId")>
</#if>

<@aui["field-wrapper"] label=label>
	<a href="/documents/ddl/${recordId}/${fieldName}">${fileName}</a>
</@>