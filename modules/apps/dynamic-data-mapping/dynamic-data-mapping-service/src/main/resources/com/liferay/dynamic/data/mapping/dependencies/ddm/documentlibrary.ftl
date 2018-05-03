<#include "../init.ftl">

<#if !(fields?? && fields.get(fieldName)??) && validator.isNull(fieldRawValue)>
	<#assign fieldRawValue = predefinedValue />
</#if>

<#assign
	fieldRawValue = paramUtil.getString(request, "${namespacedFieldName}", fieldRawValue)

	fileEntryTitle = ""
/>

<#if validator.isNotNull(fieldRawValue)>
	<#assign
		fileJSONObject = getFileJSONObject(fieldRawValue)

		fileEntry = getFileEntry(fileJSONObject)
	/>

	<#if validator.isNotNull(fileEntry)>
		<#assign fileEntryTitle = fileEntry.getTitle() />
	</#if>
</#if>

<#assign data = data + {
	"itemSelectorAuthToken": itemSelectorAuthToken
}>

<@liferay_aui["field-wrapper"]
	cssClass="form-builder-field"
	data=data
	required=required
>
	<div class="form-group">
		<div class="hide" id="${portletNamespace}${namespacedFieldName}UploadContainer"></div>

		<@liferay_aui.input
			helpMessage=escape(fieldStructure.tip)
			inlineField=true
			label=escape(label)
			name="${namespacedFieldName}Title"
			readonly="readonly"
			type="text"
			value=(fileEntryTitle?has_content)?string(fileEntryTitle, '')
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
				cssClass="clear-button ${(fieldRawValue?has_content)?string('', 'hide')}"
				id="${namespacedFieldName}ClearButton"
				value="clear"
			/>
		</div>
	</div>

	${fieldStructure.children}
</@>