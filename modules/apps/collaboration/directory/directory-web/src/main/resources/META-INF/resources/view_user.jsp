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
User selUser = PortalUtil.getSelectedUser(request);

selUser = selUser.toEscapedModel();

Contact selContact = selUser.getContact();

List<Organization> organizations = OrganizationLocalServiceUtil.getUserOrganizations(selUser.getUserId());

request.setAttribute("addresses.className", Contact.class.getName());
request.setAttribute("addresses.classPK", selContact.getContactId());
request.setAttribute("emailAddresses.className", Contact.class.getName());
request.setAttribute("emailAddresses.classPK", selContact.getContactId());
request.setAttribute("phones.className", Contact.class.getName());
request.setAttribute("phones.classPK", selContact.getContactId());
request.setAttribute("user.organizations", organizations);
request.setAttribute("user.selContact", selContact);
request.setAttribute("user.selUser", selUser);
request.setAttribute("websites.className", Contact.class.getName());
request.setAttribute("websites.classPK", selContact.getContactId());
%>

<liferay-util:include page="/tabs1.jsp" servletContext="<%= application %>">
	<liferay-util:param name="showSearch" value="<%= Boolean.FALSE.toString() %>" />
</liferay-util:include>

<div class="user-information">
	<div class="entity-details section">
		<liferay-util:include page="/user/details.jsp" servletContext="<%= application %>" />
	</div>

	<div class="section">
		<liferay-util:include page="/user/addresses.jsp" servletContext="<%= application %>" />
	</div>

	<div class="section">
		<liferay-util:include page="/common/additional_email_addresses.jsp" servletContext="<%= application %>" />
	</div>

	<div class="section">
		<liferay-util:include page="/common/websites.jsp" servletContext="<%= application %>" />
	</div>

	<div class="section">
		<liferay-util:include page="/user/phone_numbers.jsp" servletContext="<%= application %>" />
	</div>

	<div class="section">
		<liferay-util:include page="/user/instant_messenger.jsp" servletContext="<%= application %>" />
	</div>

	<div class="section">
		<liferay-util:include page="/user/social_network.jsp" servletContext="<%= application %>" />
	</div>

	<div class="section">
		<liferay-util:include page="/user/sms.jsp" servletContext="<%= application %>" />
	</div>

	<div class="section">
		<liferay-util:include page="/user/comments.jsp" servletContext="<%= application %>" />
	</div>
</div>