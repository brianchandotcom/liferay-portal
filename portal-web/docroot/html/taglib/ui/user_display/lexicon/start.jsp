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

<div class="profile-header">
	<div class="nameplate">
		<div class="nameplate-field">
			<div class="user-icon <%= userIconCssClass %> user-icon-success">
				<img alt="" class="img-responsive <%= imageCssClass %>" src="<%= HtmlUtil.escape(taglibSrc) %>">
			</div>
		</div>
		<c:if test="<%= showUserName %>">
			<div class="nameplate-content">
				<div class="heading4">
					<c:choose>
						<c:when test="<%= showLink %>">
							<aui:a href="<%= url %>">
								<%= (userDisplay != null) ? HtmlUtil.escape(userDisplay.getFullName()) : HtmlUtil.escape(userName) %>
							</aui:a>
						</c:when>
						<c:otherwise>
							<%= (userDisplay != null) ? HtmlUtil.escape(userDisplay.getFullName()) : HtmlUtil.escape(userName) %>
						</c:otherwise>
					</c:choose>
				</div>
		</c:if>

		<c:if test="<%= showUserDetails %>">
			<div class="nameplate-content">
		</c:if>