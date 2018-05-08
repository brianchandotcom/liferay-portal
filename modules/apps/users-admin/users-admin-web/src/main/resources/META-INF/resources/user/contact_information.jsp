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
User selUser = (User)request.getAttribute(UsersAdminWebKeys.SELECTED_USER);

Contact selContact = null;

if (selUser != null) {
	selContact = selUser.getContact();
}

request.setAttribute("user.selContact", selContact);
request.setAttribute("user.selUser", selUser);

request.setAttribute("emailAddresses.className", Contact.class.getName());
request.setAttribute("phones.className", Contact.class.getName());
request.setAttribute("websites.className", Contact.class.getName());

if (selContact != null) {
	request.setAttribute("emailAddresses.classPK", selContact.getContactId());
	request.setAttribute("phones.classPK", selContact.getContactId());
	request.setAttribute("websites.classPK", selContact.getContactId());
}
else {
	request.setAttribute("emailAddresses.classPK", 0L);
	request.setAttribute("phones.classPK", 0L);
	request.setAttribute("websites.classPK", 0L);
}
%>

<div class="sheet-section">
	<h3 class="sheet-subtitle"><liferay-ui:message key="phone-numbers" /></h3>

	<liferay-util:include page="/common/phone_numbers.jsp" servletContext="<%= application %>" />
</div>

<div class="sheet-section">
	<h3 class="sheet-subtitle"><liferay-ui:message key="additional-email-addresses" /></h3>

	<liferay-util:include page="/common/additional_email_addresses.jsp" servletContext="<%= application %>" />
</div>

<div class="sheet-section">
	<h3 class="sheet-subtitle"><liferay-ui:message key="websites" /></h3>

	<liferay-util:include page="/common/websites.jsp" servletContext="<%= application %>" />
</div>

<div class="sheet-section">
	<h3 class="sheet-subtitle"><liferay-ui:message key="instant-messenger" /></h3>

	<liferay-util:include page="/user/instant_messenger.jsp" servletContext="<%= application %>" />
</div>

<div class="sheet-section">
	<h3 class="sheet-subtitle"><liferay-ui:message key="sms" /></h3>

	<liferay-util:include page="/user/sms.jsp" servletContext="<%= application %>" />
</div>

<div class="sheet-section">
	<h3 class="sheet-subtitle"><liferay-ui:message key="social-network" /></h3>

	<liferay-util:include page="/user/social_network.jsp" servletContext="<%= application %>" />
</div>

<div class="sheet-section">
	<h3 class="sheet-subtitle"><liferay-ui:message key="openid" /></h3>

	<liferay-util:include page="/user/openid.jsp" servletContext="<%= application %>" />
</div>