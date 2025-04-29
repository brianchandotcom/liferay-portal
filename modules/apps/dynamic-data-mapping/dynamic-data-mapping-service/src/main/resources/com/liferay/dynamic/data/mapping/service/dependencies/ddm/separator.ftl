<#include "../init.ftl">

<#assign style = fieldStructure.style!"" />

<@liferay_aui["field-wrapper"]
	cssClass="form-builder-field"
	data=data
	helpMessage=escape(fieldStructure.tip)
	label=escape(label)
>
	<div class="form-group">
		<@liferay_ui.csp>
			<hr class="separator" style="${escapeAttribute(style)}" />
		</@liferay_ui.csp>
	</div>

	${fieldStructure.children}
</@>