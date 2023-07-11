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
List<RSSFeed> rssFeeds = rssDisplayContext.getRSSFeeds();

Map<String, Object> contextObjects = HashMapBuilder.<String, Object>put(
	"rssDisplayContext", rssDisplayContext
).build();

if (rssFeeds.isEmpty()) {
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
}
%>

			<c:choose>
				<c:when test="<%= rssFeeds.isEmpty() %>">
					<clay:alert
						displayType="info"
					>
						<liferay-ui:message key="this-application-is-not-visible-to-users-yet" />

						<c:if test="<%= rssDisplayContext.isShowConfigurationLink() %>">
								<clay:button
									cssClass="align-text-bottom border-0 p-0"
									displayType="link"
									label="select-at-least-one-valid-rss-feed-to-make-it-visible"
									onClick="<%= portletDisplay.getURLConfigurationJS() %>"
									small="<%= true %>"
								/>
						</c:if>
					</clay:alert>
				</c:when>
				<c:otherwise>
					<liferay-ddm:template-renderer
						className="<%= RSSFeed.class.getName() %>"
						contextObjects="<%= contextObjects %>"
						displayStyle="<%= rssPortletInstanceConfiguration.displayStyle() %>"
						displayStyleGroupId="<%= rssDisplayContext.getDisplayStyleGroupId() %>"
						entries="<%= rssFeeds %>"
					>
						<% for (int i = 0; i < rssFeeds.size(); i++) { RSSFeed
						rssFeed = rssFeeds.get(i); boolean last = false; if (i
						== (rssFeeds.size() - 1)) { last = true; } SyndFeed
						syndFeed = rssFeed.getSyndFeed(); %> <%@ include file="/feed.jspf" %> <%
							}
%>

					</liferay-ddm:template-renderer>
				</c:otherwise>
			</c:choose></String,
		></String,
	></RSSFeed
>