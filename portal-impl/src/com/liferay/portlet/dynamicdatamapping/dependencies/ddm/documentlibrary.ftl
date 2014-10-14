<#include "../init.ftl">

<#if !(fields?? && fields.get(fieldName)??) && (fieldRawValue == "")>
	<#assign fieldRawValue = predefinedValue>
</#if>

<#assign fieldRawValue = paramUtil.getString(request, "${namespacedFieldName}", fieldRawValue)>

<#assign fileEntryTitle = "">

<#if (fieldRawValue != "")>
	<#assign fileJSONObject = getFileJSONObject(fieldRawValue)>

	<#assign fileEntry = getFileEntry(fileJSONObject)>

	<#if (fileEntry != "")>
		<#assign fileEntryTitle = fileEntry.getTitle()>
	</#if>
</#if>

<@aui["field-wrapper"] data=data>
	<div class="hide" id="${portletNamespace}${namespacedFieldName}UploadContainer"></div>

	<@aui.input helpMessage=escape(fieldStructure.tip) inlineField=true label=escape(label) name="${namespacedFieldName}Title" readonly="readonly" type="text" value=(fileEntryTitle?has_content)?string(fileEntryTitle, languageUtil.get(locale, "drag-file-here"))>
		<#if required>
			<@aui.validator name="required" />
		</#if>
	</@aui.input>

	<div class="file-entry-upload-progress hide" id="${portletNamespace}${namespacedFieldName}Progress">
		<div aria-valuemax="100" aria-valuemin="0" aria-valuenow="0" class="progress-bar" role="progressbar"></div>
	</div>

	<@aui.input name=namespacedFieldName type="hidden" value=fieldRawValue />

	<@aui["button-row"]>
		<@aui.button cssClass="upload-button" id="${namespacedFieldName}UploadButton" value="upload" />

		<@aui.button cssClass="select-button" id="${namespacedFieldName}SelectButton" value="choose-from-document-library" />

		<@aui.button cssClass="clear-button ${(fieldRawValue?has_content)?string('', 'hide')}" id="${namespacedFieldName}ClearButton" value="clear" />
	</@>

	${fieldStructure.children}
</@>