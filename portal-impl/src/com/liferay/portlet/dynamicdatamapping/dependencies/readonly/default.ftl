<#include "../init.ftl">

<#if fieldValue?? && fieldValue != "">
	<div class="lfr-forms-field-wrapper field-wrapper-content">
		<@aui.input name=namespacedFieldName type="hidden" value=fieldValue />

		<label>
			<@liferay_ui.message key=escape(label) />
		</label>

		${escape(fieldValue)}

		${fieldStructure.children}
	</div>
</#if>