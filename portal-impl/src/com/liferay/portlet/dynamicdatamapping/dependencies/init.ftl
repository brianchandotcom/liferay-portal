<#-- Tag libraries -->

<#assign aui = PortalJspTagLibs["/WEB-INF/tld/aui.tld"] />
<#assign fmt = PortalJspTagLibs["/WEB-INF/tld/fmt.tld"] />
<#assign liferay_portlet = PortalJspTagLibs["/WEB-INF/tld/liferay-portlet.tld"] />
<#assign liferay_ui = PortalJspTagLibs["/WEB-INF/tld/liferay-ui.tld"] />

<#-- CSS class -->

<#assign cssClass = "">

<#if fieldStructure.width??>
	<#if fieldStructure.width == "large">
		<#assign cssClass = "input-large">
	<#elseif fieldStructure.width == "medium">
		<#assign cssClass = "input-medium">
	<#elseif fieldStructure.width == "small">
		<#assign cssClass = "input-small">
	</#if>
</#if>

<#-- Repeatable -->

<#assign repeatable = false>

<#if fieldStructure.repeatable?? && (fieldStructure.repeatable == "true") && (!ignoreRepeatable?? || !ignoreRepeatable)>
	<#assign repeatable = true>
</#if>

<#-- Field name -->

<#assign fieldNamespace = "_INSTANCE_" + fieldStructure.fieldNamespace>

<#assign fieldName = fieldStructure.name>

<#assign parentName = parentFieldStructure.name!"">
<#assign parentType = parentFieldStructure.type!"">

<#assign isChildField = parentName?? && (parentName != "") && ((parentType == "radio") || (parentType == "select"))>

<#if isChildField>
	<#assign fieldName = parentName>
</#if>

<#assign namespace = namespace!"">

<#assign namespacedFieldName = "${namespace}${fieldName}${fieldNamespace}">

<#assign namespacedParentName = "${namespace}${parentName}">

<#-- Data -->

<#assign data = {
	"fieldName": fieldStructure.name,
	"fieldNamespace": fieldNamespace,
	"repeatable": repeatable?string
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

	<#assign valueIndex = getterUtil.getInteger(fieldStructure.valueIndex)>

	<#assign fieldValue = field.getRenderedValue(requestedLocale, valueIndex)>
	<#assign fieldRawValue = field.getValue(requestedLocale, valueIndex)!>
</#if>

<#-- Disabled -->

<#assign disabled = false>

<#if fieldStructure.disabled?? && (fieldStructure.disabled == "true")>
	<#assign disabled = true>
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

<#function getFileJSONObject fieldValue>
	<#return jsonFactoryUtil.createJSONObject(fieldValue)>
</#function>