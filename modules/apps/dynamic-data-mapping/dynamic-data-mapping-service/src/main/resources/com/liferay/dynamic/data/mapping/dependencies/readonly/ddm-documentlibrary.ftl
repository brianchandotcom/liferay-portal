<#include "../init.ftl">

<div class="field-wrapper-content lfr-forms-field-wrapper">
	<#if hasFieldValue || showEmptyFieldLabel>
		<label>
			<@liferay_ui.message key=escape(label) />
		</label>
	</#if>

	<#if hasFieldValue>
		<#if !disabled>
			<@liferay_aui.input
				name=namespacedFieldName
				type="hidden"
				value=fieldRawValue
			/>
		</#if>

		<#assign
			fileJSONObject = getFileJSONObject(fieldRawValue)

			fileEntry = getFileEntry(fileJSONObject)
		/>

		<#if validator.isNotNull(fileEntry)>
			<a href="${getFileEntryURL(fileEntry)}">${escape(fileEntry.getTitle())}</a>
		</#if>
	</#if>

	${fieldStructure.children}
</div>