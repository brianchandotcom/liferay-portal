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
String[] displayViews = (String[])request.getAttribute("liferay-frontend:management-bar-display-buttons:displayViews");
PortletURL portletURL = (PortletURL)request.getAttribute("liferay-frontend:management-bar-display-buttons:portletURL");
String selectedDisplayStyle = (String)request.getAttribute("liferay-frontend:management-bar-display-buttons:selectedDisplayStyle");

PortletURL displayStyleURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);
%>

<%
for (String displayStyle : displayViews) {
	displayStyleURL.setParameter("displayStyle", displayStyle);

	String iconCssClass = "icon-";

	if (displayStyle.equals("descriptive")) {
		iconCssClass += "th-list";
	}
	else if (displayStyle.equals("icon")) {
		iconCssClass += "th-large";
	}
	else if (displayStyle.equals("list")) {
		iconCssClass += "align-justify";
	}
%>

	<liferay-frontend:management-bar-button
		active="<%= displayStyle.equals(selectedDisplayStyle) %>"
		href="<%= displayStyleURL.toString() %>"
		iconCssClass="<%= iconCssClass %>"
		label="<%= displayStyle %>"
	/>

<%
}
%>