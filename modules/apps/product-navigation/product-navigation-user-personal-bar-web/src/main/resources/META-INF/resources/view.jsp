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

<c:choose>
	<c:when test="<%= themeDisplay.isSignedIn() %>">
		<span class="user-avatar-link">
			<liferay-util:buffer
				var="userAvatar"
			>
				<span class="sticker sticker-lg">
					<span class="inline-item">
						<liferay-ui:user-portrait
							cssClass="sticker-lg"
							user="<%= user %>"
						/>
					</span>

					<c:if test="<%= themeDisplay.isImpersonated() %>">
						<span class="sticker sticker-bottom-right sticker-circle sticker-outside sticker-sm sticker-user-icon">
							<aui:icon image="user" markupView="lexicon" />
						</span>
					</c:if>
				</span>
			</liferay-util:buffer>

			<liferay-product-navigation:personal-menu
				expanded="<%= true %>"
				label="<%= userAvatar %>"
			/>

			<%
			int notificationsCount = GetterUtil.getInteger(request.getAttribute(ProductNavigationUserPersonalBarWebKeys.NOTIFICATIONS_COUNT));
			%>

			<c:if test="<%= notificationsCount > 0 %>">

				<%
				String notificaitonsPortletId = PortletProviderUtil.getPortletId(UserNotificationEvent.class.getName(), PortletProvider.Action.VIEW);

				String notificationsURL = PersonalApplicationURLUtil.getPersonalApplicationURL(request, notificaitonsPortletId);
				%>

				<aui:a href="<%= (notificationsURL != null) ? notificationsURL : null %>">
					<span class="badge badge-danger panel-notifications-count">
						<span class="badge-item badge-item-expand"><%= notificationsCount %></span>
					</span>
				</aui:a>
			</c:if>
		</span>
	</c:when>
	<c:otherwise>

		<%
		Map<String, Object> anchorData = new HashMap<String, Object>();

		anchorData.put("redirect", String.valueOf(PortalUtil.isLoginRedirectRequired(request)));
		%>

		<span class="sign-in text-default" role="presentation">
			<aui:icon cssClass="sign-in text-default" data="<%= anchorData %>" image="user" label="sign-in" markupView="lexicon" url="<%= themeDisplay.getURLSignIn() %>" />
		</span>
	</c:otherwise>
</c:choose>