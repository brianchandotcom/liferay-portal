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
String notificationNavigationItem = ParamUtil.getString(request, "notificationNavigationItem", "view-all-notification-queues");

ServletContext commerceAdminServletContext = (ServletContext)request.getAttribute(CommerceAdminWebKeys.COMMERCE_ADMIN_SERVLET_CONTEXT);

CommerceNotificationQueuesDisplayContext commerceNotificationQueuesDisplayContext = (CommerceNotificationQueuesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

PortletURL portletURL = commerceNotificationQueuesDisplayContext.getPortletURL();

portletURL.setParameter("notificationNavigationItem", notificationNavigationItem);
%>

<liferay-util:include page="/navbar.jsp" servletContext="<%= commerceAdminServletContext %>">
	<liferay-util:param name="commerceAdminModuleKey" value="<%= commerceAdminModuleKey %>" />
</liferay-util:include>

<%@ include file="/navbar.jspf" %>

<liferay-frontend:management-bar
	searchContainerId="commerceNotificationQueues"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceNotificationQueuesDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceNotificationQueuesDisplayContext.getOrderByType() %>"
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
		id="commerceNotificationQueues"
		searchContainer="<%= commerceNotificationQueuesDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.notification.model.CommerceNotificationQueue"
			keyProperty="commerceNotificationQueueId"
			modelVar="commerceNotificationQueue"
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
				cssClass="table-cell-content"
				name="sent"
				value='<%= LanguageUtil.get(request, commerceNotificationQueue.isSent() ? "yes" : "no") %>'
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="priority"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action-column"
				path="/notification_queue_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>