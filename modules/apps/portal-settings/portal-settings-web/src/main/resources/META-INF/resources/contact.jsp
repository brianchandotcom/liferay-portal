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
request.setAttribute("addresses.className", Company.class.getName());
request.setAttribute("emailAddresses.className", Company.class.getName());
request.setAttribute("phones.className", Company.class.getName());
request.setAttribute("websites.className", Company.class.getName());

request.setAttribute("addresses.classPK", company.getCompanyId());
request.setAttribute("emailAddresses.classPK", company.getCompanyId());
request.setAttribute("phones.classPK", company.getCompanyId());
request.setAttribute("websites.classPK", company.getCompanyId());
%>

<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

<clay:sheet-section>
	<h3 class="sheet-subtitle"><liferay-ui:message key="addresses" /></h3>

	<%@ include file="/addresses.jsp" %>
</clay:sheet-section>

<clay:sheet-section>
	<h3 class="sheet-subtitle"><liferay-ui:message key="phone-numbers" /></h3>

	<%@ include file="/phone_numbers.jsp" %>
</clay:sheet-section>

<clay:sheet-section>
	<h3 class="sheet-subtitle"><liferay-ui:message key="additional-email-addresses" /></h3>

	<%@ include file="/additional_email_addresses.jsp" %>
</clay:sheet-section>

<clay:sheet-section>
	<h3 class="sheet-subtitle"><liferay-ui:message key="websites" /></h3>

	<%@ include file="/websites.jsp" %>
</clay:sheet-section>