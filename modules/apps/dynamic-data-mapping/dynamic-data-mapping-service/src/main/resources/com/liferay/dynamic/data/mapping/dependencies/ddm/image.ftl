<#include "../init.ftl">

<#if !(fields?? && fields.get(fieldName)??) && validator.isNull(fieldRawValue)>
	<#assign fieldRawValue = predefinedValue />
</#if>

<#assign
	fieldRawValue = paramUtil.getString(request, "${namespacedFieldName}", fieldRawValue)

	alt = ""
	imageData = ""
	name = languageUtil.get(locale, "drag-file-here")
/>

<#if fieldRawValue?has_content>
	<#assign
		fileJSONObject = getFileJSONObject(fieldRawValue)

		alt = fileJSONObject.getString("alt")
		imageData = fileJSONObject.getString("data")
		name = fileJSONObject.getString("name")
	/>
</#if>

<#assign data = data + {
	"itemSelectorAuthToken": itemSelectorAuthToken
}>

<@liferay_aui["field-wrapper"]
	cssClass="form-builder-field"
	data=data
>
	<div class="form-group">
		<div class="hide" id="${portletNamespace}${namespacedFieldName}UploadContainer"></div>

		<div class="hide" id="${portletNamespace}${namespacedFieldName}PreviewContainer">
			<a href="${themeDisplay.getPathContext()}${imageData}">
				<img src="${themeDisplay.getPathContext()}${imageData}" />
			</a>
		</div>

		<@liferay_aui.input
			helpMessage=escape(fieldStructure.tip)
			inlineField=true
			label=escape(label)
			name="${namespacedFieldName}Title"
			readonly="readonly"
			type="text"
			value=(name?has_content)?string(name, languageUtil.get(locale, "drag-file-here"))
		/>

		<@liferay_aui.input
			name=namespacedFieldName
			type="hidden"
			value=fieldRawValue
		>
			<#if required>
				<@liferay_aui.validator name="required" />
			</#if>
		</@>

		<div class="button-holder">
			<@liferay_aui.button
				cssClass="select-button"
				id="${namespacedFieldName}SelectButton"
				value="select"
			/>

			<@liferay_aui.button
				cssClass="clear-button ${(imageData?has_content)?string('', 'hide')}"
				id="${namespacedFieldName}ClearButton"
				value="clear"
			/>

			<@liferay_aui.button
				cssClass="preview-button ${(imageData?has_content)?string('', 'hide')}"
				id="${namespacedFieldName}PreviewButton"
				value="preview"
			/>
		</div>

		<@liferay_aui.input
			label="image-description"
			name="${namespacedFieldName}Alt"
			type="text"
			value="${alt}"
		/>
	</div>

	${fieldStructure.children}
</@>