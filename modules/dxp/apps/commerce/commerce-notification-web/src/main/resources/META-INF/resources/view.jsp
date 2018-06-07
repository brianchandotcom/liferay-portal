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
String notificationNavigationItem = ParamUtil.getString(request, "notificationNavigationItem", "view-all-notification-queue-entries");

CommerceNotificationQueueEntriesDisplayContext commerceNotificationQueueEntriesDisplayContext = (CommerceNotificationQueueEntriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

PortletURL portletURL = commerceNotificationQueueEntriesDisplayContext.getPortletURL();

portletURL.setParameter("notificationNavigationItem", notificationNavigationItem);
%>

<%@ include file="/navbar.jspf" %>

<liferay-frontend:management-bar
	searchContainerId="commerceNotificationQueueEntries"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceNotificationQueueEntriesDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceNotificationQueueEntriesDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"priority"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="list"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<liferay-ui:search-container
		id="commerceNotificationQueueEntries"
		searchContainer="<%= commerceNotificationQueueEntriesDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.notification.model.CommerceNotificationQueueEntry"
			keyProperty="commerceNotificationQueueEntryId"
			modelVar="commerceNotificationQueueEntry"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="from"
				property="fromName"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="to"
				property="toName"
			/>

			<liferay-ui:search-container-column-text
				name="type"
				value="<%= HtmlUtil.escape(commerceNotificationQueueEntriesDisplayContext.getCommerceNotificationType(commerceNotificationQueueEntry.getCommerceNotificationTemplateId())) %>"
			/>

			<liferay-ui:search-container-column-text
				name="sent"
			>
				<c:choose>
					<c:when test="<%= commerceNotificationQueueEntry.isSent() %>">
						<liferay-ui:icon
							cssClass="commerce-admin-icon-check"
							icon="check"
							markupView="lexicon"
						/>
					</c:when>
					<c:otherwise>
						<liferay-ui:icon
							cssClass="commerce-admin-icon-times"
							icon="times"
							markupView="lexicon"
						/>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				property="priority"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action-column"
				path="/notification_queue_entry_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>