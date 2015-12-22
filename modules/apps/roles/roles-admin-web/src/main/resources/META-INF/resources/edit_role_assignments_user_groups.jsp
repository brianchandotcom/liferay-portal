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
String tabs3 = (String)request.getAttribute("edit_role_assignments.jsp-tabs3");

int cur = (Integer)request.getAttribute("edit_role_assignments.jsp-cur");

Role role = (Role)request.getAttribute("edit_role_assignments.jsp-role");

String displayStyle = (String)request.getAttribute("edit_role_assignments.jsp-displayStyle");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_role_assignments.jsp-portletURL");

UserGroupRoleChecker rowChecker = new UserGroupRoleChecker(renderResponse, role);
%>

<aui:input name="addGroupIds" type="hidden" />
<aui:input name="removeGroupIds" type="hidden" />

<liferay-ui:search-container
	rowChecker="<%= rowChecker %>"
	searchContainer="<%= new UserGroupSearch(renderRequest, portletURL) %>"
>

	<%
	UserGroupDisplayTerms searchTerms = (UserGroupDisplayTerms)searchContainer.getSearchTerms();

	LinkedHashMap<String, Object> userGroupParams = new LinkedHashMap<String, Object>();

	if (tabs3.equals("current")) {
		userGroupParams.put("userGroupsRoles", Long.valueOf(role.getRoleId()));
	}
	%>

	<liferay-ui:user-group-search-container-results
		searchTerms="<%= searchTerms %>"
		useIndexer="<%= false %>"
		userGroupParams="<%= userGroupParams %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.UserGroup"
		escapedModel="<%= true %>"
		keyProperty="group.groupId"
		modelVar="userGroup"
	>
		<%@ include file="/user_group_columns.jspf" %>
	</liferay-ui:search-container-row>

	<%
	String taglibOnClick = renderResponse.getNamespace() + "updateRoleGroups('" + portletURL.toString() + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur=" + cur + "');";
	%>

	<aui:button onClick="<%= taglibOnClick %>" value="update-associations" />

	<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
</liferay-ui:search-container>