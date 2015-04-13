<#include "../init.ftl">

<#assign fileEntryTitle = "">
<#assign fileEntryURL = "">

<#if (fields??) && (fieldValue != "")>
	<#assign fileJSONObject = getFileJSONObject(fieldRawValue)>

	<#assign fileEntry = getFileEntry(fileJSONObject)>

	<#if (fileEntry != "")>
		<#assign fileEntryTitle = fileEntry.getTitle()>
		<#assign fileEntryURL = getFileEntryURL(fileEntry)>
	</#if>
</#if>

<div class="field-wrapper-content lfr-forms-field-wrapper">
	<label>
		<@liferay_ui.message key=escape(label) />
	</label>

	<a href="${fileEntryURL}">${escape(fileEntryTitle)}</a>

	${fieldStructure.children}
</div>

<#if !disabled>
	<@aui.input name=namespacedFieldName type="hidden" value=fieldRawValue />
</#if>
