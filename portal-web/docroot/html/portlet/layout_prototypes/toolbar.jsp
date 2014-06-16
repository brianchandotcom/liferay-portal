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

<%@ include file="/html/portlet/layout_prototypes/init.jsp" %>

<%
String toolbarItem = ParamUtil.getString(request, "toolbarItem");
%>

<aui:nav-bar>
	<aui:nav cssClass="navbar-nav">
		<c:if test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_LAYOUT_PROTOTYPE) %>">
			<portlet:renderURL var="viewLayoutPrototypesURL">
				<portlet:param name="struts_action" value="/layout_prototypes/view" />
			</portlet:renderURL>

			<portlet:renderURL var="addLayoutPrototypeURL">
				<portlet:param name="struts_action" value="/layout_prototypes/edit_layout_prototype" />
				<portlet:param name="redirect" value="<%= viewLayoutPrototypesURL %>" />
				<portlet:param name="backURL" value="<%= viewLayoutPrototypesURL %>" />
			</portlet:renderURL>

			<aui:nav-item href="<%= addLayoutPrototypeURL %>" iconCssClass="icon-plus" label="add" selected='<%= toolbarItem.equals("add") %>' />
		</c:if>
	</aui:nav>
</aui:nav-bar>