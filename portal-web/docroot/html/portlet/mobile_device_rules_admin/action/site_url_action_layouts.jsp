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
	<aui:option disabled="<%= true %>" label="select-a-layout" selected="<%= actionLayoutId == 0 %>" value="0" />

	<c:if test="<%=!publicLayouts.isEmpty() %>">
		<aui:option disabled="<%= true %>" label="public-layouts" value="0" />

		<%
			for (Layout publicLayout : publicLayouts) {
		%>

				<aui:option label="<%= publicLayout.getName(locale) %>" selected="<%= publicLayout.getPlid() == actionLayoutId %>" value="<%= publicLayout.getPlid() %>" />

		<%
			}
		%>

	</c:if>

	<c:if test="<%=!privateLayouts.isEmpty() %>">
		<aui:option disabled="<%= true %>" label="private-layouts" value="0" />

		<%
			for (Layout privateLayout : privateLayouts) {
		%>

				<aui:option label="<%= privateLayout.getName(locale) %>" selected="<%= privateLayout.getPlid() == actionLayoutId %>" value="<%= privateLayout.getPlid() %>" />

		<%
			}
		%>

	</c:if>

	<c:if test="<%=publicLayouts.isEmpty() && privateLayouts.isEmpty() %>">
		<aui:option disabled="<%= true %>" label="no-available-layouts" value="0" />
	</c:if>
</aui:select>