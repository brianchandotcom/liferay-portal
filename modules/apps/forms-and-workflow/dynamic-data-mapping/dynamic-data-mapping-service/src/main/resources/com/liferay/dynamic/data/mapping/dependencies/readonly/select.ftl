<#include "../init.ftl">

<#if !(fields?? && fields.get(fieldName)??)>
	<#assign fieldValue = "" />
</#if>

<div class="field-wrapper-content lfr-forms-field-wrapper">
	<#if !disabled>
		<@liferay_aui.input
			name=namespacedFieldName
			type="hidden"
			value=fieldValue
		/>
	</#if>

	<#if hasFieldValue || showEmptyFieldLabel>
		<label>
			<@liferay_ui.message key=escape(label) />
		</label>
	</#if>

	${escape(fieldValue)}
</div>