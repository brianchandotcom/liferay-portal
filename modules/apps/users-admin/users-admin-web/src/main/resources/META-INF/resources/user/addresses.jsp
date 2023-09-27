<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

<clay:sheet-section>
	<liferay-util:include page="/common/addresses.jsp" servletContext="<%= application %>">
		<liferay-util:param name="emptyResultsMessage" value="this-user-does-not-have-any-addresses" />
	</liferay-util:include>
</clay:sheet-section>