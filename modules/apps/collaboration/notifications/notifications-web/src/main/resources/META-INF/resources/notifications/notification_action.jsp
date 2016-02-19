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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

UserNotificationEvent userNotificationEvent = (UserNotificationEvent)row.getObject();

JSONObject jsonObject = JSONFactoryUtil.createJSONObject(userNotificationEvent.getPayload());

long subscriptionId = jsonObject.getLong("subscriptionId");

if (subscriptionId > 0) {
	Subscription subscription = SubscriptionLocalServiceUtil.fetchSubscription(subscriptionId);

	if (subscription == null) {
		subscriptionId = 0;
	}
}
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<c:choose>
		<c:when test="<%= userNotificationEvent.isActionRequired() %>">
			<portlet:actionURL name="deleteUserNotificationEvent" var="deleteURL">
				<portlet:param name="userNotificationEventId" value="<%= String.valueOf(userNotificationEvent.getUserNotificationEventId()) %>" />
			</portlet:actionURL>

			<liferay-ui:icon message="delete" url="<%= deleteURL.toString() %>" />
		</c:when>
		<c:otherwise>
			<portlet:actionURL name="markAsRead" var="markAsReadURL">
				<portlet:param name="userNotificationEventId" value="<%= String.valueOf(userNotificationEvent.getUserNotificationEventId()) %>" />
			</portlet:actionURL>

			<liferay-ui:icon message="mark-as-read" url="<%= markAsReadURL.toString() %>" />

			<c:if test="<%= subscriptionId > 0 %>">
				<portlet:actionURL name="unsubscribe" var="unsubscribeURL">
					<portlet:param name="subscriptionId" value="<%= String.valueOf(subscriptionId) %>" />
					<portlet:param name="userNotificationEventId" value="<%= String.valueOf(userNotificationEvent.getUserNotificationEventId()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon message="stop-receiving-notifications-from-this-asset" url="<%= unsubscribeURL.toString() %>" />
			</c:if>
		</c:otherwise>
	</c:choose>
</liferay-ui:icon-menu>