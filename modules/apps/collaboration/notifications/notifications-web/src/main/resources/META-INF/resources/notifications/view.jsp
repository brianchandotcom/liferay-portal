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
boolean actionRequired = ParamUtil.getBoolean(request, "actionRequired");

int userNotificationEventsCount = UserNotificationEventLocalServiceUtil.getDeliveredUserNotificationEventsCount(themeDisplay.getUserId(), UserNotificationDeliveryConstants.TYPE_WEBSITE, true, actionRequired);
%>

<aui:nav-bar markupView="lexicon">
	<liferay-portlet:renderURL var="viewNotificationsURL">
		<liferay-portlet:param name="actionRequired" value="<%= StringPool.FALSE %>" />
	</liferay-portlet:renderURL>

	<aui:nav cssClass="navbar-nav">
		<aui:nav-item
			href="<%= viewNotificationsURL %>"
			label="notifications-list"
			selected="<%= !actionRequired %>"
		/>
	</aui:nav>

	<liferay-portlet:renderURL var="viewRequestsURL">
		<liferay-portlet:param name="actionRequired" value="<%= StringPool.TRUE %>" />
	</liferay-portlet:renderURL>

	<aui:nav cssClass="navbar-nav">
		<aui:nav-item
			href="<%= viewRequestsURL %>"
			label="requests-list"
			selected="<%= actionRequired %>"
		/>
	</aui:nav>
</aui:nav-bar>

<liferay-frontend:management-bar
	disabled="<%= userNotificationEventsCount == 0 %>"
	includeCheckBox="<%= true %>"
	searchContainerId="userNotificationEvents"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"descriptive"} %>'
			portletURL="<%= currentURLObj %>"
			selectedDisplayStyle="descriptive"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "markAsRead();" %>' iconCssClass="icon-remove" label="mark-as-read" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280 main-content-body">
	<aui:form action="<%= currentURL %>" cssClass="row" method="get" name="fm">
		<div class="user-notifications">
			<liferay-ui:search-container
				emptyResultsMessage='<%= actionRequired ? "you-do-not-have-any-requests" : "you-do-not-have-any-notifications" %>'
				id="userNotificationEvents"
				iteratorURL="<%= currentURLObj %>"
				rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
				total="<%= userNotificationEventsCount %>"
			>
				<liferay-ui:search-container-results
					results="<%= UserNotificationEventLocalServiceUtil.getDeliveredUserNotificationEvents(themeDisplay.getUserId(), UserNotificationDeliveryConstants.TYPE_WEBSITE, true, actionRequired, searchContainer.getStart(), searchContainer.getEnd()) %>"
				/>

				<liferay-ui:search-container-row
					className="com.liferay.portal.kernel.model.UserNotificationEvent"
					keyProperty="userNotificationEventId"
					modelVar="userNotificationEvent"
				>
					<%@ include file="/notifications/user_notification_entry.jspf" %>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator displayStyle="descriptive" markupView="lexicon" />
			</liferay-ui:search-container>
		</div>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />markAsRead() {
		var form = AUI.$(document.<portlet:namespace />fm);

		form.attr('method', 'post');

		submitForm(form, '<portlet:actionURL name="markAllAsRead" />');
	}
</aui:script>