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

<%@ include file="/html/portlet/mobile_device_rules_admin/action/init.jsp" %>

<%
long actionGroupId = (Long) request.getAttribute("actionGroupId");
long actionLayoutId = (Long) request.getAttribute("actionLayoutId");

List<Layout> publicLayouts = LayoutLocalServiceUtil.getLayouts(actionGroupId, false);
List<Layout> privateLayouts = LayoutLocalServiceUtil.getLayouts(actionGroupId, true);

Iterator<Layout> layoutIterator = publicLayouts.iterator();

while (layoutIterator.hasNext()) {
	Layout layoutEntry = layoutIterator.next();

	if (!LayoutPermissionUtil.contains(permissionChecker, layoutEntry, ActionKeys.VIEW)) {
		layoutIterator.remove();
	}
}

layoutIterator = privateLayouts.iterator();

while (layoutIterator.hasNext()) {
	Layout layoutEntry = layoutIterator.next();

	if (!LayoutPermissionUtil.contains(permissionChecker, layoutEntry, ActionKeys.VIEW)) {
		layoutIterator.remove();
	}
}
%>


<aui:select label="page" name="layoutId">
	<aui:option label="select-a-layout" value="0" selected="<%= actionLayoutId == 0%>" disabled="<%= true %>"/>

	<c:if test="<%=!publicLayouts.isEmpty()%>">
		<aui:option label="public-layouts" value="0" disabled="<%= true %>"/>
		<%
			for (Layout publicLayout : publicLayouts) {
		%>
				<aui:option label="<%= publicLayout.getName(locale) %>" value="<%= publicLayout.getPlid() %>" selected="<%= publicLayout.getPlid() == actionLayoutId %>" />
		<%
			}
		%>
	</c:if>

	<c:if test="<%=!privateLayouts.isEmpty()%>">
		<aui:option label="private-layouts" value="0" disabled="<%= true %>"/>
		<%
			for (Layout privateLayout : privateLayouts) {
		%>
				<aui:option label="<%= privateLayout.getName(locale) %>" value="<%= privateLayout.getPlid() %>" selected="<%= privateLayout.getPlid() == actionLayoutId %>" />
		<%
			}
		%>
	</c:if>

	<c:if test="<%=publicLayouts.isEmpty() && privateLayouts.isEmpty()%>">
		<aui:option label="no-available-layouts" value="0" disabled="<%= true %>"/>
	</c:if>
</aui:select>