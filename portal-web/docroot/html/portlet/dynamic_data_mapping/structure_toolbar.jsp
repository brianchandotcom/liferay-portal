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

long classPK = ParamUtil.getLong(request, "classPK");
String eventName = ParamUtil.getString(request, "eventName", "selectStructure");
%>

<aui:nav-bar>
	<aui:nav>
		<portlet:renderURL var="viewStructureURL">
			<portlet:param name="struts_action" value="/dynamic_data_mapping/select_structure" />
			<portlet:param name="classPK" value="<%= String.valueOf(classPK) %>" />
			<portlet:param name="eventName" value="<%= eventName %>" />
		</portlet:renderURL>

		<aui:nav-item href="<%= viewStructureURL %>" label="view-all" selected='<%= toolbarItem.equals("view-all") %>' />

		<c:if test="<%= ddmDisplay.isShowAddStructureButton(permissionChecker, scopeGroupId) %>">
			<portlet:renderURL var="addStructureURL">
				<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_structure" />
				<portlet:param name="redirect" value="<%= viewStructureURL %>" />
			</portlet:renderURL>

			<aui:nav-item href="<%= addStructureURL %>" iconClass="icon-plus" label="add" selected='<%= toolbarItem.equals("add") %>' />
		</c:if>
	</aui:nav>

	<aui:nav-bar-search cssClass="pull-right" file="/html/portlet/dynamic_data_mapping/structure_search.jsp" />
</aui:nav-bar>