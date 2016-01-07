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

<liferay-frontend:management-bar
	includeCheckBox="<%= false %>"
>
	<liferay-frontend:management-bar-buttons>
		<c:if test="<%= !workflowInstanceViewDisplayContext.isSearch() %>">
			<liferay-frontend:management-bar-display-buttons
				displayViews="<%= workflowInstanceViewDisplayContext.getDisplayViews() %>"
				portletURL="<%= workflowInstanceViewDisplayContext.getViewPortletURL() %>"
				selectedDisplayStyle="<%= workflowInstanceViewDisplayContext.getDisplayStyle() %>"
			/>
		</c:if>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			label="<%= null %>"
		>
			<portlet:renderURL var="viewAllURL">
				<portlet:param name="navigation" value="all" />
			</portlet:renderURL>

			<liferay-frontend:management-bar-navigation-item active="<%= workflowInstanceViewDisplayContext.isNavigationAll() %>" label="all" url="<%= viewAllURL.toString() %>" />

			<portlet:renderURL var="viewPendingsURL">
				<portlet:param name="navigation" value="pending" />
			</portlet:renderURL>

			<liferay-frontend:management-bar-navigation-item active="<%= workflowInstanceViewDisplayContext.isNavigationPending() %>" label="pending" url="<%= viewPendingsURL.toString() %>" />

			<portlet:renderURL var="viewCompletedURL">
				<portlet:param name="navigation" value="completed" />
			</portlet:renderURL>

			<liferay-frontend:management-bar-navigation-item active="<%= workflowInstanceViewDisplayContext.isNavigationCompleted() %>" label="completed" url="<%= viewCompletedURL.toString() %>" />
		</liferay-frontend:management-bar-navigation>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= workflowInstanceViewDisplayContext.getOrderByCol() %>"
			orderByType="<%= workflowInstanceViewDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"last-activity-date", "end-date"} %>'
			portletURL="<%= workflowInstanceViewDisplayContext.getViewPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>