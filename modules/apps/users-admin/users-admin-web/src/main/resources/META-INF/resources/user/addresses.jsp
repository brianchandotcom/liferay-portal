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

long selContactId = (selUser != null) ? selUser.getContactId() : 0;

request.setAttribute("contact_information.jsp-className", Contact.class.getName());
request.setAttribute("contact_information.jsp-classPK", selContactId);
request.setAttribute("contact_information.jsp-mvcActionPath", "/users_admin/update_user_contact_information");
%>

<aui:input name="classPK" type="hidden" value="<%= String.valueOf(selContactId) %>" />

<div class="sheet-section">
	<liferay-util:include page="/common/addresses.jsp" servletContext="<%= application %>">
		<liferay-util:param name="emptyResultsMessage" value="this-user-does-not-have-any-addresses" />
	</liferay-util:include>
</div>