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

<style type="text/css">
	#impersonate-user-icon {
		color: #272833;
	}

	#impersonate-user-icon .lexicon-icon {
		margin-top: -.125rem;
	}

	#impersonate-user-sticker {
		bottom: -.4rem;
		color: #000;
		font-size: .6rem;
		height: 1.2rem;
		right: -0.4rem;
		width: 1.2rem;
	}

	#personal-menu-icon-wrapper .lexicon-icon {
		margin-top: -.25rem;
	}
</style>

<li class="control-menu-nav-item">
	<span class="user-avatar-link">
		<liferay-product-navigation:personal-menu
			expanded="<%= true %>"
			user="<%= user %>"
		/>

		<%
		int notificationsCount = GetterUtil.getInteger(request.getAttribute(PersonalMenuWebKeys.NOTIFICATIONS_COUNT));
		%>

		<c:if test="<%= notificationsCount > 0 %>">

			<%
			String notificationsURL = PersonalApplicationURLUtil.getPersonalApplicationURL(request, PortletProviderUtil.getPortletId(UserNotificationEvent.class.getName(), PortletProvider.Action.VIEW));
			%>

			<aui:a href="<%= (notificationsURL != null) ? notificationsURL : null %>">
				<span class="badge badge-danger panel-notifications-count">
					<span class="badge-item badge-item-expand"><%= notificationsCount %></span>
				</span>
			</aui:a>
		</c:if>
	</span>
</li>