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

int userNotificationEventsCount = UserNotificationEventLocalServiceUtil.getDeliveredUserNotificationEventsCount(themeDisplay.getUserId(), UserNotificationDeliveryConstants.TYPE_WEBSITE, true);
%>

<aui:nav-bar markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item
			href="<%= portletURL %>"
			label="notifications-list"
			selected="<%= Boolean.TRUE %>"
		/>
	</aui:nav>
</aui:nav-bar>

<liferay-frontend:management-bar
	includeCheckBox="<%= userNotificationEventsCount > 0 %>"
	searchContainerId="userNotificationEvents"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"descriptive"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="descriptive"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-action-buttons>

		<%
		String taglibURL = "javascript:" + renderResponse.getNamespace() + "markAsRead();";
		%>

		<liferay-frontend:management-bar-button href="<%= taglibURL %>" iconCssClass="icon-remove" label="mark-as-read" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<aui:form action="<%= portletURL.toString() %>" cssClass="row" method="get" name="fm">
		<div class="user-notifications">
			<liferay-ui:search-container
				id="userNotificationEvents"
				rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
				searchContainer='<%= new SearchContainer(renderRequest, null, null, "cur", SearchContainer.DEFAULT_DELTA, portletURL, null, "no-groups-were-found") %>'
				total="<%= userNotificationEventsCount %>"
			>
				<liferay-ui:search-container-results
					results="<%= UserNotificationEventLocalServiceUtil.getDeliveredUserNotificationEvents(themeDisplay.getUserId(), UserNotificationDeliveryConstants.TYPE_WEBSITE, true, searchContainer.getStart(), searchContainer.getEnd()) %>"
				/>

				<liferay-ui:search-container-row
					className="com.liferay.portal.model.UserNotificationEvent"
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