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

<%@ include file="/init.jsp" %>

<%
SearchContainer searchContainer = (SearchContainer)request.getAttribute(WebKeys.SEARCH_CONTAINER);

String toolbarItem = ParamUtil.getString(request, "toolbarItem");

long groupId = ParamUtil.getLong(request, "groupId", scopeGroupId);
%>

<aui:nav-bar>
	<aui:nav cssClass="navbar-nav" searchContainer="<%= searchContainer %>">
		<c:if test="<%= ddmDisplay.isShowAddStructureButton() && DDMStructurePermission.containsAddStruturePermission(permissionChecker, groupId, scopeClassNameId) %>">
			<liferay-portlet:renderURL var="viewStructuresURL">
				<portlet:param name="mvcPath" value="/view.jsp" />
				<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
			</liferay-portlet:renderURL>

			<liferay-portlet:renderURL var="addStructureURL">
				<portlet:param name="mvcPath" value="/edit_structure.jsp" />
				<portlet:param name="redirect" value="<%= viewStructuresURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
			</liferay-portlet:renderURL>

			<aui:nav-item href="<%= addStructureURL %>" iconCssClass="icon-plus" label="add" selected='<%= toolbarItem.equals("add") %>' />
		</c:if>
	</aui:nav>

	<aui:nav-bar-search searchContainer="<%= searchContainer %>">
		<liferay-util:include page="/structure_search.jsp" servletContext="<%= application %>" />
	</aui:nav-bar-search>
</aui:nav-bar>