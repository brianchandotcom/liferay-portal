<#include "../init.ftl">

<#if fieldValue?? && fieldValue != "">
	<div class="lfr-forms-field-wrapper field-wrapper-content">
		<#if !disabled>
			<@aui.input name=namespacedFieldName type="hidden" value=fieldValue />
		</#if>

		<label>
			<@liferay_ui.message key=escape(label) />
		</label>

		${escape(fieldValue)}

		${fieldStructure.children}
	</div>
</#if>