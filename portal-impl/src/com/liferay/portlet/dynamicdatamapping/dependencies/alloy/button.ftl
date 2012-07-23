<#include "../init.ftl">

<span class="lfr-forms-field-wrapper lfr-forms-field-wrapper-button">
	<@aui.input cssClass=cssClass helpMessage=escape(fieldStructure.tip) label=escape(label) name=namespacedFieldName type="button" value=fieldStructure.predefinedValue />

	${fieldStructure.children}
</span>