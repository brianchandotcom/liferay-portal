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

<%@ include file="/html/portlet/admin/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Map.Entry entry = (Map.Entry)row.getObject();

String property = (String)entry.getKey();
String value = (String)entry.getValue();

PortletPreferences serverPreferences = (PortletPreferences)request.getAttribute("server.jspf-serverPreferences");
Map<String, String[]> serverPreferencesMap = (Map<String, String[]>)request.getAttribute("server.jspf-serverPreferencesMap");
PortletPreferences companyPreferences = (PortletPreferences)request.getAttribute("server.jspf-companyPreferences");
Map<String, String[]> companyPreferencesMap = (Map<String, String[]>)request.getAttribute("server.jspf-companyPreferencesMap");

boolean isPropertyValueOverridden = false;

if (serverPreferencesMap.containsKey(property)) {
	value = serverPreferences.getValue(property, StringPool.BLANK);

	isPropertyValueOverridden = true;
}

if (companyPreferencesMap.containsKey(property)) {
	value = companyPreferences.getValue(property, StringPool.BLANK);

	isPropertyValueOverridden = true;
}
%>

<%= HtmlUtil.escape(StringUtil.shorten(value, 80)) %>

<c:if test="<%= isPropertyValueOverridden %>">
	 <liferay-ui:icon-help message='<%= LanguageUtil.format(pageContext, "this-property-was-stored-in-portal-preferences-using-control-panel-and-its-original-value-x-from-portal-ext-properties-was-overridden-by-this-value", HtmlUtil.escape(value)) %>' />
</c:if>