<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/directory/init.jsp" %>

<%
OrganizationSearch searchContainer = (OrganizationSearch)request.getAttribute("liferay-ui:search:searchContainer");

String redirect = searchContainer.getIteratorURL().toString();

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Organization organization = (Organization)row.getObject();

long organizationId = organization.getOrganizationId();
%>

<liferay-ui:icon-menu>
	<portlet:renderURL var="viewUsersURL">
		<portlet:param name="struts_action" value="/directory/view" />
		<portlet:param name="tabs1" value="users" />
		<portlet:param name="viewUsersRedirect" value="<%= currentURL %>" />
		<portlet:param name="organizationId" value="<%= String.valueOf(organizationId) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		image="view_users"
		message="view-users"
		method="get" url="<%= viewUsersURL %>"
	/>

	<c:if test="<%= organization.hasSuborganizations() %>">
		<portlet:renderURL var="viewSuborganizationsURL">
			<portlet:param name="struts_action" value="/directory/view" />
			<portlet:param name="tabs1" value="organizations" />
			<portlet:param name="viewUsersRedirect" value="<%= currentURL %>" />
			<portlet:param name="parentOrganizationId" value="<%= String.valueOf(organizationId) %>" />
		</portlet:renderURL>

		<liferay-ui:icon image="view_locations" message="view-suborganizations" method="get" url="<%= viewSuborganizationsURL %>" />
	</c:if>
</liferay-ui:icon-menu>