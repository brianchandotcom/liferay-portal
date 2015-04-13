<#include "../init.ftl">

<#if !(fields?? && fields.get(fieldName)??)>
	<#assign fieldValue = "">
</#if>

<div class="field-wrapper-content lfr-forms-field-wrapper">
	<#if !disabled>
		<@aui.input name=namespacedFieldName type="hidden" value=fieldValue />
	</#if>

	<label>
		<@liferay_ui.message key=escape(label) />
	</label>

	${escape(fieldValue)}
</div>