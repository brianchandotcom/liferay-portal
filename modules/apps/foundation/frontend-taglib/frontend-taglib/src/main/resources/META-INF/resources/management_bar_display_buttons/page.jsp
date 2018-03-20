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

<%@ include file="/management_bar_display_buttons/init.jsp" %>

<%
String[] defaultViews = (String[])request.getAttribute("liferay-frontend:management-bar-display-buttons:defaultViews");
boolean disabled = GetterUtil.getBoolean(request.getAttribute("liferay-frontend:management-bar-display-buttons:disabled"));
String[] displayViews = (String[])request.getAttribute("liferay-frontend:management-bar-display-buttons:displayViews");
PortletURL portletURL = (PortletURL)request.getAttribute("liferay-frontend:management-bar-display-buttons:portletURL");
String selectedDisplayStyle = (String)request.getAttribute("liferay-frontend:management-bar-display-buttons:selectedDisplayStyle");

PortletURL displayStyleURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

for (String displayStyle : defaultViews) {
	String cssClass = StringPool.BLANK;

	if (displayStyle.equals("list")) {
		cssClass = "hidden-xs";
	}

	displayStyleURL.setParameter("displayStyle", displayStyle);

	String icon = "table2";

	if (displayStyle.equals("descriptive")) {
		icon = "list";
	}
	else if (displayStyle.equals("icon")) {
		icon = "cards2";
	}
%>

	<liferay-frontend:management-bar-button
		active="<%= displayStyle.equals(selectedDisplayStyle) %>"
		cssClass="<%= cssClass %>"
		disabled="<%= disabled || !ArrayUtil.contains(displayViews, displayStyle) %>"
		href='<%= (disabled || !ArrayUtil.contains(displayViews, displayStyle)) ? "javascript:;" : displayStyleURL.toString() %>'
		icon="<%= icon %>"
		label="<%= displayStyle %>"
	/>

<%
}
%>