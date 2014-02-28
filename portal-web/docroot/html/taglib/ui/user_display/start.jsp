<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
%>

<div class="taglib-user-display display-style-<%= displayStyle %>">

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
		<c:choose>
			<c:when test="<%= displayStyle == 3 %>">
				<c:choose>
					<c:when test="<%= BrowserSnifferUtil.isIe(request) && (BrowserSnifferUtil.getMajorVersion(request) < 9) %>">
						<img alt="" class="user-profile-image" src="<%= HtmlUtil.escape(taglibSrc) %>" style="height: <%= height %>px; width: <%= width %>px;" />
					</c:when>
					<c:otherwise>
						<span class="user-profile-image" style="background-image: url('<%= HtmlUtil.escape(taglibSrc) %>'); background-size: <%= height %>px <%= width %>px; height: <%= height %>px; width: <%= width %>px;"></span>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<span class="user-profile-image">
					<img alt="" class="avatar" src="<%= HtmlUtil.escape(taglibSrc) %>" style="height: <%= height %>px; width: <%= width %>px;" />
				</span>
			</c:otherwise>
		</c:choose>

		<span class="user-name">
			<%= (userDisplay != null) ? HtmlUtil.escape(userDisplay.getFullName()) : HtmlUtil.escape(userName) %>
		</span>
	</aui:a>

	<div class="user-details">