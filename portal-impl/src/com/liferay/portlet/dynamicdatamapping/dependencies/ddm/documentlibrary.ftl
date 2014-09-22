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
	<@aui.input helpMessage=escape(fieldStructure.tip) inlineField=true label=escape(label) name="${namespacedFieldName}Title" readonly="readonly" type="text" value=fileEntryTitle>
		<#if required>
			<@aui.validator name="required" />
		</#if>
	</@aui.input>

	<@aui["button-row"]>
		<@aui.button cssClass="select-button" value="select" />

		<@aui.button cssClass="clear-button" value="clear" />
	</@>

	<@aui.input name=namespacedFieldName type="hidden" value=fieldRawValue />

	${fieldStructure.children}
</@>