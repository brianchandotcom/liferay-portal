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
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-members");

long groupId = ParamUtil.getLong(request, "groupId", themeDisplay.getSiteGroupId());

Group group = GroupLocalServiceUtil.getGroup(groupId);
%>

<aui:nav-bar>
	<aui:nav cssClass="navbar-nav">
		<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, group, ActionKeys.ASSIGN_MEMBERS) %>">
			<aui:nav-item dropdown="<%= true %>" iconCssClass="icon-plus" label="add-members" selected='<%= toolbarItem.equals("add-members") %>'>
				<liferay-portlet:renderURL varImpl="assignMembersURL">
					<liferay-portlet:param name="mvcPath" value="/view.jsp" />
				</liferay-portlet:renderURL>

				<%
				assignMembersURL.setParameter("tabs1", "users");
				assignMembersURL.setParameter("tabs2", "available");
				%>

				<aui:nav-item href="<%= assignMembersURL.toString() %>" iconCssClass="icon-user" label="user" />

				<%
				assignMembersURL.setParameter("tabs1", "organizations");
				assignMembersURL.setParameter("tabs2", "available");
				%>

				<aui:nav-item href="<%= assignMembersURL.toString() %>" iconCssClass="icon-globe" label="organization" />

				<%
				assignMembersURL.setParameter("tabs1", "user-groups");
				assignMembersURL.setParameter("tabs2", "available");
				%>

				<aui:nav-item href="<%= assignMembersURL.toString() %>" iconCssClass="icon-globe" label="user-group" />
			</aui:nav-item>
		</c:if>
	</aui:nav>
</aui:nav-bar>