<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
OrganizationScreenNavigationDisplayContext organizationScreenNavigationDisplayContext = (OrganizationScreenNavigationDisplayContext)request.getAttribute(UsersAdminWebKeys.ORGANIZATION_SCREEN_NAVIGATION_DISPLAY_CONTEXT);

long organizationId = organizationScreenNavigationDisplayContext.getOrganizationId();

request.setAttribute("contact_information.jsp-className", Organization.class.getName());
request.setAttribute("contact_information.jsp-classPK", organizationId);
request.setAttribute("contact_information.jsp-mvcActionPath", "/users_admin/update_organization_contact_information");
%>

<aui:input name="classPK" type="hidden" value="<%= String.valueOf(organizationId) %>" />

<clay:sheet-section>
	<liferay-util:include page="/common/addresses.jsp" servletContext="<%= application %>">
		<liferay-util:param name="emptyResultsMessage" value="this-organization-does-not-have-any-addresses" />
	</liferay-util:include>
</clay:sheet-section>