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
int status = GetterUtil.getInteger(request.getAttribute(UsersAdminWebKeys.STATUS), WorkflowConstants.STATUS_APPROVED);
%>

<liferay-portlet:resourceURL id="/users_admin/export_users" var="exportURL">
	<liferay-portlet:param name="status" value="<%= String.valueOf(status) %>" />
</liferay-portlet:resourceURL>

<liferay-util:buffer
	var="onClickFn"
>
	if (confirm('<liferay-ui:message key="warning-this-csv-file-contains-user-supplied-inputs" unicode="<%= true %>" />')) {
		submitForm(document.hrefFm, '<%= exportURL + "&compress=0&etag=0&strip=0" %>');
	}
</liferay-util:buffer>

<liferay-ui:icon
	message="export-users"
	method="get"
	onClick="<%= onClickFn %>"
	url="javascript:;"
/>