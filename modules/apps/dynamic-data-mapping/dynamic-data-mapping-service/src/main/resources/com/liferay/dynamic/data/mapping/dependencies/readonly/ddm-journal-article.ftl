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
			jsonObject = getFileJSONObject(fieldRawValue)

			journalArticle = fetchLatestArticle(jsonObject)
		/>

		<#if validator.isNotNull(journalArticle)>
			${escape(journalArticle.getTitle(requestedLocale))}
		</#if>
	</#if>

	${fieldStructure.children}
</div>