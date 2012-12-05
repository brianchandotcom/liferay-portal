<#include "../init.ftl">

<@aui["field-wrapper"] label=escape(label)>
	<#if (fields??) && (fieldValue != "")>
		<#assign fileJSONObject = getFileJSONObject(fieldRawValue)>

		<#assign fileName = fileJSONObject.getString("name")>
		<#assign className = fileJSONObject.getString("className")>
		<#assign classPK = fileJSONObject.getString("classPK")>

		<a href="/documents/ddm/${className}/${classPK}/${fieldName}/${valueIndex}">${fileName}</a>
	</#if>
</@>