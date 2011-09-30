<#include "../init.ftl">

<#assign fileEntryTitle = "">
<#assign fileEntryURL = "">

<#if (fields??) && (fieldValue != "")>
	<#assign fileEntryJSONObject = getFileEntryJSONObject(fieldRawValue)>

	<#assign fileEntry = getFileEntry(fileEntryJSONObject)>

	<#assign fileEntryTitle = fileEntry.getTitle()>
	<#assign fileEntryURL = getFileEntryURL(fileEntry)>
</#if>

<@aui["field-wrapper"] label=label>
	<a href="${fileEntryURL}">${fileEntryTitle}</a>
</@>