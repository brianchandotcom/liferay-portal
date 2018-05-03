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

<%@ include file="/instance/init.jsp" %>

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
		<portlet:renderURL var="viewURL" />

		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all", "pending", "completed"} %>'
			portletURL="<%= workflowInstanceViewDisplayContext.getViewPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= workflowInstanceViewDisplayContext.getOrderByCol() %>"
			orderByType="<%= workflowInstanceViewDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"last-activity-date", "end-date"} %>'
			portletURL="<%= workflowInstanceViewDisplayContext.getViewPortletURL() %>"
		/>

		<liferay-util:include page="/search.jsp" servletContext="<%= application %>" />
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>