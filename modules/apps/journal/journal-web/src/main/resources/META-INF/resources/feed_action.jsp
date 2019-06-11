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

JournalFeed feed = (JournalFeed)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= JournalFeedPermission.contains(permissionChecker, feed, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editFeedURL">
			<portlet:param name="mvcPath" value="/edit_feed.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(feed.getGroupId()) %>" />
			<portlet:param name="feedId" value="<%= feed.getFeedId() %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editFeedURL %>"
		/>
	</c:if>

	<c:if test="<%= JournalFeedPermission.contains(permissionChecker, feed, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= JournalFeed.class.getName() %>"
			modelResourceDescription="<%= feed.getName() %>"
			resourcePrimKey="<%= String.valueOf(feed.getId()) %>"
			var="permissionsFeedURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= permissionsFeedURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= JournalFeedPermission.contains(permissionChecker, feed, ActionKeys.DELETE) %>">
		<portlet:actionURL name="/journal/delete_feeds" var="deleteFeedURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(feed.getGroupId()) %>" />
			<portlet:param name="deleteFeedId" value="<%= feed.getFeedId() %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteFeedURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>