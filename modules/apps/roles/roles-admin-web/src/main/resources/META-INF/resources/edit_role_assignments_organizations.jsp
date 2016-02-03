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

EmptyOnClickRowChecker rowChecker = new EmptyOnClickRowChecker(renderResponse);

if (tabs3.equals("available")) {
	rowChecker = new OrganizationRoleChecker(renderResponse, role);
}
%>

<liferay-ui:search-container
	id="assigneesSearch"
	rowChecker="<%= rowChecker %>"
	searchContainer="<%= new OrganizationSearch(renderRequest, portletURL) %>"
	var="organizationSearchContainer"
>

	<%
	OrganizationSearchTerms searchTerms = (OrganizationSearchTerms)organizationSearchContainer.getSearchTerms();

	long parentOrganizationId = OrganizationConstants.ANY_PARENT_ORGANIZATION_ID;

	LinkedHashMap<String, Object> organizationParams = new LinkedHashMap<String, Object>();

	if (tabs3.equals("current")) {
		organizationParams.put("organizationsRoles", Long.valueOf(role.getRoleId()));
	}
	%>

	<liferay-ui:organization-search-container-results
		organizationParams="<%= organizationParams %>"
		parentOrganizationId="<%= parentOrganizationId %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.Organization"
		escapedModel="<%= true %>"
		keyProperty="group.groupId"
		modelVar="organization"
	>
		<%@ include file="/organization_columns.jspf" %>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
</liferay-ui:search-container>