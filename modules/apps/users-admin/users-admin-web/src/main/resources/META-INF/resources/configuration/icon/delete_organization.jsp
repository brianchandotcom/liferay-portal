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
Organization organization = ActionUtil.getOrganization(renderRequest);

String backURL = String.valueOf(renderRequest.getAttribute("view.jsp-backURL"));

if (Validator.isNull(backURL)) {
	backURL = UsersAdminPortletURLUtil.createParentOrganizationViewTreeURL(organization.getOrganizationId(), renderResponse);
}
%>

<liferay-util:buffer
	var="onClickFn"
>
	<portlet:namespace />deleteOrganization('<%= organization.getOrganizationId() %>', '<%= HtmlUtil.escapeJS(backURL) %>');
</liferay-util:buffer>

<liferay-ui:icon
	message="delete"
	onClick="<%= onClickFn %>"
	url="javascript:void(0);"
/>