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

<%@ include file="/html/portlet/blogs_admin/init.jsp" %>

<%
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-all");
%>

<aui:nav-bar>
	<aui:nav>
		<portlet:renderURL var="viewEntriesURL">
			<portlet:param name="struts_action" value="/blogs_admin/view" />
		</portlet:renderURL>

		<aui:nav-item href="<%= viewEntriesURL %>" label="view-all" selected='<%= toolbarItem.equals("view-all") %>' />

		<c:if test="<%= BlogsPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_ENTRY) %>">
			<portlet:renderURL var="addEntryURL">
				<portlet:param name="struts_action" value="/blogs_admin/edit_entry" />
				<portlet:param name="redirect" value="<%= viewEntriesURL %>" />
				<portlet:param name="backURL" value="<%= viewEntriesURL %>" />
			</portlet:renderURL>

			<aui:nav-item href="<%= addEntryURL %>" iconClass="icon-plus" label="add" selected='<%= toolbarItem.equals("add") %>' />
		</c:if>
	</aui:nav>
</aui:nav-bar>