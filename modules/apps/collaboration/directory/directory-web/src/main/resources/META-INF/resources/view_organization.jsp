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
long organizationId = ParamUtil.getLong(request, "organizationId");

Organization organization = OrganizationServiceUtil.fetchOrganization(organizationId);

request.setAttribute(WebKeys.ORGANIZATION, organization);
request.setAttribute("addresses.className", Organization.class.getName());
request.setAttribute("addresses.classPK", organizationId);
request.setAttribute("emailAddresses.className", Organization.class.getName());
request.setAttribute("emailAddresses.classPK", organizationId);
request.setAttribute("phones.className", Organization.class.getName());
request.setAttribute("phones.classPK", organizationId);
request.setAttribute("websites.className", Organization.class.getName());
request.setAttribute("websites.classPK", organizationId);
%>

<liferay-util:include page="/tabs1.jsp" servletContext="<%= application %>">
	<liferay-util:param name="showSearch" value="<%= Boolean.FALSE.toString() %>" />
</liferay-util:include>

<div class="organization-information">
	<div class="entity-details section">
		<liferay-util:include page="/organization/details.jsp" servletContext="<%= application %>" />
	</div>

	<div class="section">
		<liferay-util:include page="/common/additional_email_addresses.jsp" servletContext="<%= application %>" />
	</div>

	<div class="section">
		<liferay-util:include page="/common/websites.jsp" servletContext="<%= application %>" />
	</div>

	<div class="section">
		<liferay-util:include page="/organization/addresses.jsp" servletContext="<%= application %>" />
	</div>

	<div class="section">
		<liferay-util:include page="/organization/phone_numbers.jsp" servletContext="<%= application %>" />
	</div>

	<div class="section">
		<liferay-util:include page="/organization/services.jsp" servletContext="<%= application %>" />
	</div>

	<div class="section">
		<liferay-util:include page="/organization/comments.jsp" servletContext="<%= application %>" />
	</div>
</div>