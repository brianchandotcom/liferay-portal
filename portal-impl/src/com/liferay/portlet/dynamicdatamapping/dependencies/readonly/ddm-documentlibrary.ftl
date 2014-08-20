<#include "../init.ftl">

<div class="field-wrapper-content lfr-forms-field-wrapper">
	<label>
		<@liferay_ui.message key=escape(label) />
	</label>

	<#if (fields??) && (fieldValue != "")>
		<#if !disabled>
			<@aui.input name=namespacedFieldName type="hidden" value=fieldRawValue />
		</#if>

		<#assign fileJSONObject = getFileJSONObject(fieldRawValue)>

		<#assign fileEntry = getFileEntry(fileJSONObject)>

		<#if (fileEntry != "")>
			<a href="${getFileEntryURL(fileEntry)}">${escape(fileEntry.getTitle())}</a>
		</#if>
	</#if>

	${fieldStructure.children}
</div>