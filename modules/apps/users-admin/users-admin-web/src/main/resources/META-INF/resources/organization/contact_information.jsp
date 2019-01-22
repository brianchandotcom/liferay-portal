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
OrganizationScreenNavigationDisplayContext organizationScreenNavigationDisplayContext = (OrganizationScreenNavigationDisplayContext)request.getAttribute(UsersAdminWebKeys.ORGANIZATION_SCREEN_NAVIGATION_DISPLAY_CONTEXT);

long organizationId = organizationScreenNavigationDisplayContext.getOrganizationId();

request.setAttribute("contact_information.jsp-className", Organization.class.getName());
request.setAttribute("contact_information.jsp-classPK", organizationId);
%>

<div class="sheet-section">
	<liferay-util:include page="/common/phone_numbers.jsp" servletContext="<%= application %>">
		<liferay-util:param name="emptyResultsMessage" value="this-organization-does-not-have-any-phone-numbers" />
	</liferay-util:include>
</div>

<div class="sheet-section">
	<liferay-util:include page="/common/additional_email_addresses.jsp" servletContext="<%= application %>">
		<liferay-util:param name="emptyResultsMessage" value="this-organization-does-not-have-any-additional-email-addresses" />
	</liferay-util:include>
</div>

<div class="sheet-section">
	<liferay-util:include page="/common/websites.jsp" servletContext="<%= application %>">
		<liferay-util:param name="emptyResultsMessage" value="this-organization-does-not-have-any-websites" />
	</liferay-util:include>
</div>