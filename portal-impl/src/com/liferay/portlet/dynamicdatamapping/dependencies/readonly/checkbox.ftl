<#include "../init.ftl">

<#if fieldValue?? && fieldValue != "">
	<div class="field-wrapper-content lfr-forms-field-wrapper">
		<#if !disabled>
			<@aui.input name=namespacedFieldName type="hidden" value=fieldValue />
		</#if>

		<label>
			<@liferay_ui.message key=escape(label) />
		</label>

		<#if fieldValue == "true">
			<@liferay_ui.message key=escape("yes") />
		<#else>
			<@liferay_ui.message key=escape("no") />
		</#if>

		${fieldStructure.children}
	</div>
</#if>