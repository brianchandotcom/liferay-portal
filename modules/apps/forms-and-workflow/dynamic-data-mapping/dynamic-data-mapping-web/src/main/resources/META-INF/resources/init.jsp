<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.dynamic.data.mapping.constants.DDMPortletKeys" %><%@
page import="com.liferay.dynamic.data.mapping.constants.DDMWebKeys" %><%@
page import="com.liferay.dynamic.data.mapping.exception.NoSuchStructureException" %><%@
page import="com.liferay.dynamic.data.mapping.exception.RequiredStructureException" %><%@
page import="com.liferay.dynamic.data.mapping.exception.RequiredTemplateException" %><%@
page import="com.liferay.dynamic.data.mapping.exception.StructureDefinitionException" %><%@
page import="com.liferay.dynamic.data.mapping.exception.StructureDuplicateElementException" %><%@
page import="com.liferay.dynamic.data.mapping.exception.StructureFieldException" %><%@
page import="com.liferay.dynamic.data.mapping.exception.StructureNameException" %><%@
page import="com.liferay.dynamic.data.mapping.exception.TemplateNameException" %><%@
page import="com.liferay.dynamic.data.mapping.exception.TemplateScriptException" %><%@
page import="com.liferay.dynamic.data.mapping.exception.TemplateSmallImageNameException" %><%@
page import="com.liferay.dynamic.data.mapping.exception.TemplateSmallImageSizeException" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMForm" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMStructure" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMStructureConstants" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMStructureVersion" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMTemplate" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMTemplateConstants" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMTemplateVersion" %><%@
page import="com.liferay.dynamic.data.mapping.search.StructureDisplayTerms" %><%@
page import="com.liferay.dynamic.data.mapping.search.StructureSearch" %><%@
page import="com.liferay.dynamic.data.mapping.search.StructureSearchTerms" %><%@
page import="com.liferay.dynamic.data.mapping.search.TemplateDisplayTerms" %><%@
page import="com.liferay.dynamic.data.mapping.search.TemplateSearch" %><%@
page import="com.liferay.dynamic.data.mapping.search.TemplateSearchTerms" %><%@
page import="com.liferay.dynamic.data.mapping.service.DDMStorageLinkLocalServiceUtil" %><%@
page import="com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil" %><%@
page import="com.liferay.dynamic.data.mapping.service.DDMStructureServiceUtil" %><%@
page import="com.liferay.dynamic.data.mapping.service.DDMStructureVersionServiceUtil" %><%@
page import="com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil" %><%@
page import="com.liferay.dynamic.data.mapping.service.DDMTemplateServiceUtil" %><%@
page import="com.liferay.dynamic.data.mapping.service.DDMTemplateVersionServiceUtil" %><%@
page import="com.liferay.dynamic.data.mapping.service.permission.DDMStructurePermission" %><%@
page import="com.liferay.dynamic.data.mapping.service.permission.DDMTemplatePermission" %><%@
page import="com.liferay.dynamic.data.mapping.storage.StorageType" %><%@
page import="com.liferay.dynamic.data.mapping.util.DDMDisplay" %><%@
page import="com.liferay.dynamic.data.mapping.util.DDMNavigationHelper" %><%@
page import="com.liferay.dynamic.data.mapping.util.DDMUtil" %><%@
page import="com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidationException" %><%@
page import="com.liferay.dynamic.data.mapping.validator.DDMFormValidationException" %><%@
page import="com.liferay.dynamic.data.mapping.web.configuration.DDMWebConfigurationKeys" %><%@
page import="com.liferay.dynamic.data.mapping.web.configuration.DDMWebConfigurationUtil" %><%@
page import="com.liferay.dynamic.data.mapping.web.display.context.DDMDisplayContext" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.configuration.Filter" %><%@
page import="com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.editor.EditorModeUtil" %><%@
page import="com.liferay.portal.kernel.exception.LocaleException" %><%@
page import="com.liferay.portal.kernel.exception.PortletPreferencesException" %><%@
page import="com.liferay.portal.kernel.json.JSONArray" %><%@
page import="com.liferay.portal.kernel.json.JSONFactoryUtil" %><%@
page import="com.liferay.portal.kernel.json.JSONObject" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.log.Log" %><%@
page import="com.liferay.portal.kernel.log.LogFactoryUtil" %><%@
page import="com.liferay.portal.kernel.model.Group" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.portlet.PortletURLFactoryUtil" %><%@
page import="com.liferay.portal.kernel.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.kernel.security.permission.ResourceActionsUtil" %><%@
page import="com.liferay.portal.kernel.service.GroupLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.permission.PortletPermissionUtil" %><%@
page import="com.liferay.portal.kernel.template.TemplateConstants" %><%@
page import="com.liferay.portal.kernel.template.TemplateHandler" %><%@
page import="com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil" %><%@
page import="com.liferay.portal.kernel.template.TemplateVariableDefinition" %><%@
page import="com.liferay.portal.kernel.template.TemplateVariableGroup" %><%@
page import="com.liferay.portal.kernel.template.comparator.TemplateHandlerComparator" %><%@
page import="com.liferay.portal.kernel.util.CharPool" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.LocaleUtil" %><%@
page import="com.liferay.portal.kernel.util.OrderByComparator" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.ResourceBundleUtil" %><%@
page import="com.liferay.portal.kernel.util.StringBundler" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.TextFormatter" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %><%@
page import="com.liferay.portal.template.TemplateContextHelper" %><%@
page import="com.liferay.portlet.display.template.PortletDisplayTemplate" %><%@
page import="com.liferay.portlet.display.template.PortletDisplayTemplateUtil" %><%@
page import="com.liferay.taglib.search.ResultRow" %><%@
page import="com.liferay.util.ContentUtil" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.Iterator" %><%@
page import="java.util.List" %><%@
page import="java.util.Locale" %><%@
page import="java.util.Map" %><%@
page import="java.util.ResourceBundle" %><%@
page import="java.util.Set" %><%@
page import="java.util.StringTokenizer" %>

