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
JournalFeedsDisplayContext journalFeedsDisplayContext = new JournalFeedsDisplayContext(renderRequest, renderResponse);

JournalFeedsManagementToolbarDisplayContext journalFeedsManagementToolbarDisplayContext = new JournalFeedsManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, journalFeedsDisplayContext);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(journalFeedsDisplayContext.getRedirect());

renderResponse.setTitle(LanguageUtil.get(request, "feeds"));
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems='<%= journalDisplayContext.getNavigationBarItems("feeds") %>'
/>

<clay:management-toolbar
	displayContext="<%= journalFeedsManagementToolbarDisplayContext %>"
/>

<portlet:actionURL name="/journal/delete_feeds" var="deleteFeedsURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteFeedsURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<liferay-ui:search-container
		id="feeds"
		searchContainer="<%= journalFeedsDisplayContext.getFeedsSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.journal.model.JournalFeed"
			escapedModel="<%= true %>"
			keyProperty="feedId"
			modelVar="feed"
		>

			<%
			String editURL = StringPool.BLANK;

			if (JournalFeedPermission.contains(permissionChecker, feed, ActionKeys.UPDATE)) {
				PortletURL editFeedURL = liferayPortletResponse.createRenderURL();

				editFeedURL.setParameter("mvcPath", "/edit_feed.jsp");
				editFeedURL.setParameter("redirect", currentURL);
				editFeedURL.setParameter("groupId", String.valueOf(feed.getGroupId()));
				editFeedURL.setParameter("feedId", feed.getFeedId());

				editURL = editFeedURL.toString();
			}

			Map<String, Object> rowData = new HashMap<>();

			rowData.put("actions", journalFeedsManagementToolbarDisplayContext.getAvailableActions(feed));

			row.setData(rowData);
			%>

			<c:choose>
				<c:when test='<%= Objects.equals(journalFeedsDisplayContext.getDisplayStyle(), "descriptive") %>'>
					<liferay-ui:search-container-column-icon
						icon="rss"
						toggleRowChecker="<%= true %>"
					/>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>
						<h5>
							<aui:a href="<%= editURL %>">
								<%= feed.getName() %>
							</aui:a>
						</h5>

						<h6 class="text-default">
							<%= feed.getDescription() %>
						</h6>

						<h6 class="text-default">
							<strong><liferay-ui:message key="id" /></strong>: <%= feed.getId() %>
						</h6>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-jsp
						path="/feed_action.jsp"
					/>
				</c:when>
				<c:when test='<%= Objects.equals(journalFeedsDisplayContext.getDisplayStyle(), "list") %>'>
					<liferay-ui:search-container-column-text
						name="id"
						property="feedId"
					/>

					<liferay-ui:search-container-column-text
						href="<%= editURL %>"
						name="name"
						property="name"
						truncate="<%= true %>"
					/>

					<liferay-ui:search-container-column-text
						name="description"
						property="description"
						truncate="<%= true %>"
					/>

					<liferay-ui:search-container-column-jsp
						path="/feed_action.jsp"
					/>
				</c:when>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= journalFeedsDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<liferay-frontend:component
	componentId="<%= journalFeedsManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	module="js/FeedsManagementToolbarDefaultEventHandler.es"
/>