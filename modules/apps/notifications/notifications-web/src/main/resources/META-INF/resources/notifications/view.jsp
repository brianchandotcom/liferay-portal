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

<%
PortletURL portletURL = renderResponse.createRenderURL();
%>

<div class="container-fluid-1280">

	<%
	SearchContainer notificationsSearchContainer = new SearchContainer(renderRequest, null, null, "cur", SearchContainer.DEFAULT_DELTA, portletURL, null, "no-groups-were-found");

	List<UserNotificationEvent> userNotificationEvents = UserNotificationEventLocalServiceUtil.getDeliveredUserNotificationEvents(themeDisplay.getUserId(), UserNotificationDeliveryConstants.TYPE_WEBSITE, true, notificationsSearchContainer.getStart(), notificationsSearchContainer.getEnd());

	List<Long> newUserNotificationEventIds = new ArrayList<>();

	for (UserNotificationEvent userNotificationEvent : userNotificationEvents) {
		if (!userNotificationEvent.isArchived()) {
			newUserNotificationEventIds.add(userNotificationEvent.getUserNotificationEventId());
		}
	}
	%>

	<portlet:actionURL name="markAllAsRead" var="markAllAsReadURL">
		<portlet:param name="userNotificationEventIds" value="<%= StringUtil.merge(newUserNotificationEventIds) %>" />
	</portlet:actionURL>

	<div class="mark-all-as-read"><a class="<%= (newUserNotificationEventIds.isEmpty()) ? "hide" : "" %>" href="<%= markAllAsReadURL %>"><liferay-ui:message key="mark-as-read" /></a></div>

	<div class="user-notifications">
		<liferay-ui:search-container searchContainer="<%= notificationsSearchContainer %>">
			<liferay-ui:search-container-results
				results="<%= userNotificationEvents %>"
				total="<%= UserNotificationEventLocalServiceUtil.getDeliveredUserNotificationEventsCount(themeDisplay.getUserId(), UserNotificationDeliveryConstants.TYPE_WEBSITE, true) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.model.UserNotificationEvent"
				keyProperty="userNotificationEventId"
				modelVar="userNotificationEvent"
			>
				<%@ include file="/notifications/user_notification_entry.jspf" %>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator displayStyle="list" markupView="lexicon" />
		</liferay-ui:search-container>
	</div>
</div>