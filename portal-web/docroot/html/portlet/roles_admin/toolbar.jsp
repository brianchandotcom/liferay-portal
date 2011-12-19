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

<%@ include file="/html/portlet/roles_admin/init.jsp" %>

<%
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-all");

PortletURL currentPortletURL = PortletURLUtil.clone(currentURLObj, renderResponse);

currentPortletURL.setParameter("struts_action", "/roles_admin/view");

currentURL = currentPortletURL.toString();
%>

<div class="lfr-portlet-toolbar">
	<portlet:renderURL var="viewRolesURL">
		<portlet:param name="struts_action" value="/roles_admin/view" />
	</portlet:renderURL>

	<span class="lfr-toolbar-button view-button <%= toolbarItem.equals("view-all") ? "current" : StringPool.BLANK %>">
		<a href="<%= viewRolesURL %>"><liferay-ui:message key="view-all" /></a>
	</span>

	<c:if test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_ROLE) %>">
		<liferay-portlet:renderURL varImpl="addRoleURL">
			<portlet:param name="struts_action" value="/roles_admin/edit_role" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:icon-menu align="left" cssClass='<%= "lfr-toolbar-button add-button " + (toolbarItem.equals("add") ? "current" : StringPool.BLANK) %>' direction="down" extended="<%= false %>" icon='<%= themeDisplay.getPathThemeImages() + "/common/add.png" %>' message="add">

			<%
			addRoleURL.setParameter("type", String.valueOf(RoleConstants.TYPE_REGULAR));
			%>

			<liferay-ui:icon
				image="user_icon"
				message="regular-role"
				method="get"
				url="<%= addRoleURL.toString() %>"
			/>

			<%
			addRoleURL.setParameter("type", String.valueOf(RoleConstants.TYPE_SITE));
			%>

			<liferay-ui:icon
				image="site_icon"
				message="site-role"
				method="get"
				url="<%= addRoleURL.toString() %>"
			/>

			<%
			addRoleURL.setParameter("type", String.valueOf(RoleConstants.TYPE_ORGANIZATION));
			%>

			<liferay-ui:icon
				image="organization_icon"
				message="organization-role"
				method="get"
				url="<%= addRoleURL.toString() %>"
			/>
		</liferay-ui:icon-menu>
	</c:if>
</div>