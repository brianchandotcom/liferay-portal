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

<%@ include file="/html/portlet/users_admin/init.jsp" %>

<%
UserSearch searchContainer = (UserSearch)request.getAttribute("liferay-ui:search:searchContainer");

String redirect = searchContainer.getIteratorURL().toString();

UserSearchTerms searchTerms = (UserSearchTerms)searchContainer.getSearchTerms();

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

User user2 = (User)row.getObject();

long userId = user2.getUserId();
%>

<liferay-ui:icon-menu icon="<%= StringPool.BLANK %>" message="<%= StringPool.BLANK %>">

	<%
	boolean hasUpdatePermission = UserPermissionUtil.contains(permissionChecker, userId, ActionKeys.UPDATE);
	%>

	<c:if test="<%= hasUpdatePermission %>">
		<portlet:renderURL var="editUserURL">
			<portlet:param name="struts_action" value="/users_admin/edit_user" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="p_u_i_d" value="<%= String.valueOf(userId) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			iconCssClass="icon-edit"
			message="edit"
			url="<%= editUserURL %>"
		/>
	</c:if>

	<c:if test="<%= UserPermissionUtil.contains(permissionChecker, userId, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= User.class.getName() %>"
			modelResourceDescription="<%= user2.getFullName() %>"
			resourcePrimKey="<%= String.valueOf(userId) %>"
			var="permissionsUserURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			iconCssClass="icon-lock"
			message="permissions"
			method="get"
			url="<%= permissionsUserURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED && hasUpdatePermission %>">
		<portlet:renderURL var="managePagesURL">
			<portlet:param name="struts_action" value="/users_admin/edit_layouts" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(user2.getGroup().getGroupId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			iconCssClass="icon-copy"
			message="manage-pages"
			url="<%= managePagesURL %>"
		/>
	</c:if>

	<c:if test="<%= !PropsValues.PORTAL_JAAS_ENABLE && PropsValues.PORTAL_IMPERSONATION_ENABLE && (userId != user.getUserId()) && !themeDisplay.isImpersonated() && UserPermissionUtil.contains(permissionChecker, userId, ActionKeys.IMPERSONATE) %>">
		<liferay-security:doAsURL
			doAsUserId="<%= userId %>"
			var="impersonateUserURL"
		/>

		<liferay-ui:icon
			iconCssClass="icon-user"
			message="impersonate-user"
			target="_blank"
			url="<%= impersonateUserURL %>"
		/>
	</c:if>

	<c:if test="<%= UserPermissionUtil.contains(permissionChecker, userId, ActionKeys.DELETE) %>">
		<c:if test="<%= !user2.isActive() %>">
			<portlet:actionURL var="restoreUserURL">
				<portlet:param name="struts_action" value="/users_admin/edit_user" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
				<portlet:param name="redirect" value="<%= redirect %>" />
				<portlet:param name="deleteUserIds" value="<%= String.valueOf(userId) %>" />
			</portlet:actionURL>

			<liferay-ui:icon
				iconCssClass="icon-ok-sign"
				message="activate"
				url="<%= restoreUserURL %>"
			/>
		</c:if>

		<portlet:actionURL var="deleteUserURL">
			<portlet:param name="struts_action" value="/users_admin/edit_user" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= user2.isActive() ? Constants.DEACTIVATE : Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="deleteUserIds" value="<%= String.valueOf(userId) %>" />
		</portlet:actionURL>

		<c:if test="<%= userId != user.getUserId() %>">
			<c:choose>
				<c:when test="<%= user2.isActive() %>">
					<liferay-ui:icon-deactivate url="<%= deleteUserURL %>" />
				</c:when>
				<c:when test="<%= !user2.isActive() && PropsValues.USERS_DELETE %>">
					<liferay-ui:icon-delete url="<%= deleteUserURL %>" />
				</c:when>
			</c:choose>
		</c:if>
	</c:if>
</liferay-ui:icon-menu>