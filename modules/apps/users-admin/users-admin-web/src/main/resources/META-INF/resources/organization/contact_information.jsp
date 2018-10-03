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
%>

<div class="sheet-section">
	<liferay-util:include page="/organization/phone_numbers.jsp" servletContext="<%= application %>" />
</div>

<div class="sheet-section">
	<liferay-util:include page="/organization/additional_email_addresses.jsp" servletContext="<%= application %>" />
</div>

<div class="sheet-section">
	<liferay-util:include page="/organization/websites.jsp" servletContext="<%= application %>" />
</div>

<portlet:renderURL var="editPhoneRenderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/organization/edit_phone_number.jsp" />
</portlet:renderURL>

<portlet:renderURL var="editEmailAddressRenderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/organization/edit_email_address.jsp" />
</portlet:renderURL>

<portlet:renderURL var="editWebsiteRenderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/organization/edit_website.jsp" />
</portlet:renderURL>

<aui:script require="<%= organizationScreenNavigationDisplayContext.getContactInformationJSRequire() %>">
	ContactInformation.registerContactInformationListener(
		'.modify-phone-number-link a',
		'<%= editPhoneRenderURL.toString() %>',
		470
	);

	ContactInformation.registerContactInformationListener(
		'.modify-email-address-link a',
		'<%= editEmailAddressRenderURL.toString() %>',
		390
	);

	ContactInformation.registerContactInformationListener(
		'.modify-website-link a',
		'<%= editWebsiteRenderURL.toString() %>',
		390
	);
</aui:script>