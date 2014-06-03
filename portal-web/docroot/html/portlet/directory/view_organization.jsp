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
Organization organization = (Organization)request.getAttribute(WebKeys.ORGANIZATION);

long organizationId = BeanParamUtil.getLong(organization, request, "organizationId");

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

<liferay-util:include page="/html/portlet/directory/tabs1.jsp" />

<div class="organization-information">
	<div class="section entity-details">
		<liferay-util:include page="/html/portlet/directory/organization/details.jsp" />
	</div>

	<div class="section">
		<liferay-util:include page="/html/portlet/directory/common/additional_email_addresses.jsp" />
	</div>

	<div class="section">
		<liferay-util:include page="/html/portlet/directory/common/websites.jsp" />
	</div>

	<div class="section">
		<liferay-util:include page="/html/portlet/directory/organization/addresses.jsp" />
	</div>

	<div class="section">
		<liferay-util:include page="/html/portlet/directory/organization/phone_numbers.jsp" />
	</div>

	<div class="section">
		<liferay-util:include page="/html/portlet/directory/organization/services.jsp" />
	</div>

	<div class="section">
		<liferay-util:include page="/html/portlet/directory/organization/comments.jsp" />
	</div>
</div>