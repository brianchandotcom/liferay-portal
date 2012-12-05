<#-- Tag libraries -->

<#assign aui = PortalJspTagLibs["/WEB-INF/tld/aui.tld"] />
<#assign liferay_portlet = PortalJspTagLibs["/WEB-INF/tld/liferay-portlet.tld"] />
<#assign liferay_ui = PortalJspTagLibs["/WEB-INF/tld/liferay-ui.tld"] />

<#-- CSS class -->

<#assign cssClass = escapeAttribute(fieldStructure.fieldCssClass!"")>

<#-- Repeatable -->

<#assign repeatable = false>

<#if fieldStructure.repeatable?? && (fieldStructure.repeatable == "true")>
	<#assign repeatable = true>
</#if>

<#assign repeatableIndex = "">

<#if fieldStructure.repeatableIndex?? && (fieldStructure.repeatableIndex != "0")>
	<#assign repeatableIndex = "__" + fieldStructure.repeatableIndex>
</#if>

<#-- Field name -->

<#assign fieldName = fieldStructure.name>

<#assign parentName = parentFieldStructure.name!"">
<#assign parentType = parentFieldStructure.type!"">

<#assign isChildField = parentName?? && (parentName != "") && ((parentType == "radio") || (parentType == "select"))>

<#if isChildField>
	<#assign fieldName = parentName>
</#if>

<#assign namespace = namespace!"">

<#assign namespacedFieldName = "${namespace}${fieldName}${repeatableIndex}">

<#assign namespacedParentName = "${namespace}${parentName}">

<#-- Data -->

<#assign data = {
	"fieldName": fieldStructure.name,
	"repeatable": repeatable?string,
	"repeatableIndex": repeatableIndex
}>

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

	<#assign valueIndex = getterUtil.getInteger(repeatableIndex)>

	<#assign fieldValue = field.getRenderedValue(locale, valueIndex)>
	<#assign fieldRawValue = field.getValue(valueIndex)>
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

<#function escape value="">
	<#if value?is_string>
		<#return htmlUtil.escape(value)>
	<#else>
		<#return value>
	</#if>
</#function>

<#function escapeAttribute value="">
	<#if value?is_string>
		<#return htmlUtil.escapeAttribute(value)>
	<#else>
		<#return value>
	</#if>
</#function>

<#function escapeCSS value="">
	<#if value?is_string>
		<#return htmlUtil.escapeCSS(value)>
	<#else>
		<#return value>
	</#if>
</#function>

<#function escapeJS value="">
	<#if value?is_string>
		<#return htmlUtil.escapeJS(value)>
	<#else>
		<#return value>
	</#if>
</#function>

<#assign dlAppServiceUtil = serviceLocator.findService("com.liferay.portlet.documentlibrary.service.DLAppService")>

<#function getFileEntry fileJSONObject>
	<#assign fileEntryUUID = fileJSONObject.getString("uuid")>

	<#if (fileJSONObject.getLong("groupId") > 0)>
		<#assign fileEntryGroupId = fileJSONObject.getLong("groupId")>
	<#else>
		<#assign fileEntryGroupId = scopeGroupId>
	</#if>

	<#return dlAppServiceUtil.getFileEntryByUuidAndGroupId(fileEntryUUID, fileEntryGroupId)!"">
</#function>

<#function getFileEntryURL fileEntry>
	<#return themeDisplay.getPathContext() + "/documents/" + fileEntry.getRepositoryId()?c + "/" + fileEntry.getFolderId()?c + "/" +  httpUtil.encodeURL(htmlUtil.unescape(fileEntry.getTitle()), true) + "/" + fileEntry.getUuid()>
</#function>

<#assign jsonFactoryUtil = utilLocator.findUtil("com.liferay.portal.kernel.json.JSONFactory")>

<#function getFileJSONObject fieldValue>
	<#return jsonFactoryUtil.createJSONObject(fieldValue)>>
</#function>