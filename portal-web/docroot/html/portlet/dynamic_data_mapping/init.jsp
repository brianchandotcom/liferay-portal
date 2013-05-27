<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/init.jsp" %>

<%@ page import="com.liferay.portal.LocaleException" %><%@
page import="com.liferay.portal.kernel.editor.EditorUtil" %><%@
page import="com.liferay.portal.kernel.portletdisplaytemplate.BasePortletDisplayTemplateHandler" %><%@
page import="com.liferay.portal.kernel.template.TemplateConstants" %><%@
page import="com.liferay.portal.kernel.template.TemplateHandler" %><%@
page import="com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil" %><%@
page import="com.liferay.portal.kernel.template.TemplateVariableDefinition" %><%@
page import="com.liferay.portal.kernel.template.TemplateVariableGroup" %><%@
page import="com.liferay.portal.template.TemplateContextHelper" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFolderConstants" %><%@
page import="com.liferay.portlet.dynamicdatalists.model.DDLRecordSet" %><%@
page import="com.liferay.portlet.dynamicdatamapping.NoSuchStructureException" %><%@
page import="com.liferay.portlet.dynamicdatamapping.RequiredStructureException" %><%@
page import="com.liferay.portlet.dynamicdatamapping.RequiredTemplateException" %><%@
page import="com.liferay.portlet.dynamicdatamapping.StructureDuplicateElementException" %><%@
page import="com.liferay.portlet.dynamicdatamapping.StructureFieldException" %><%@
page import="com.liferay.portlet.dynamicdatamapping.StructureNameException" %><%@
page import="com.liferay.portlet.dynamicdatamapping.StructureXsdException" %><%@
page import="com.liferay.portlet.dynamicdatamapping.TemplateNameException" %><%@
page import="com.liferay.portlet.dynamicdatamapping.TemplateScriptException" %><%@
page import="com.liferay.portlet.dynamicdatamapping.TemplateSmallImageNameException" %><%@
page import="com.liferay.portlet.dynamicdatamapping.TemplateSmallImageSizeException" %><%@
page import="com.liferay.portlet.dynamicdatamapping.model.DDMStructure" %><%@
page import="com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants" %><%@
page import="com.liferay.portlet.dynamicdatamapping.model.DDMTemplate" %><%@
page import="com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants" %><%@
page import="com.liferay.portlet.dynamicdatamapping.search.StructureDisplayTerms" %><%@
page import="com.liferay.portlet.dynamicdatamapping.search.StructureSearch" %><%@
page import="com.liferay.portlet.dynamicdatamapping.search.StructureSearchTerms" %><%@
page import="com.liferay.portlet.dynamicdatamapping.search.TemplateDisplayTerms" %><%@
page import="com.liferay.portlet.dynamicdatamapping.search.TemplateSearch" %><%@
page import="com.liferay.portlet.dynamicdatamapping.search.TemplateSearchTerms" %><%@
page import="com.liferay.portlet.dynamicdatamapping.service.DDMStructureServiceUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.service.DDMTemplateServiceUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.service.permission.DDMPermission" %><%@
page import="com.liferay.portlet.dynamicdatamapping.service.permission.DDMStructurePermission" %><%@
page import="com.liferay.portlet.dynamicdatamapping.service.permission.DDMTemplatePermission" %><%@
page import="com.liferay.portlet.dynamicdatamapping.storage.StorageType" %><%@
page import="com.liferay.portlet.dynamicdatamapping.util.DDMDisplay" %><%@
page import="com.liferay.portlet.dynamicdatamapping.util.DDMDisplayRegistryUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.util.DDMTemplateHelperUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.util.DDMUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.util.DDMXSDUtil" %><%@
page import="com.liferay.portlet.journal.model.JournalArticle" %>

<%
PortalPreferences portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(request);

String refererPortletName = ParamUtil.getString(request, "refererPortletName", portletName);
String refererWebDAVToken = ParamUtil.getString(request, "refererWebDAVToken", portletConfig.getInitParameter("refererWebDAVToken"));
String scopeAvailableFields = ParamUtil.getString(request, "scopeAvailableFields");
String scopeStructureName = ParamUtil.getString(request, "scopeStructureName");
String scopeTemplateMode = ParamUtil.getString(request, "scopeTemplateMode");
String scopeTemplateType = ParamUtil.getString(request, "scopeTemplateType");
String scopeTitle = ParamUtil.getString(request, "scopeTitle");
boolean showGlobalScope = ParamUtil.getBoolean(request, "showGlobalScope");
boolean showManageTemplates = ParamUtil.getBoolean(request, "showManageTemplates", true);
boolean showToolbar = ParamUtil.getBoolean(request, "showToolbar", true);

DDMDisplay ddmDisplay = DDMDisplayRegistryUtil.getDDMDisplay(refererPortletName);

long scopeClassNameId = PortalUtil.getClassNameId(ddmDisplay.getStructureType());

String templateTypeValue = StringPool.BLANK;

if (scopeTemplateType.equals(DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY)) {
	templateTypeValue = DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY;
}
else if (scopeTemplateType.equals(DDMTemplateConstants.TEMPLATE_TYPE_FORM)) {
	templateTypeValue = DDMTemplateConstants.TEMPLATE_TYPE_FORM;
}

String storageType = ddmDisplay.getStorageType();

String storageTypeValue = StringPool.BLANK;

if (storageType.equals("expando")) {
	storageTypeValue = StorageType.EXPANDO.getValue();
}
else if (storageType.equals("xml")) {
	storageTypeValue = StorageType.XML.getValue();
}

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<%@ include file="/html/portlet/dynamic_data_mapping/init-ext.jsp" %>