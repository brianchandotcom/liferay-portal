<#-- Tag libraries -->

<#assign aui = PortalJspTagLibs["/WEB-INF/tld/liferay-aui.tld"] />
<#assign liferay_portlet = PortalJspTagLibs["/WEB-INF/tld/liferay-portlet.tld"] />
<#assign liferay_ui = PortalJspTagLibs["/WEB-INF/tld/liferay-ui.tld"] />

<#-- CSS class -->

<#assign cssClass = fieldStructure.fieldCssClass!"">

<#-- Field name -->

<#assign fieldName = fieldStructure.name>

<#assign parentName = parentFieldStructure.name!"">
<#assign parentType = parentFieldStructure.type!"">

<#assign isChildField = parentName?? && (parentName != "") && ((parentType == "radio") || (parentType == "select"))>

<#if isChildField>
	<#assign fieldName = parentName>
</#if>

<#assign namespacedFieldName = "${namespace}${fieldName}">

<#assign namespacedParentName = "${namespace}${parentName}">

<#-- Predefined value -->

<#assign predefinedValue = fieldStructure.predefinedValue!"">

<#if isChildField>
	<#assign predefinedValue = parentFieldStructure.predefinedValue!"">
</#if>

<#-- Field value -->

<#assign fieldValue = predefinedValue>
<#assign fieldRawValue = "">

<#if fields?? && fields.get(fieldName)??>
	<#assign field = fields.get(fieldName)>

	<#assign fieldValue = field.getRenderedValue(themeDisplay)>
	<#assign fieldRawValue = field.getValue()>
</#if>

<#-- Label -->

<#assign label = fieldStructure.label!"">

<#if fieldStructure.showLabel?? && (fieldStructure.showLabel == "false")>
	<#assign label = "">
</#if>

<#-- Required -->

<#assign required = false>

<#if fieldStructure.required?? && (fieldStructure.required == "true")>
	<#assign required = true>
</#if>

<#-- Util -->

<#assign jsonFactoryUtil = utilLocator.findUtil("com.liferay.portal.kernel.json.JSONFactory")>

<#function getFileJSONObject fieldValue>
	<#return jsonFactoryUtil.createJSONObject(fieldValue)>>
</#function>

<#assign dlAppServiceUtil = serviceLocator.findService("com.liferay.portlet.documentlibrary.service.DLAppService")>

<#function getFileEntry fileJSONObject>
	<#assign fileEntryUUID = fileJSONObject.getString("uuid")>

	<#return dlAppServiceUtil.getFileEntryByUuidAndGroupId(fileEntryUUID, scopeGroupId)>
</#function>

<#function getFileEntryURL fileEntry>
	<#return themeDisplay.getPathContext() + "/documents/" + scopeGroupId?c + "/" + (fileEntry.getFolderId())?c + "/" +  httpUtil.encodeURL(htmlUtil.unescape(fileEntry.getTitle()))>
</#function>