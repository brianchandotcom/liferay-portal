<#include "../init.ftl">

<@liferay_aui["field-wrapper"]
	cssClass="form-builder-field"
	data=data
	helpMessage=escape(fieldStructure.tip)
	label=escape(label)
>
	<div class="clearfix form-group">
		${fieldStructure.children}
	</div>
</@>