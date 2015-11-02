<#include "../init.ftl">

<div class="lfr-ddm-field-group">
	<@aui.input
		cssClass=cssClass
		helpMessage=escape(fieldStructure.tip)
		label=escape(label)
		name=namespacedFieldName
		type="checkbox"
		value=fieldValue
	/>

	${fieldStructure.children}
</div>