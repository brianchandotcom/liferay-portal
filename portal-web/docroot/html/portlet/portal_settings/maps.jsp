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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
	PortletPreferences companyPreferences = PrefsPropsUtil.getPreferences(company.getCompanyId());
%>

<liferay-ui:error-marker key="errorSection" value="maps" />

<h3><liferay-ui:message key="maps" /></h3>

<%
String googleMapsKey = PrefsParamUtil.getString(companyPreferences, request, "googleMapsKey");
%>

<aui:input helpMessage="set-the-google-maps-key-that-will-be-used-for-this-set-of-pages" label="google-maps-key" name="settings--googleMapsKey--" size="40" type="text" value="<%= googleMapsKey %>" />