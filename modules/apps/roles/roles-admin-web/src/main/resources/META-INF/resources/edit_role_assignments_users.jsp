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

UserRoleChecker rowChecker = new UserRoleChecker(renderResponse, role);
%>

<aui:input name="addUserIds" type="hidden" />
<aui:input name="removeUserIds" type="hidden" />

<liferay-ui:membership-policy-error />

<liferay-ui:search-container
	id="assigneesSearch"
	rowChecker="<%= rowChecker %>"
	searchContainer="<%= new UserSearch(renderRequest, portletURL) %>"
	var="userSearchContainer"
>

	<%
	UserSearchTerms searchTerms = (UserSearchTerms)userSearchContainer.getSearchTerms();

	LinkedHashMap<String, Object> userParams = new LinkedHashMap<String, Object>();

	if (tabs3.equals("current")) {
		userParams.put("usersRoles", Long.valueOf(role.getRoleId()));
	}
	%>

	<liferay-ui:user-search-container-results userParams="<%= userParams %>" />

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.User"
		escapedModel="<%= true %>"
		keyProperty="userId"
		modelVar="user2"
		rowIdProperty="screenName"
	>
		<%@ include file="/user_columns.jspf" %>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
</liferay-ui:search-container>