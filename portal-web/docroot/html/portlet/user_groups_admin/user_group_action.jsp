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

<%@ include file="/html/portlet/user_groups_admin/init.jsp" %>

<%
UserGroupSearch searchContainer = (UserGroupSearch)request.getAttribute("liferay-ui:search:searchContainer");

String redirect = searchContainer.getIteratorURL().toString();

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

UserGroup userGroup = (UserGroup)row.getObject();
%>

<liferay-ui:icon-menu>
	<c:if test="<%= UserGroupPermissionUtil.contains(permissionChecker, userGroup.getUserGroupId(), ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="struts_action" value="/users_admin/edit_user_group" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="userGroupId" value="<%= String.valueOf(userGroup.getUserGroupId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			image="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<%
	boolean hasPermissionsPermission = UserGroupPermissionUtil.contains(permissionChecker, userGroup.getUserGroupId(), ActionKeys.PERMISSIONS);
	%>

	<c:if test="<%= hasPermissionsPermission %>">
		<liferay-security:permissionsURL
			modelResource="<%= UserGroup.class.getName() %>"
			modelResourceDescription="<%= userGroup.getName() %>"
			resourcePrimKey="<%= String.valueOf(userGroup.getUserGroupId()) %>"
			var="permissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			image="permissions"
			method="get"
			url="<%= permissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= hasPermissionsPermission %>">
		<liferay-security:permissionsURL
			modelResource="<%= Group.class.getName() %>"
			modelResourceDescription='<%= LanguageUtil.format(pageContext, "site-for-user-group-x", userGroup.getName()) %>'
			resourcePrimKey="<%= String.valueOf(userGroup.getGroup().getGroupId()) %>"
			var="permissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			image="permissions"
			message="site-permissions"
			method="get"
			url="<%= permissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<%
	Group userGroupGroup = userGroup.getGroup();
	%>

	<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, userGroupGroup.getGroupId(), ActionKeys.MANAGE_LAYOUTS) %>">
		<portlet:renderURL var="managePagesURL">
			<portlet:param name="struts_action" value="/users_admin/edit_layouts" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(userGroupGroup.getGroupId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			image="pages"
			message="manage-site-pages"
			url="<%= managePagesURL %>"
		/>
	</c:if>

	<%
	boolean hasViewPermission = GroupPermissionUtil.contains(permissionChecker, userGroupGroup.getGroupId(), ActionKeys.VIEW);
	%>

	<c:if test="<%= hasViewPermission && (userGroupGroup.getPublicLayoutsPageCount() > 0) %>">
		<portlet:actionURL var="viewPublicPagesURL">
			<portlet:param name="struts_action" value="/sites_admin/page" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(userGroupGroup.getGroupId()) %>" />
			<portlet:param name="privateLayout" value="<%= Boolean.FALSE.toString() %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			image="view"
			message="go-to-the-site's-public-pages"
			target="_blank"
			url="<%= viewPublicPagesURL %>"
		/>
	</c:if>

	<c:if test="<%= hasViewPermission && (userGroupGroup.getPrivateLayoutsPageCount() > 0) %>">
		<portlet:actionURL var="viewPrivatePagesURL">
			<portlet:param name="struts_action" value="/sites_admin/page" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(userGroupGroup.getGroupId()) %>" />
			<portlet:param name="privateLayout" value="<%= Boolean.TRUE.toString() %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			image="view"
			message="go-to-the-site's-private-pages"
			target="_blank"
			url="<%= viewPrivatePagesURL %>"
		/>
	</c:if>

	<c:if test="<%= UserGroupPermissionUtil.contains(permissionChecker, userGroup.getUserGroupId(), ActionKeys.ASSIGN_MEMBERS) %>">
		<portlet:renderURL var="assignURL">
			<portlet:param name="struts_action" value="/users_admin/edit_user_group_assignments" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="userGroupId" value="<%= String.valueOf(userGroup.getUserGroupId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			image="assign"
			message="assign-members"
			url="<%= assignURL %>"
		/>
	</c:if>

	<c:if test="<%= UserGroupPermissionUtil.contains(permissionChecker, userGroup.getUserGroupId(), ActionKeys.DELETE) %>">

		<%
		String taglibDeleteURL = "javascript:" + renderResponse.getNamespace() + "deleteUserGroup('" + userGroup.getUserGroupId() + "');";
		%>

		<liferay-ui:icon
			image="delete"
			url="<%= taglibDeleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>