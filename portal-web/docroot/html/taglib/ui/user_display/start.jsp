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

<%@ include file="/html/taglib/ui/user_display/init.jsp" %>

<div class="display-style-<%= displayStyle %> taglib-user-display">

	<%
	if (Validator.isNull(url) && (userDisplay != null)) {
		url = userDisplay.getDisplayURL(themeDisplay);
	}
	%>

	<aui:a href="<%= url %>">
		<liferay-ui:user-portrait
			imageCssClass="<%= imageCssClass %>"
			user="<%= userDisplay %>"
			userName="<%= (userDisplay != null) ? userDisplay.getFullName() : userName %>"
		/>

		<c:if test="<%= showUserName %>">
			<span class="user-name">
				<%= (userDisplay != null) ? HtmlUtil.escape(userDisplay.getFullName()) : HtmlUtil.escape(userName) %>
			</span>
		</c:if>
	</aui:a>

	<c:if test="<%= showUserDetails %>">
		<div class="user-details">
	</c:if>