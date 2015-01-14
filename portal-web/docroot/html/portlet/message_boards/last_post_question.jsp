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

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Object[] objArray = (Object[])row.getObject();

MBMessage message = (MBMessage)objArray[0];

MBThread thread = message.getThread();

User userDisplay = UserLocalServiceUtil.fetchUserById(thread.getLastPostByUserId());
%>

<div class="user-info">
	<div class="portrait">
		<c:choose>
			<c:when test="<%= message.isAnonymous() || (userDisplay == null) %>">
				<img alt="<%= LanguageUtil.get(pageContext, "generic-portrait") %>" class="avatar" src="<%= UserConstants.getPortraitURL(themeDisplay.getPathImage(), true, 0, StringPool.BLANK) %>" width="60" /></a>
			</c:when>
			<c:otherwise>
				<a href="<%= userDisplay.getDisplayURL(themeDisplay) %>"><img alt="<%= HtmlUtil.escapeAttribute(userDisplay.getFullName()) %>" class="avatar" src="<%= userDisplay.getPortraitURL(themeDisplay) %>" width="60" /></a>
			</c:otherwise>
		</c:choose>
	</div>

	<div class="username">
		<c:choose>
			<c:when test="<%= message.isAnonymous() || (userDisplay == null) %>">
				<liferay-ui:message key="anonymous" />
			</c:when>
			<c:otherwise>
				<a href="<%= userDisplay.getDisplayURL(themeDisplay) %>"><%= HtmlUtil.escape(PortalUtil.getUserName(thread.getLastPostByUserId(), StringPool.BLANK)) %></a>
			</c:otherwise>
		</c:choose>
	</div>

	<div class="time">

		<%
		Date now = new Date();

		long lastPostAgo = now.getTime() - thread.getLastPostDate().getTime();
		%>

		<liferay-ui:icon
			image="../aui/time"
			label="<%= true %>"
			message='<%= LanguageUtil.format(pageContext, "x-ago", LanguageUtil.getTimeDescription(pageContext, lastPostAgo, true)) %>'
		/>
	</div>
</div>