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
long teamId = ParamUtil.getLong(request, "teamId");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

UserGroup userGroup = (UserGroup)row.getObject();
%>

<portlet:actionURL name="deleteTeamUserGroups" var="deleteTeamUserGroupsURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="teamId" value="<%= String.valueOf(teamId) %>" />
	<portlet:param name="removeUserGroupId" value="<%= String.valueOf(userGroup.getUserGroupId()) %>" />
</portlet:actionURL>

<liferay-ui:icon
	iconCssClass="icon-trash"
	message="delete"
	url="<%= deleteTeamUserGroupsURL %>"
/>