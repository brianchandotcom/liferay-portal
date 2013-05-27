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

<%@ include file="/html/portlet/dynamic_data_mapping/init.jsp" %>

<%
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-all");

long groupId = ParamUtil.getLong(request, "groupId", scopeGroupId);
%>

<aui:nav-bar>
	<aui:nav>
		<portlet:renderURL var="viewStructuresURL">
			<portlet:param name="struts_action" value="/dynamic_data_mapping/view" />
			<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
		</portlet:renderURL>

		<aui:nav-item href="<%= viewStructuresURL %>" label="view-all" selected='<%= toolbarItem.equals("view-all") %>' />

		<c:if test="<%= ddmDisplay.isShowAddStructureButton(permissionChecker, groupId) %>">
			<portlet:renderURL var="addStructureURL">
				<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_structure" />
				<portlet:param name="redirect" value="<%= viewStructuresURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
			</portlet:renderURL>

			<aui:nav-item href="<%= addStructureURL %>" iconClass="icon-plus" label="add" selected='<%= toolbarItem.equals("add") %>' />
		</c:if>
	</aui:nav>

	<aui:nav-bar-search cssClass="pull-right" file="/html/portlet/dynamic_data_mapping/structure_search.jsp" />
</aui:nav-bar>