<%@ page import="javax.portlet.PortletRequest" %><%@
page import="javax.portlet.PortletURL" %><%@
page import="javax.portlet.WindowState" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String refererPortletName = ParamUtil.getString(request, "refererPortletName", portletName);
String refererWebDAVToken = ParamUtil.getString(request, "refererWebDAVToken", portletConfig.getInitParameter("refererWebDAVToken"));
String scopeTitle = ParamUtil.getString(request, "scopeTitle");
boolean showAncestorScopes = ParamUtil.getBoolean(request, "showAncestorScopes");
boolean showManageTemplates = ParamUtil.getBoolean(request, "showManageTemplates", true);

DDMDisplayContext ddmDisplayContext = (DDMDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

DDMDisplay ddmDisplay = null;

boolean changeableDefaultLanguage = false;
String scopeAvailableFields = StringPool.BLANK;
long scopeClassNameId = 0;
String scopeStorageType = StringPool.BLANK;
String scopeTemplateType = StringPool.BLANK;

if (ddmDisplayContext != null) {
	ddmDisplay = ddmDisplayContext.getDDMDisplay(refererPortletName);

	changeableDefaultLanguage = ddmDisplayContext.changeableDefaultLanguage();
	scopeAvailableFields = ddmDisplay.getAvailableFields();
	scopeClassNameId = PortalUtil.getClassNameId(ddmDisplay.getStructureType());
	scopeStorageType = ddmDisplay.getStorageType();
	scopeTemplateType = ddmDisplay.getTemplateType();
}

String storageTypeValue = StringPool.BLANK;

if (scopeStorageType.equals("json")) {
	storageTypeValue = StorageType.JSON.getValue();
}

String templateTypeValue = StringPool.BLANK;

if (scopeTemplateType.equals(DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY)) {
	templateTypeValue = DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY;
}
else if (scopeTemplateType.equals(DDMTemplateConstants.TEMPLATE_TYPE_FORM)) {
	templateTypeValue = DDMTemplateConstants.TEMPLATE_TYPE_FORM;
}
%>

<%@ include file="/init-ext.jsp" %>

<%!
private void _addFormTemplateFieldAttributes(DDMStructure structure, JSONArray jsonArray) throws Exception {
	for (int i = 0; i < jsonArray.length(); i++) {
		JSONObject jsonObject = jsonArray.getJSONObject(i);

		String fieldName = jsonObject.getString("name");

		try {
			jsonObject.put("readOnlyAttributes", _getFieldReadOnlyAttributes(structure, fieldName));
			jsonObject.put("unique", true);
		}
		catch (StructureFieldException sfe) {
		}
	}
}

private JSONArray _getFieldReadOnlyAttributes(DDMStructure structure, String fieldName) throws Exception {
	JSONArray readOnlyAttributesJSONArray = JSONFactoryUtil.createJSONArray();

	readOnlyAttributesJSONArray.put("indexType");
	readOnlyAttributesJSONArray.put("name");
	readOnlyAttributesJSONArray.put("options");
	readOnlyAttributesJSONArray.put("repeatable");

	boolean required = structure.getFieldRequired(fieldName);

	if (required) {
		readOnlyAttributesJSONArray.put("required");
	}

	return readOnlyAttributesJSONArray;
}

private JSONArray _getFormTemplateFieldsJSONArray(DDMStructure structure, String script) throws Exception {
	JSONArray jsonArray = DDMUtil.getDDMFormFieldsJSONArray(structure, script);

	_addFormTemplateFieldAttributes(structure, jsonArray);

	return jsonArray;
}
%>