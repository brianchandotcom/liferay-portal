<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/user_management_bar/init.jsp" %>

<%
String href = (String)request.getAttribute("liferay-commerce:user-management-bar:href");
int notificationsCount = (int)request.getAttribute("liferay-commerce:user-management-bar:notificationsCount");
boolean showNotifications = (boolean)request.getAttribute("liferay-commerce:user-management-bar:showNotifications");
%>

<c:choose>
	<c:when test="<%= themeDisplay.isSignedIn() %>">
		<span class="user-avatar-link">
			<a class="text-default" href="<%= href %>">
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

				<aui:a href="<%= notificationsURL != null ? notificationsURL.toString() : null %>">
					<span class="panel-notifications-count sticker sticker-right sticker-rounded sticker-sm sticker-warning"><%= notificationsCount %></span>
				</aui:a>
			</c:if>
		</span>
	</c:when>
	<c:otherwise>

		<%
		Map<String, Object> anchorData = new HashMap<>();

		anchorData.put("redirect", String.valueOf(PortalUtil.isLoginRedirectRequired(request)));
		%>

		<span class="sign-in text-default" role="presentation">
			<aui:a cssClass="sign-in text-default" data="<%= anchorData %>" href="<%= themeDisplay.getURLSignIn() %>" iconCssClass="icon-user" label="sign-in" />
		</span>
	</c:otherwise>
</c:choose>