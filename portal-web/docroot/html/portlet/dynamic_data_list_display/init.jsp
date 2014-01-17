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

<%@ page import="com.liferay.portlet.dynamicdatalists.NoSuchRecordSetException" %><%@
page import="com.liferay.portlet.dynamicdatalists.model.DDLRecordSet" %><%@
page import="com.liferay.portlet.dynamicdatalists.model.DDLRecordSetConstants" %><%@
page import="com.liferay.portlet.dynamicdatalists.search.RecordSetSearch" %><%@
page import="com.liferay.portlet.dynamicdatalists.search.RecordSetSearchTerms" %><%@
page import="com.liferay.portlet.dynamicdatalists.service.DDLRecordSetLocalServiceUtil" %><%@
page import="com.liferay.portlet.dynamicdatalists.service.DDLRecordSetServiceUtil" %><%@
page import="com.liferay.portlet.dynamicdatalists.service.permission.DDLPermission" %><%@
page import="com.liferay.portlet.dynamicdatalists.service.permission.DDLRecordSetPermission" %><%@
page import="com.liferay.portlet.dynamicdatalists.util.DDLUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants" %><%@
page import="com.liferay.portlet.dynamicdatamapping.service.permission.DDMPermission" %><%@
page import="com.liferay.portlet.dynamicdatamapping.util.DDMDisplay" %><%@
page import="com.liferay.portlet.dynamicdatamapping.util.DDMDisplayRegistryUtil" %>

<%
String portletResource = ParamUtil.getString(request, "portletResource");

long recordSetId = GetterUtil.getLong(portletPreferences.getValue("recordSetId", StringPool.BLANK));

long displayDDMTemplateId = GetterUtil.getLong(portletPreferences.getValue("displayDDMTemplateId", StringPool.BLANK));
long formDDMTemplateId = GetterUtil.getLong(portletPreferences.getValue("formDDMTemplateId", StringPool.BLANK));

boolean editable = GetterUtil.getBoolean(portletPreferences.getValue("editable", Boolean.TRUE.toString()));
boolean spreadsheet = GetterUtil.getBoolean(portletPreferences.getValue("spreadsheet", Boolean.FALSE.toString()));

DDMDisplay ddmDisplay = DDMDisplayRegistryUtil.getDDMDisplay(PortletKeys.DYNAMIC_DATA_LISTS);
%>

<%@ include file="/html/portlet/dynamic_data_list_display/init-ext.jsp" %>