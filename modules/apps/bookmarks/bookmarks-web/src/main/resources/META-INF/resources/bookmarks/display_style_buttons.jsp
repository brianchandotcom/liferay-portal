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

<%@ include file="/bookmarks/init.jsp" %>

<%
String navigation = ParamUtil.getString(request, "navigation", "home");

int curEntry = ParamUtil.getInteger(request, "curEntry");
int deltaEntry = ParamUtil.getInteger(request, "deltaEntry");

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(BookmarksPortletKeys.BOOKMARKS, "display-style", "descriptive");
}

String keywords = ParamUtil.getString(request, "keywords");

PortletURL displayStyleURL = renderResponse.createRenderURL();

if (Validator.isNull(keywords)) {
	displayStyleURL.setParameter("mvcRenderCommandName", "/bookmarks/view");
}
else {
	displayStyleURL.setParameter("mvcPath", "/bookmarks/search.jsp");
}

displayStyleURL.setParameter("navigation", HtmlUtil.escapeJS(navigation));

if (curEntry > 0) {
	displayStyleURL.setParameter("curEntry", String.valueOf(curEntry));
}

if (deltaEntry > 0) {
	displayStyleURL.setParameter("deltaEntry", String.valueOf(deltaEntry));
}

displayStyleURL.setParameter("folderId", String.valueOf(folderId));
%>

<liferay-frontend:management-bar-display-buttons
	displayViews='<%= new String[] {"descriptive", "list"} %>'
	portletURL="<%= displayStyleURL %>"
	selectedDisplayStyle="<%= displayStyle %>"
/>