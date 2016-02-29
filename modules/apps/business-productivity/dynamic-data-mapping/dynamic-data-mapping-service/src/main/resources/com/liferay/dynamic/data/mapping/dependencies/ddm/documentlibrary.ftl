<#include "../init.ftl">

<#if !(fields?? && fields.get(fieldName)??) && (fieldRawValue == "")>
	<#assign fieldRawValue = predefinedValue>
</#if>

<#assign fieldRawValue = paramUtil.getString(request, "${namespacedFieldName}", fieldRawValue)>

<#assign fileEntryTitle = "">

<#if fieldRawValue != "">
	<#assign fileJSONObject = getFileJSONObject(fieldRawValue)>

	<#assign fileEntry = getFileEntry(fileJSONObject)>

	<#if fileEntry != "">
		<#assign fileEntryTitle = fileEntry.getTitle()>
	</#if>
</#if>

<@liferay_aui["field-wrapper"] data=data required=required>
	<div class="form-group">
		<div class="hide" id="${portletNamespace}${namespacedFieldName}UploadContainer"></div>

		<@liferay_aui.input
			helpMessage=escape(fieldStructure.tip)
			inlineField=true label=escape(label)
			name="${namespacedFieldName}Title"
			readonly="readonly" type="text"
			value=(fileEntryTitle?has_content)?string(fileEntryTitle, '')
		/>

		<@liferay_aui.input name=namespacedFieldName type="hidden" value=fieldRawValue>
			<#if required>
				<@liferay_aui.validator name="required" />
			</#if>
		</@>

		<@liferay_aui["button-row"]>
			<@liferay_aui.button
				cssClass="select-button"
				id="${namespacedFieldName}SelectButton"
				value="choose-from-document-library"
			/>

			<@liferay_aui.button
				cssClass="clear-button ${(fieldRawValue?has_content)?string('', 'hide')}"
				id="${namespacedFieldName}ClearButton"
				value="clear"
			/>
		</@>
	</div>

	${fieldStructure.children}
</@>