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

<%@ include file="/html/portlet/dynamic_data_mapping/init.jsp" %>

<%
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-all");

long groupId = ParamUtil.getLong(request, "groupId", scopeGroupId);
%>

<div class="lfr-portlet-toolbar">
	<portlet:renderURL var="viewStructuresURL">
		<portlet:param name="struts_action" value="/dynamic_data_mapping/view" />
		<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	</portlet:renderURL>

	<span class="lfr-toolbar-button view-button <%= toolbarItem.equals("view-all") ? "current" : StringPool.BLANK %>">
		<a href="<%= viewStructuresURL %>"><liferay-ui:message key="view-all" /></a>
	</span>

	<c:if test="<%= DDMPermission.contains(permissionChecker, scopeGroupId, ddmResource, ActionKeys.ADD_STRUCTURE) %>">
		<portlet:renderURL var="addStructureURL">
			<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_structure" />
			<portlet:param name="redirect" value="<%= viewStructuresURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
		</portlet:renderURL>

		<span class="lfr-toolbar-button add-button <%= toolbarItem.equals("add") ? "current" : StringPool.BLANK %>">
			<a href="<%= addStructureURL %>"><liferay-ui:message key="add" /></a>
		</span>
	</c:if>
</div>