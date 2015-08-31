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

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

String ddmStructureKey = ParamUtil.getString(request, "ddmStructureKey");

PortletURL displayStyleURL = renderResponse.createRenderURL();

displayStyleURL.setParameter("navigation", HtmlUtil.escapeJS(navigation));
displayStyleURL.setParameter("folderId", String.valueOf(folderId));

if (!ddmStructureKey.equals("0")) {
	displayStyleURL.setParameter("ddmStructureKey", ddmStructureKey);
}

for (String displayStyle : journalDisplayContext.getDisplayViews()) {
	displayStyleURL.setParameter("displayStyle", displayStyle);

	String displayStyleIcon = displayStyle;

	if (displayStyle.equals("descriptive")) {
		displayStyleIcon = "th-list";
	}
	else if (displayStyle.equals("icon")) {
		displayStyleIcon = "th-large";
	}
	else if (displayStyle.equals("list")) {
		displayStyleIcon = "align-justify";
	}
%>

	<aui:a
		cssClass='<%= displayStyle.equals(journalDisplayContext.getDisplayStyle()) ? "active btn" : "btn" %>'
		href="<%= displayStyleURL.toString() %>"
		iconCssClass='<%= "icon-" + HtmlUtil.escapeAttribute(displayStyleIcon) %>'
	/>

<%
}
%>