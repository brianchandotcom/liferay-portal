<#include "../init.ftl">

<@liferay_aui["field-wrapper"]
	cssClass="form-builder-field"
	data=data
>
	<div class="clearfix form-group">
		<@liferay_aui.input
			cssClass=cssClass
			helpMessage=escape(fieldStructure.tip)
			label=escape(label)
			name=namespacedFieldName
			required=required
			type="checkbox"
			value=fieldValue
		/>
	</div>

	${fieldStructure.children}
</@>