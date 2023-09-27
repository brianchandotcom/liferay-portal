<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
OrganizationScreenNavigationDisplayContext organizationScreenNavigationDisplayContext = (OrganizationScreenNavigationDisplayContext)request.getAttribute(UsersAdminWebKeys.ORGANIZATION_SCREEN_NAVIGATION_DISPLAY_CONTEXT);

request.setAttribute("contact_information.jsp-className", Organization.class.getName());
request.setAttribute("contact_information.jsp-classPK", organizationScreenNavigationDisplayContext.getOrganizationId());
%>

<clay:sheet-section>
	<liferay-util:include page="/common/phone_numbers.jsp" servletContext="<%= application %>">
		<liferay-util:param name="emptyResultsMessage" value="this-organization-does-not-have-any-phone-numbers" />
	</liferay-util:include>
</clay:sheet-section>

<clay:sheet-section>
	<liferay-util:include page="/common/additional_email_addresses.jsp" servletContext="<%= application %>">
		<liferay-util:param name="emptyResultsMessage" value="this-organization-does-not-have-any-additional-email-addresses" />
	</liferay-util:include>
</clay:sheet-section>

<clay:sheet-section>
	<liferay-util:include page="/common/websites.jsp" servletContext="<%= application %>">
		<liferay-util:param name="emptyResultsMessage" value="this-organization-does-not-have-any-websites" />
	</liferay-util:include>
</clay:sheet-section>