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

<%@ include file="/init.jsp" %>

<%
String navigation = ParamUtil.getString(request, "navigation", "home");

String ddmStructureKey = ParamUtil.getString(request, "ddmStructureKey");

PortletURL displayStyleURL = renderResponse.createRenderURL();

displayStyleURL.setParameter("navigation", HtmlUtil.escapeJS(navigation));
displayStyleURL.setParameter("folderId", String.valueOf(journalDisplayContext.getFolderId()));

if (!ddmStructureKey.equals("0")) {
	displayStyleURL.setParameter("ddmStructureKey", ddmStructureKey);
}
%>

<liferay-frontend:management-bar-display-buttons
	displayStyleURL="<%= displayStyleURL %>"
	displayViews="<%= journalDisplayContext.getDisplayViews() %>"
	selectedDisplayStyle="<%= journalDisplayContext.getDisplayStyle() %>"
/>