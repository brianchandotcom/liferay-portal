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

PortletURL portletURL = (PortletURL)request.getAttribute("edit_role_assignments.jsp-portletURL");
%>

<aui:input name="addGroupIds" type="hidden" />
<aui:input name="removeGroupIds" type="hidden" />

<liferay-ui:tabs
	names="current,available"
	param="tabs3"
	url="<%= portletURL.toString() %>"
/>

<liferay-ui:search-container
	rowChecker="<%= new OrganizationRoleChecker(renderResponse, role) %>"
	searchContainer="<%= new OrganizationSearch(renderRequest, portletURL) %>"
	var="organizationSearchContainer"
>
	<liferay-ui:organization-search-form />

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
		useIndexer="<%= false %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.Organization"
		escapedModel="<%= true %>"
		keyProperty="group.groupId"
		modelVar="organization"
	>
		<liferay-ui:search-container-column-text
			name="name"
			orderable="<%= true %>"
			property="name"
		/>

		<liferay-ui:search-container-column-text
			name="parent-organization"
			value="<%= HtmlUtil.escape(organization.getParentOrganizationName()) %>"
		/>

		<liferay-ui:search-container-column-text
			name="type"
			orderable="<%= true %>"
			value="<%= LanguageUtil.get(request, organization.getType()) %>"
		/>

		<liferay-ui:search-container-column-text
			name="city"
			value="<%= HtmlUtil.escape(organization.getAddress().getCity()) %>"
		/>

		<liferay-ui:search-container-column-text
			name="region"
			value="<%= UsersAdmin.ORGANIZATION_REGION_NAME_ACCESSOR.get(organization) %>"
		/>

		<liferay-ui:search-container-column-text
			name="country"
			value="<%= UsersAdmin.ORGANIZATION_COUNTRY_NAME_ACCESSOR.get(organization) %>"
		/>
	</liferay-ui:search-container-row>

	<div class="separator"><!-- --></div>

	<%
	String taglibOnClick = renderResponse.getNamespace() + "updateRoleGroups('" + portletURL.toString() + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur=" + cur + "');";
	%>

	<aui:button onClick="<%= taglibOnClick %>" value="update-associations" />

	<liferay-ui:search-iterator />
</liferay-ui:search-container>