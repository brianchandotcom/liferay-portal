<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/users_admin/init.jsp" %>

<%
String tabs3 = (String)request.getAttribute("edit_organization_assignments.jsp-tabs3");

int cur = (Integer)request.getAttribute("edit_organization_assignments.jsp-cur");

Organization organization = (Organization)request.getAttribute("edit_organization_assignments.jsp-organization");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_organization_assignments.jsp-portletURL");
%>

<liferay-ui:tabs
	names="current,available"
	param="tabs3"
	url="<%= portletURL.toString() %>"
/>

<aui:input name="addUserIds" type="hidden" />
<aui:input name="removeUserIds" type="hidden" />

<liferay-ui:search-container
	rowChecker="<%= new UserOrganizationChecker(renderResponse, organization) %>"
	searchContainer="<%= new UserSearch(renderRequest, portletURL) %>"
>
	<liferay-ui:search-form
		page="/html/portlet/users_admin/user_search.jsp"
	/>

	<%
	UserSearchTerms searchTerms = (UserSearchTerms)searchContainer.getSearchTerms();

	LinkedHashMap userParams = new LinkedHashMap();

	if (tabs3.equals("current")) {
		userParams.put("usersOrgs", new Long(organization.getOrganizationId()));
	}
	else if (PropsValues.ORGANIZATIONS_ASSIGNMENT_STRICT && !permissionChecker.isCompanyAdmin()) {
		userParams.put("usersOrgsTree", user.getOrganizations());
	}
	%>

	<liferay-ui:search-container-results>
		<%@ include file="/html/portlet/users_admin/user_search_results.jspf" %>
	</liferay-ui:search-container-results>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.User"
		escapedModel="<%= true %>"
		keyProperty="userId"
		modelVar="user2"
	>
		<liferay-ui:search-container-column-text
			name="name"
			property="fullName"
		/>

		<liferay-ui:search-container-column-text
			name="screen-name"
			property="screenName"
		/>
	</liferay-ui:search-container-row>

	<div class="separator"><!-- --></div>

	<%
	String taglibOnClick = renderResponse.getNamespace() + "updateOrganizationUsers('" + portletURL.toString() + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur=" + cur + "');";
	%>

	<aui:button onClick="<%= taglibOnClick %>" value="update-associations" />

	<br /><br />

	<liferay-ui:search-iterator />
</liferay-ui:search-container>