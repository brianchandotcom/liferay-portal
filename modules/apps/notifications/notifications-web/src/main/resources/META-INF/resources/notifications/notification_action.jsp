<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

<c:choose>
	<c:when test="<%= notificationActionIconList %>">
		<liferay-ui:icon-list>
			<%@ include file="/notifications/notification_action_icon_body.jspf" %>
		</liferay-ui:icon-list>
	</c:when>
	<c:otherwise>
		<liferay-ui:icon-menu
			direction="left-side"
			icon="<%= StringPool.BLANK %>"
			markupView="lexicon"
			message="actions"
			showWhenSingleIcon="<%= true %>"
		>
			<%@ include file="/notifications/notification_action_icon_body.jspf" %>
		</liferay-ui:icon-menu>
	</c:otherwise>
</c:choose>