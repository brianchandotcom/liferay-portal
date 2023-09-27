<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

UserGroup userGroup = (UserGroup)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= UserGroupPermissionUtil.contains(permissionChecker, userGroup.getUserGroupId(), ActionKeys.UPDATE) && UserGroupPermissionUtil.contains(permissionChecker, userGroup.getUserGroupId(), ActionKeys.VIEW) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcPath" value="/edit_user_group.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="userGroupId" value="<%= String.valueOf(userGroup.getUserGroupId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
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
			message="permissions"
			method="get"
			url="<%= permissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<%
	Group userGroupGroup = userGroup.getGroup();

	hasPermissionsPermission = GroupPermissionUtil.contains(permissionChecker, userGroupGroup, ActionKeys.PERMISSIONS);
	%>

	<c:if test="<%= hasPermissionsPermission %>">
		<liferay-security:permissionsURL
			modelResource="<%= Group.class.getName() %>"
			modelResourceDescription='<%= LanguageUtil.format(request, "pages-for-user-group-x", userGroup.getName(), false) %>'
			resourcePrimKey="<%= String.valueOf(userGroup.getGroup().getGroupId()) %>"
			var="permissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="user-group-pages-permissions"
			method="get"
			url="<%= permissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, userGroupGroup, ActionKeys.MANAGE_LAYOUTS) %>">
		<liferay-ui:icon
			message="manage-pages"
			url="<%=
				PortletURLBuilder.create(
					PortletProviderUtil.getPortletURL(request, userGroupGroup, Layout.class.getName(), PortletProvider.Action.EDIT)
				).setRedirect(
					currentURL
				).buildString()
			%>"
		/>
	</c:if>

	<%
	boolean hasViewPermission = GroupPermissionUtil.contains(permissionChecker, userGroupGroup, ActionKeys.VIEW);
	%>

	<c:if test="<%= hasViewPermission && (userGroupGroup.getPublicLayoutsPageCount() > 0) %>">
		<liferay-ui:icon
			message="go-to-profile-pages"
			target="_blank"
			url="<%= userGroupGroup.getDisplayURL(themeDisplay, false) %>"
		/>
	</c:if>

	<c:if test="<%= hasViewPermission && (userGroupGroup.getPrivateLayoutsPageCount() > 0) %>">
		<liferay-ui:icon
			message="go-to-dashboard-pages"
			target="_blank"
			url="<%= userGroupGroup.getDisplayURL(themeDisplay, true) %>"
		/>
	</c:if>

	<c:if test="<%= UserGroupPermissionUtil.contains(permissionChecker, userGroup.getUserGroupId(), ActionKeys.ASSIGN_MEMBERS) %>">
		<portlet:renderURL var="assignURL">
			<portlet:param name="mvcPath" value="/edit_user_group_assignments.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="userGroupId" value="<%= String.valueOf(userGroup.getUserGroupId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="assign-members"
			url="<%= assignURL %>"
		/>
	</c:if>

	<c:if test="<%= UserGroupPermissionUtil.contains(permissionChecker, userGroup.getUserGroupId(), ActionKeys.DELETE) %>">

		<%
		String taglibDeleteURL = "javascript:" + liferayPortletResponse.getNamespace() + "doDeleteUserGroup('" + UserGroup.class.getName() + "','" + userGroup.getUserGroupId() + "');";
		%>

		<liferay-ui:icon
			cssClass="item-remove"
			message="delete"
			url="<%= taglibDeleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>