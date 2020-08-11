<#include "../init.ftl">

<#if !(fields?? && fields.get(fieldName)??) && validator.isNull(fieldRawValue)>
	<#assign fieldRawValue = predefinedValue />
</#if>

<#assign
	fieldRawValue = paramUtil.getString(request, "${namespacedFieldName}", fieldRawValue)

	message = ""
	title = ""
/>

<#if validator.isNotNull(fieldRawValue)>
	<#assign
		fileJSONObject = getFileJSONObject(fieldRawValue)

		message = fileJSONObject.getString("message")
		title = fileJSONObject.getString("title")
	/>
</#if>

<#assign data = data + {
	"itemSelectorAuthToken": itemSelectorAuthToken
}>

<@liferay_aui["field-wrapper"]
	cssClass="form-builder-field"
	data=data
	required=required
>
	<div class="form-group ${(message?has_content)?string('has-warning', '')}">
		<div class="hide" id="${portletNamespace}${namespacedFieldName}UploadContainer"></div>

		<@liferay_aui.input
			helpMessage=escape(fieldStructure.tip)
			inlineField=true
			label=escape(label)
			name="${namespacedFieldName}Title"
			readonly="readonly"
			required=required
			type="text"
			value=title
		/>

		<@liferay_aui.input
			name=namespacedFieldName
			type="hidden"
			value=fieldRawValue
		/>

		<#if validator.isNotNull(message)>
			<div class="form-feedback-item" id="${portletNamespace}${namespacedFieldName}Message">${message}</div>
		</#if>

		<div class="button-holder">
			<@liferay_aui.button
				cssClass="select-button"
				id="${namespacedFieldName}SelectButton"
				value="select"
			/>

			<@liferay_aui.button
				cssClass="clear-button ${(fieldRawValue?has_content)?string('', 'hide')}"
				id="${namespacedFieldName}ClearButton"
				value="clear"
			/>
		</div>
	</div>

	${fieldStructure.children}
</@>