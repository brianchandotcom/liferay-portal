<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ page import="com.liferay.portlet.dynamicdatalists.NoSuchRecordSetException" %>
<%@ page import="com.liferay.portlet.dynamicdatalists.model.DDLRecordSet" %>
<%@ page import="com.liferay.portlet.dynamicdatalists.model.DDLRecordSetConstants" %>
<%@ page import="com.liferay.portlet.dynamicdatalists.search.RecordSetDisplayTerms" %>
<%@ page import="com.liferay.portlet.dynamicdatalists.search.RecordSetSearch" %>
<%@ page import="com.liferay.portlet.dynamicdatalists.search.RecordSetSearchTerms" %>
<%@ page import="com.liferay.portlet.dynamicdatalists.service.DDLRecordSetLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.dynamicdatalists.service.permission.DDLPermission" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.model.DDMTemplate" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.service.permission.DDMPermission" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.service.permission.DDMTemplatePermission" %>

<%
PortletPreferences preferences = renderRequest.getPreferences();

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}

long recordSetId = GetterUtil.getLong(preferences.getValue("recordSetId", StringPool.BLANK));

long detailDDMTemplateId = GetterUtil.getLong(preferences.getValue("detailDDMTemplateId", StringPool.BLANK));
long listDDMTemplateId = GetterUtil.getLong(preferences.getValue("listDDMTemplateId", StringPool.BLANK));

boolean editable = GetterUtil.getBoolean(preferences.getValue("editable", Boolean.TRUE.toString()));
boolean spreadsheet = GetterUtil.getBoolean(preferences.getValue("spreadsheet", Boolean.FALSE.toString()));

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>