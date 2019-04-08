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
request.setAttribute("addresses.className", Account.class.getName());
request.setAttribute("emailAddresses.className", Account.class.getName());
request.setAttribute("phones.className", Account.class.getName());
request.setAttribute("websites.className", Account.class.getName());

request.setAttribute("addresses.classPK", company.getAccountId());
request.setAttribute("emailAddresses.classPK", company.getAccountId());
request.setAttribute("phones.classPK", company.getAccountId());
request.setAttribute("websites.classPK", company.getAccountId());
%>

<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

<div class="sheet-section">
	<h3 class="sheet-subtitle"><liferay-ui:message key="addresses" /></h3>

	<%@ include file="/addresses.jsp" %>
</div>

<div class="sheet-section">
	<h3 class="sheet-subtitle"><liferay-ui:message key="phone-numbers" /></h3>

	<%@ include file="/phone_numbers.jsp" %>
</div>

<div class="sheet-section">
	<h3 class="sheet-subtitle"><liferay-ui:message key="additional-email-addresses" /></h3>

	<%@ include file="/additional_email_addresses.jsp" %>
</div>

<div class="sheet-section">
	<h3 class="sheet-subtitle"><liferay-ui:message key="websites" /></h3>

	<%@ include file="/websites.jsp" %>
</div>