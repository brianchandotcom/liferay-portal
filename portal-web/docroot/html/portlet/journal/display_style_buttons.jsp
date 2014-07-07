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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
String navigation = ParamUtil.getString(request, "navigation", "home");

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

String structureId = ParamUtil.getString(request, "structureId");

String displayStyle = JournalUtil.getDisplayStyle(liferayPortletRequest, displayViews);

PortletURL displayStyleURL = renderResponse.createRenderURL();

displayStyleURL.setParameter("struts_action", "/journal/view");
displayStyleURL.setParameter("navigation", HtmlUtil.escapeJS(navigation));
displayStyleURL.setParameter("folderId", String.valueOf(folderId));

if (!structureId.equals("0")) {
	displayStyleURL.setParameter("structureId", structureId);
}
%>

<c:if test="<%= displayViews.length > 1 %>">
	<div id="<portlet:namespace />displayStyleButtons">
		<liferay-ui:icon-menu direction="down" icon='<%= "../aui/" + _getIcon(displayStyle) %>' message="" select="<%= true %>">

			<%
			for (String dataStyle : displayViews) {
				displayStyleURL.setParameter("displayStyle", dataStyle);
			%>

				<liferay-ui:icon
					image='<%= "../aui/" + _getIcon(dataStyle) %>'
					message="<%= dataStyle %>"
					url="<%= displayStyleURL.toString() %>"
				/>

			<%
			}
			%>

		</liferay-ui:icon-menu>
	</div>
</c:if>

<%!
private String _getIcon(String displayStyle) {
	String icon = displayStyle;

	if (displayStyle.equals("descriptive")) {
		icon = "th-list";
	}
	else if (displayStyle.equals("icon")) {
		icon = "th-large";
	}
	else if (displayStyle.equals("list")) {
		icon = "align-justify";
	}

	return icon;
}
%>