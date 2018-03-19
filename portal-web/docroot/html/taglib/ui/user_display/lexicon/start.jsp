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

<liferay-util:buffer
	var="html"
>
	<liferay-ui:user-portrait
		imageCssClass="<%= imageCssClass %>"
		user="<%= userDisplay %>"
		userName="<%= (userDisplay != null) ? userDisplay.getFullName() : userName %>"
	/>
</liferay-util:buffer>

<c:choose>
	<c:when test="<%= showUserDetails || showUserName %>">
		<div class="profile-header">
			<div class="nameplate">
				<div class="nameplate-field">
					<%= html %>
				</div>

				<c:if test="<%= showUserName %>">
					<div class="nameplate-content">
						<div class="heading4">

							<%
							if (Validator.isNull(url) && (userDisplay != null)) {
								url = userDisplay.getDisplayURL(themeDisplay);
							}
							%>

							<aui:a href="<%= showLink ? url : null %>">
								<%= (userDisplay != null) ? HtmlUtil.escape(userDisplay.getFullName()) : HtmlUtil.escape(userName) %>
							</aui:a>
						</div>
					</div>
				</c:if>

				<c:if test="<%= showUserDetails %>">
					<div class="nameplate-content">
				</c:if>
	</c:when>
	<c:otherwise>
		<%= html %>
	</c:otherwise>
</c:choose>