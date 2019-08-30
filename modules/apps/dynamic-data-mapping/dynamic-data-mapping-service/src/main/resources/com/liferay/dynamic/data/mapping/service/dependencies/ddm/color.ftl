<#include "../init.ftl">

<#if !(fields?? && fields.get(fieldName)??) && validator.isNull(fieldRawValue)>
	<#assign fieldRawValue = predefinedValue />
</#if>

<@liferay_aui["field-wrapper"]
	cssClass="form-builder-field"
	data=data
	label=escape(label)
	required=required
>
	<div class="form-group">
		<@liferay_aui.input
			cssClass="selector-input"
			helpMessage=escape(fieldStructure.tip)
			inlineField=true
			label=""
			name="${namespacedFieldName}Color"
			readonly="readonly"
			title=escape(label)
			type="text"
			value=escape(fieldStructure.tip)
		/>

		<@liferay_aui.input
			cssClass="color-value"
			name=namespacedFieldName
			required=required
			type="hidden"
			value=fieldRawValue
		/>
	</div>
</@>