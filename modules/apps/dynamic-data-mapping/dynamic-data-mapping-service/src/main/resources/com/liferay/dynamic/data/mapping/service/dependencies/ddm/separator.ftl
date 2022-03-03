<#include "../init.ftl">

<#assign style = fieldStructure.style!"" />

<@liferay_aui["field-wrapper"]
	cssClass="form-builder-field"
	data=data
	helpMessage=escape(fieldStructure.tip)
	label=escape(label)
>
	<div class="form-group">
		<hr class="separator" style="${escapeAttribute(style)}" />
	</div>

	${fieldStructure.children}
</@>