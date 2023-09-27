<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

<liferay-ui:icon-delete
	icon="times-circle"
	linkCssClass="icon-monospaced text-default"
	message="delete"
	showIcon="<%= true %>"
	url="<%= deleteTeamUserGroupsURL %>"
/>