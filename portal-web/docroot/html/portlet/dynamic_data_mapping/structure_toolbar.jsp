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

<%@ include file="/html/portlet/dynamic_data_mapping/init.jsp" %>

<%
SearchContainer searchContainer = (SearchContainer)request.getAttribute(WebKeys.SEARCH_CONTAINER);

String toolbarItem = ParamUtil.getString(request, "toolbarItem");

long groupId = ParamUtil.getLong(request, "groupId", scopeGroupId);
long classPK = ParamUtil.getLong(request, "classPK");
String eventName = ParamUtil.getString(request, "eventName", "selectStructure");
%>

<aui:nav-bar>
	<aui:nav cssClass="navbar-nav" searchContainer="<%= searchContainer %>">
		<c:if test="<%= ddmDisplay.isShowAddStructureButton() && DDMPermission.contains(permissionChecker, groupId, ddmDisplay.getResourceName(), ddmDisplay.getAddStructureActionId()) %>">
			<portlet:renderURL var="viewStructureURL">
				<portlet:param name="struts_action" value="/dynamic_data_mapping/select_structure" />
				<portlet:param name="classPK" value="<%= String.valueOf(classPK) %>" />
				<portlet:param name="eventName" value="<%= eventName %>" />
			</portlet:renderURL>

			<portlet:renderURL var="addStructureURL">
				<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_structure" />
				<portlet:param name="redirect" value="<%= viewStructureURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
			</portlet:renderURL>

			<aui:nav-item href="<%= addStructureURL %>" iconCssClass="icon-plus" label="add" selected='<%= toolbarItem.equals("add") %>' />
		</c:if>
	</aui:nav>

	<aui:nav-bar-search cssClass="navbar-search-advanced" file="/html/portlet/dynamic_data_mapping/structure_search.jsp" searchContainer="<%= searchContainer %>" />
</aui:nav-bar>