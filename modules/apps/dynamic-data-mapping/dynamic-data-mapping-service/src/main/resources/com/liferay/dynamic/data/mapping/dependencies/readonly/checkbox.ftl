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
				value=fieldValue
			/>
		</#if>

		<#if stringUtil.equals(fieldValue, "true")>
			<@liferay_ui.message key=escape("yes") />
		<#else>
			<@liferay_ui.message key=escape("no") />
		</#if>
	</#if>

	${fieldStructure.children}
</div>