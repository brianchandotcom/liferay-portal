<#include "../init.ftl">

<div class="field-wrapper-content lfr-forms-field-wrapper">
	<#if fieldValue?? && fieldValue != "">
		<#if !disabled>
			<@aui.input name=namespacedFieldName type="hidden" value=fieldValue />
		</#if>

		<label>
			<@liferay_ui.message key=escape(label) />
		</label>

		${escape(fieldValue)}
	</#if>

	${fieldStructure.children}
</div>