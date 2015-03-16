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

<%@ include file="/html/taglib/init.jsp" %>

<%
boolean showListed = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:journal-content-search:showListed"));
String targetPortletId = (String)request.getAttribute("liferay-ui:journal-content-search:targetPortletId");

PortletPreferences defaultPreferences = PortletPreferencesFactoryUtil.fromDefaultXML(PortletConstants.DEFAULT_PREFERENCES);

defaultPreferences.setValue("showListed", String.valueOf(showListed));
defaultPreferences.setValue("targetPortletId", targetPortletId);
%>

<liferay-portlet:runtime defaultPreferences="<%= defaultPreferences.toString() %>" portletName="com_liferay_journal_content_search_web_portlet_JournalContentSearchPortlet" />