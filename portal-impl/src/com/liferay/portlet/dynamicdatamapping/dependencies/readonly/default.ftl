<#include "../init.ftl">

<#if fieldValue?? && fieldValue != "">
	<div class="lfr-forms-field-wrapper aui-field-wrapper-content">
		<@aui.input name=namespacedFieldName type="hidden" value=fieldValue />

		<label class="aui-field-label">
			<@liferay_ui.message key=escape(label) />
		</label>

		${escape(fieldValue)}

		${fieldStructure.children}
	</div>
</#if>