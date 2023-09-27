<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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