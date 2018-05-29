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
CommerceNotificationQueueEntriesDisplayContext commerceNotificationQueueEntriesDisplayContext = (CommerceNotificationQueueEntriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CommerceNotificationQueueEntry commerceNotificationQueueEntry = (CommerceNotificationQueueEntry)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= commerceNotificationQueueEntriesDisplayContext.hasPermission(CommerceNotificationActionKeys.RESEND_COMMERCE_NOTIFICATION_QUEUE_ENTRY) %>">
		<portlet:actionURL name="editCommerceNotificationQueueEntry" var="resendURL">
			<portlet:param name="<%= Constants.CMD %>" value="resend" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceNotificationQueueEntryId" value="<%= String.valueOf(commerceNotificationQueueEntry.getCommerceNotificationQueueEntryId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message="resend"
			url="<%= resendURL %>"
		/>
	</c:if>

	<c:if test="<%= commerceNotificationQueueEntriesDisplayContext.hasPermission(CommerceNotificationActionKeys.DELETE_COMMERCE_NOTIFICATION_QUEUE_ENTRY) %>">
		<portlet:actionURL name="editCommerceNotificationQueueEntry" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceNotificationQueueEntryId" value="<%= String.valueOf(commerceNotificationQueueEntry.getCommerceNotificationQueueEntryId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>