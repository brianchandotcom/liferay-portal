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
User user2 = (User)request.getAttribute(ContactsWebKeys.CONTACTS_USER);

user2 = user2.toEscapedModel();

request.setAttribute("view_user.jsp-user", user2);
%>

<aui:row>
	<aui:col cssClass="contacts-container" width="<%= 100 %>">
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
	</aui:col>
</aui:row>