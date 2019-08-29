<#include "../init.ftl">

<#assign style = fieldStructure.style!"" />

<@liferay_aui["field-wrapper"]
	cssClass="form-builder-field"
	data=data
	helpMessage=escape(fieldStructure.tip)
	label=escape(label)
>
	<div class="form-group">
		<div class="separator" style="${escapeAttribute(style)}"></div>
	</div>

	${fieldStructure.children}
</@>