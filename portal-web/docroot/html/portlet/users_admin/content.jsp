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

<%@ include file="/html/portlet/users_admin/init.jsp" %>

<%
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "browse");

String viewUsersRedirect = ParamUtil.getString(request, "viewUsersRedirect");
String backURL = ParamUtil.getString(request, "backURL", viewUsersRedirect);

int status = ParamUtil.getInteger(request, "status", WorkflowConstants.STATUS_APPROVED);

String usersListView = ParamUtil.get(request, "usersListView", UserConstants.LIST_VIEW_TREE);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/users_admin/view_users");
portletURL.setParameter("usersListView", usersListView);

if (Validator.isNotNull(viewUsersRedirect)) {
	portletURL.setParameter("viewUsersRedirect", viewUsersRedirect);
}

String portletURLString = portletURL.toString();

request.setAttribute("view.jsp-usersListView", usersListView);

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<liferay-ui:error exception="<%= CompanyMaxUsersException.class %>" message="unable-to-activate-user-because-that-would-exceed-the-maximum-number-of-users-allowed" />
<liferay-ui:error exception="<%= RequiredOrganizationException.class %>" message="you-cannot-delete-organizations-that-have-suborganizations-or-users" />
<liferay-ui:error exception="<%= RequiredUserException.class %>" message="you-cannot-delete-or-deactivate-yourself" />

<aui:form action="<%= portletURLString %>" method="post" name="fm">
	<liferay-portlet:renderURLParams varImpl="portletURL" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="toolbarItem" type="hidden" value="<%= toolbarItem %>" />
	<aui:input name="redirect" type="hidden" value="<%= portletURLString %>" />

	<%
	long organizationGroupId = 0;

	int inactiveUsersCount = 0;
	int usersCount = 0;

	long organizationId = ParamUtil.getLong(request, "organizationId", OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID);

	Organization organization = null;

	if (organizationId != 0) {
		organization = OrganizationServiceUtil.getOrganization(organizationId);
	}

	if (organization != null) {
		inactiveUsersCount = UserLocalServiceUtil.getOrganizationUsersCount(organizationId, WorkflowConstants.STATUS_INACTIVE);
		usersCount = UserLocalServiceUtil.getOrganizationUsersCount(organizationId, WorkflowConstants.STATUS_APPROVED);
	}
	else {
		LinkedHashMap<String, Object> userParams = new LinkedHashMap<String, Object>();

		if (!usersListView.equals(UserConstants.LIST_VIEW_FLAT_USERS)) {
			userParams.put("noOrganizations", Boolean.TRUE);
			userParams.put("usersOrgsCount", 0);
		}

		inactiveUsersCount = UserLocalServiceUtil.searchCount(company.getCompanyId(), null, WorkflowConstants.STATUS_INACTIVE, userParams);
		usersCount = UserLocalServiceUtil.searchCount(company.getCompanyId(), null, WorkflowConstants.STATUS_APPROVED, userParams);
	}
	%>

	<c:choose>
		<c:when test="<%= usersListView.equals(UserConstants.LIST_VIEW_FLAT_ORGANIZATIONS) %>">
			<liferay-util:include page="/html/portlet/users_admin/view_flat_organizations.jsp" />
		</c:when>
		<c:when test="<%= usersListView.equals(UserConstants.LIST_VIEW_FLAT_USERS) %>">

			<%
			boolean organizationContextView = false;
			%>

			<%@ include file="/html/portlet/users_admin/view_flat_users.jspf" %>
		</c:when>
		<c:otherwise>
			<%@ include file="/html/portlet/users_admin/view_tree.jspf" %>
		</c:otherwise>
	</c:choose>
</aui:form>