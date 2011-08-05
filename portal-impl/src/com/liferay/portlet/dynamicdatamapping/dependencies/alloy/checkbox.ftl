<#include "../init.ftl">

<div class="aui-field-wrapper-content lfr-forms-field-wrapper">
	<@aui.input cssClass=cssClass helpMessage=field.tip label=label name=namespacedFieldName type="checkbox" value=fieldValue />

	${field.children}
</div>