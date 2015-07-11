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

<%@ include file="/blogs_admin/init.jsp" %>

<%
String toolbarItem = ParamUtil.getString(request, "toolbarItem");
%>

<aui:nav-bar>
	<aui:nav cssClass="navbar-nav">
		<portlet:renderURL var="viewEntriesURL">
			<portlet:param name="mvcRenderCommandName" value="/blogs_admin/view" />
		</portlet:renderURL>

		<c:if test="<%= BlogsPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_ENTRY) %>">
			<portlet:renderURL var="addEntryURL">
				<portlet:param name="mvcRenderCommandName" value="/blogs/edit_entry" />
				<portlet:param name="redirect" value="<%= viewEntriesURL %>" />
				<portlet:param name="backURL" value="<%= viewEntriesURL %>" />
			</portlet:renderURL>

			<aui:nav-item href="<%= addEntryURL %>" iconCssClass="icon-plus" label="add-blog-entry" selected='<%= toolbarItem.equals("add") %>' />
		</c:if>

		<c:if test="<%= BlogsPermission.contains(permissionChecker, scopeGroupId, ActionKeys.PERMISSIONS) %>">
			<liferay-security:permissionsURL
				modelResource="com.liferay.portlet.blogs"
				modelResourceDescription="<%= HtmlUtil.escape(themeDisplay.getScopeGroupName()) %>"
				resourcePrimKey="<%= String.valueOf(scopeGroupId) %>"
				var="permissionsURL"
				windowState="<%= LiferayWindowState.POP_UP.toString() %>"
			/>

			<aui:nav-item href="<%= permissionsURL %>" label="permissions" title="edit-permissions" useDialog="<%= true %>" />
		</c:if>
	</aui:nav>

	<aui:nav-bar-search>
		<div class="form-search">
			<liferay-ui:input-search autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" id="keywords1" name="keywords" placeholder='<%= LanguageUtil.get(request, "keywords") %>' />
		</div>
	</aui:nav-bar-search>
</aui:nav-bar>