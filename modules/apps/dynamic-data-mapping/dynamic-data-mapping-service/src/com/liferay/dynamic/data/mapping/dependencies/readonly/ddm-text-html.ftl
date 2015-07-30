<#include "../init.ftl">

<div class="field-wrapper-content lfr-forms-field-wrapper">
	<#if hasFieldValue || showEmptyFieldLabel>
		<label>
			<@liferay_ui.message key=escape(label) />
		</label>
	</#if>

	<#if hasFieldValue>
		<#if !disabled>
			<@aui.input name=namespacedFieldName type="hidden" value=fieldValue />
		</#if>

		${fieldValue}
	</#if>

	${fieldStructure.children}
</div>