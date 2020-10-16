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

<%@ include file="/user_management_bar/init.jsp" %>

<%
int notificationsCount = (int)request.getAttribute("liferay-commerce:user-management-bar:notificationsCount");
boolean showNotifications = (boolean)request.getAttribute("liferay-commerce:user-management-bar:showNotifications");
%>

<c:choose>
	<c:when test="<%= themeDisplay.isSignedIn() %>">
		<span class="user-avatar-link">
			<a class="text-default" href="<%= HtmlUtil.escapeHREF((String)request.getAttribute("liferay-commerce:user-management-bar:href")) %>">
				<c:if test="<%= themeDisplay.isImpersonated() %>">
					<aui:icon image="asterisk" markupView="lexicon" />
				</c:if>

				<span class="user-avatar-image">
					<liferay-ui:user-portrait
						user="<%= user %>"
					/>
				</span>
				<span class="user-full-name">
					<%= HtmlUtil.escape(user.getFullName()) %>
				</span>
			</a>

			<c:if test="<%= showNotifications && (notificationsCount > 0) %>">

				<%
				PortletURL notificationsURL = PortletProviderUtil.getPortletURL(request, UserNotificationEvent.class.getName(), PortletProvider.Action.VIEW);
				%>

				<aui:a href="<%= (notificationsURL != null) ? notificationsURL.toString() : null %>">
					<span class="panel-notifications-count sticker sticker-right sticker-rounded sticker-sm sticker-warning"><%= notificationsCount %></span>
				</aui:a>
			</c:if>
		</span>
	</c:when>
	<c:otherwise>
		<span class="sign-in text-default" role="presentation">
			<aui:a
				cssClass="sign-in text-default"
				data='<%=
					HashMapBuilder.<String, Object>put(
						"redirect", String.valueOf(PortalUtil.isLoginRedirectRequired(request))
					).build()
				%>'
				href="<%= themeDisplay.getURLSignIn() %>"
				iconCssClass="icon-user"
				label="sign-in"
			/>
		</span>
	</c:otherwise>
</c:choose>