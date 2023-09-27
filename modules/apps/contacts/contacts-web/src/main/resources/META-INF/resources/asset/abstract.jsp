<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
User user2 = (User)request.getAttribute(ContactsWebKeys.CONTACTS_USER);

user2 = user2.toEscapedModel();

request.setAttribute("view_user.jsp-user", user2);
%>

<clay:row>
	<clay:col
		cssClass="contacts-container"
	>
		<div class="lfr-contact-thumb">
			<img alt="<%= HtmlUtil.escapeAttribute(user2.getFullName()) %>" src="<%= user2.getPortraitURL(themeDisplay) %>" />
		</div>

		<div class="lfr-contact-name">
			<%= user2.getFullName() %>
		</div>

		<div class="lfr-contact-job-title">
			<%= user2.getJobTitle() %>
		</div>

		<div class="lfr-contact-extra">
			<a href="mailto:<%= user2.getEmailAddress() %>"><%= user2.getEmailAddress() %></a>
		</div>

		<liferay-util:include page="/user/view_user_information.jsp" servletContext="<%= application %>" />
	</clay:col>
</clay:row>