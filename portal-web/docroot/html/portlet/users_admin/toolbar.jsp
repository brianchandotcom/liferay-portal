<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "browse");

String usersListView = ParamUtil.get(request, "usersListView", UserConstants.LIST_VIEW_TREE);
%>

<div class="lfr-portlet-toolbar">
	<portlet:renderURL var="viewUsersTreeURL">
		<portlet:param name="struts_action" value="/users_admin/view" />
		<portlet:param name="toolbarItem" value="browse" />
		<portlet:param name="usersListView" value="<%= UserConstants.LIST_VIEW_TREE %>" />
		<portlet:param name="saveUsersListView" value="<%= Boolean.TRUE.toString() %>" />
	</portlet:renderURL>

	<span class="lfr-toolbar-button view-button <%= toolbarItem.equals("browse") ? "current" : StringPool.BLANK %>">
		<a href="<%= viewUsersTreeURL %>"><liferay-ui:message key="browse" /></a>
	</span>

	<portlet:renderURL var="viewOrganizationsFlatURL">
		<portlet:param name="struts_action" value="/users_admin/view" />
		<portlet:param name="toolbarItem" value="view-all-organizations" />
		<portlet:param name="usersListView" value="<%= UserConstants.LIST_VIEW_FLAT_ORGANIZATIONS %>" />
		<portlet:param name="saveUsersListView" value="<%= Boolean.TRUE.toString() %>" />
	</portlet:renderURL>

	<span class="lfr-toolbar-button view-button <%= toolbarItem.equals("view-all-organizations") ? "current" : StringPool.BLANK %>">
		<a href="<%= viewOrganizationsFlatURL %>"><liferay-ui:message key="view-organizations" /></a>
	</span>

	<portlet:renderURL var="viewUsersFlatURL">
		<portlet:param name="struts_action" value="/users_admin/view" />
		<portlet:param name="toolbarItem" value="view-all-users" />
		<portlet:param name="usersListView" value="<%= UserConstants.LIST_VIEW_FLAT_USERS %>" />
		<portlet:param name="saveUsersListView" value="<%= Boolean.TRUE.toString() %>" />
	</portlet:renderURL>

	<span class="lfr-toolbar-button view-button <%= toolbarItem.equals("view-all-users") ? "current" : StringPool.BLANK %>">
		<a href="<%= viewUsersFlatURL %>"><liferay-ui:message key="view-users" /></a>
	</span>

	<%
	boolean hasAddOrganizationPermission = PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_ORGANIZATION);
	boolean hasAddUserPermission = PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_USER);
	%>

	<c:if test="<%= hasAddOrganizationPermission || hasAddUserPermission %>">
		<liferay-ui:icon-menu align="left" cssClass='<%= "lfr-toolbar-button add-button " + (toolbarItem.equals("add") ? "current" : StringPool.BLANK) %>' direction="down" extended="<%= false %>" icon='<%= themeDisplay.getPathThemeImages() + "/common/add.png" %>' message="add" showWhenSingleIcon="<%= true %>">
			<portlet:renderURL var="viewUsersURL">
				<portlet:param name="struts_action" value="/users_admin/view" />
				<portlet:param name="sitesListView" value="<%= usersListView %>" />
			</portlet:renderURL>

			<c:if test="<%= hasAddUserPermission %>">
				<portlet:renderURL var="addUserURL">
					<portlet:param name="struts_action" value="/users_admin/edit_user" />
					<portlet:param name="redirect" value="<%= viewUsersURL %>" />
				</portlet:renderURL>

				<liferay-ui:icon
					image="user_icon"
					message="user"
					url="<%= addUserURL %>"
				/>
			</c:if>

			<c:if test="<%= hasAddOrganizationPermission %>">

				<%
				for (String organizationType : PropsValues.ORGANIZATIONS_TYPES) {
				%>

					<portlet:renderURL var="addOrganizationURL">
						<portlet:param name="struts_action" value="/users_admin/edit_organization" />
						<portlet:param name="redirect" value="<%= viewUsersURL %>" />
						<portlet:param name="type" value="<%= organizationType %>" />
					</portlet:renderURL>

					<liferay-ui:icon
						image="add_location"
						message='<%= LanguageUtil.get(pageContext, organizationType) %>'
						url="<%= addOrganizationURL %>"
					/>

				<%
				}
				%>

			</c:if>
		</liferay-ui:icon-menu>
	</c:if>

	<c:choose>
		<c:when test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.EXPORT_USER) %>">
			<span class="lfr-toolbar-button export-button"><a href="javascript:<portlet:namespace />exportUsers();"><liferay-ui:message key="export-users" /></a></span>
		</c:when>
		<c:when test="<%= PortletPermissionUtil.contains(permissionChecker, PortletKeys.USERS_ADMIN, ActionKeys.EXPORT_USER) %>">
			<span class="lfr-toolbar-button export-button"><a href="javascript:<portlet:namespace />exportUsers();"><liferay-ui:message key="export-organization-users" /></a></span>
		</c:when>
	</c:choose>
</div>

<aui:script>
	function <portlet:namespace />exportUsers() {
		document.<portlet:namespace />fm.method = "post";
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/users_admin/export_users" /></portlet:actionURL>&compress=0&etag=0&strip=0", false);
	}
</aui:script>