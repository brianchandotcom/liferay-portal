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

<%@ include file="/html/portlet/directory/init.jsp" %>

<%
User selUser = (User)request.getAttribute(WebKeys.USER);

selUser = selUser.toEscapedModel();

Contact selContact = selUser.getContact();

List<Organization> organizations = OrganizationLocalServiceUtil.getUserOrganizations(selUser.getUserId());

request.setAttribute("user.selUser", selUser);
request.setAttribute("user.selContact", selContact);
request.setAttribute("user.organizations", organizations);
request.setAttribute("addresses.className", Contact.class.getName());
request.setAttribute("addresses.classPK", selContact.getContactId());
request.setAttribute("emailAddresses.className", Contact.class.getName());
request.setAttribute("emailAddresses.classPK", selContact.getContactId());
request.setAttribute("phones.className", Contact.class.getName());
request.setAttribute("phones.classPK", selContact.getContactId());
request.setAttribute("websites.className", Contact.class.getName());
request.setAttribute("websites.classPK", selContact.getContactId());
%>

<div class="user-information">
	<div class="section entity-details">
		<liferay-util:include page="/html/portlet/directory/user/details.jsp" />
	</div>

	<div class="section">
		<liferay-util:include page="/html/portlet/directory/user/addresses.jsp" />
	</div>

	<div class="section">
		<liferay-util:include page="/html/portlet/directory/common/additional_email_addresses.jsp" />
	</div>

	<div class="section">
		<liferay-util:include page="/html/portlet/directory/common/websites.jsp" />
	</div>

	<div class="section entity-phones">
		<liferay-util:include page="/html/portlet/directory/user/phone_numbers.jsp" />
	</div>

	<div class="section">
		<liferay-util:include page="/html/portlet/directory/user/instant_messenger.jsp" />
	</div>

	<div class="section">
		<liferay-util:include page="/html/portlet/directory/user/social_network.jsp" />
	</div>

	<div class="section">
		<liferay-util:include page="/html/portlet/directory/user/sms.jsp" />
	</div>

	<div class="section">
		<liferay-util:include page="/html/portlet/directory/user/comments.jsp" />
	</div>
</div>