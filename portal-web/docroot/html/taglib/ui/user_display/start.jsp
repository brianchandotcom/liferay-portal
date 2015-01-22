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

<%
if (Validator.isNull(url) && (userDisplay != null)) {
	url = userDisplay.getDisplayURL(themeDisplay);
}

if (isAuthor) {
	imageCssClass += " author";
}
%>

<div class="display-style-<%= displayStyle %> taglib-user-display">

	<%
	String taglibSrc = null;

	if (userDisplay != null) {
		taglibSrc = userDisplay.getPortraitURL(themeDisplay);
	}
	else {
		taglibSrc = UserConstants.getPortraitURL(themeDisplay.getPathImage(), true, 0, null);
	}
	%>

	<aui:a href="<%= url %>">
		<span class="user-profile-image">
			<span class="avatar <%= imageCssClass %>" style="background-image: url('<%= HtmlUtil.escape(taglibSrc) %>')"></span>
		</span>

		<c:if test="<%= showUserName %>">
			<span class="user-name">
				<%= (userDisplay != null) ? HtmlUtil.escape(userDisplay.getFullName()) : HtmlUtil.escape(userName) %>
			</span>
		</c:if>
	</aui:a>

	<c:if test="<%= showUserDetails %>">
		<div class="user-details">
	</c:if>