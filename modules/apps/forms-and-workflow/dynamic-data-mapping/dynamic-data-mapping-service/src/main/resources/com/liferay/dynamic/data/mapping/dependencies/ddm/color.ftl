<#include "../init.ftl">

<#if !(fields?? && fields.get(fieldName)??) && validator.isNull(fieldRawValue)>
	<#assign fieldRawValue = predefinedValue />
</#if>

<@liferay_aui["field-wrapper"]
	cssClass="form-builder-field"
	data=data
	required=required
>
	<div class="form-group">
		<@liferay_aui.input
			cssClass="selector-input"
			helpMessage=escape(fieldStructure.tip)
			inlineField=true
			label=escape(label)
			name="${namespacedFieldName}Color"
			readonly="readonly"
			type="text"
			value=escape(fieldStructure.tip)
		/>

		<@liferay_aui.input
			cssClass="color-value"
			name=namespacedFieldName
			type="hidden"
			value=fieldRawValue
		>
			<#if required>
				<@liferay_aui.validator name="required" />
			</#if>
		</@>
	</div>
</@>