<#include "../init.ftl">

<#if !(fields?? && fields.get(fieldName)??) && (fieldRawValue == "")>
	<#assign fieldRawValue = predefinedValue>
</#if>

<#assign fieldRawValue = paramUtil.getString(request, "${namespacedFieldName}", fieldRawValue)>

<#assign alt = "">
<#assign imageData = "">
<#assign name = languageUtil.get(locale, "drag-file-here")>

<#if fieldRawValue?has_content>
	<#assign fileJSONObject = getFileJSONObject(fieldRawValue)>

	<#assign alt = fileJSONObject.getString("alt")>
	<#assign imageData = fileJSONObject.getString("data")>
	<#assign name = fileJSONObject.getString("name")>
</#if>

<@aui["field-wrapper"] data=data>
	<div class="form-group">
		<div class="hide" id="${portletNamespace}${namespacedFieldName}UploadContainer"></div>

		<div class="hide" id="${portletNamespace}${namespacedFieldName}PreviewContainer">
			<a href="${themeDisplay.getPathContext()}${imageData}">
				<img src="${themeDisplay.getPathContext()}${imageData}" />
			</a>
		</div>

		<@aui.input
			helpMessage=escape(fieldStructure.tip)
			inlineField=true label=escape(label)
			name="${namespacedFieldName}Title"
			readonly="readonly" type="text"
			value=(name?has_content)?string(name, languageUtil.get(locale, "drag-file-here"))
		/>

		<@aui.input name=namespacedFieldName type="hidden" value=fieldRawValue>
			<#if required>
				<@aui.validator name="required" />
			</#if>
		</@>

		<@aui["button-row"]>
			<@aui.button
				cssClass="select-button"
				id="${namespacedFieldName}SelectButton"
				value="select"
			/>

			<@aui.button
				cssClass="clear-button ${(imageData?has_content)?string('', 'hide')}"
				id="${namespacedFieldName}ClearButton"
				value="clear"
			/>

			<@aui.button
				cssClass="preview-button ${(imageData?has_content)?string('', 'hide')}"
				id="${namespacedFieldName}PreviewButton"
				value="preview"
			/>
		</@>

		<@aui.input
			label="image-description"
			name="${namespacedFieldName}Alt"
			type="text"
			value="${alt}"
		/>
	</div>

	${fieldStructure.children}
</@>