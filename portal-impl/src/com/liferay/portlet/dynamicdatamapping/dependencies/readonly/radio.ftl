<#include "../init.ftl">

<div class="lfr-forms-field-wrapper aui-field-wrapper-content">
	<@aui.input name=namespacedFieldName type="hidden" value=fieldValue />

	<label class="aui-field-label">
		<@liferay_ui.message key=label />
	</label>

	${fieldValue}
</div>