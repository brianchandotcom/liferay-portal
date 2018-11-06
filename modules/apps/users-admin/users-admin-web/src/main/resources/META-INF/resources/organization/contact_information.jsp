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

String contactInformationRequireJS = organizationScreenNavigationDisplayContext.getContactInformationJSRequire();
%>

<aui:input name="classPK" type="hidden" value="<%= String.valueOf(organizationId) %>" />

<div class="sheet-section">
	<liferay-util:include page="/common/phone_numbers.jsp" servletContext="<%= application %>">
		<liferay-util:param name="className" value="<%= Organization.class.getName() %>" />
		<liferay-util:param name="classPK" value="<%= String.valueOf(organizationId) %>" />
		<liferay-util:param name="contactInformationRequireJS" value="<%= contactInformationRequireJS %>" />
		<liferay-util:param name="emptyResultsMessage" value="this-organization-does-not-have-any-phone-numbers" />
		<liferay-util:param name="mvcActionPath" value="/users_admin/update_organization_contact_information" />
	</liferay-util:include>
</div>

<div class="sheet-section">
	<liferay-util:include page="/organization/additional_email_addresses.jsp" servletContext="<%= application %>" />
</div>

<div class="sheet-section">
	<liferay-util:include page="/organization/websites.jsp" servletContext="<%= application %>" />
</div>

<portlet:renderURL var="editEmailAddressRenderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/organization/edit_email_address.jsp" />
</portlet:renderURL>

<portlet:renderURL var="editWebsiteRenderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/organization/edit_website.jsp" />
</portlet:renderURL>

<aui:script require="<%= organizationScreenNavigationDisplayContext.getContactInformationJSRequire() %>">
	ContactInformation.registerContactInformationListener(
		'.modify-email-address-link a',
		'<%= editEmailAddressRenderURL.toString() %>',
		390
	);

	ContactInformation.registerContactInformationListener(
		'.modify-website-link a',
		'<%= editWebsiteRenderURL.toString() %>',
		460
	);
</aui:script>