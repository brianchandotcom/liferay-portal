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

<%@ include file="/html/portlet/rss/init.jsp" %>

<%
long portletDisplayDDMTemplateId = PortletDisplayTemplateUtil.getPortletDisplayTemplateDDMTemplateId(rssDisplayContext.getDisplayStyleGroupId(), rssDisplayContext.getDisplayStyle());

List<RSSFeedContext> rssFeedContexts = rssDisplayContext.getRSSFeedContexts();
%>

<c:choose>
	<c:when test="<%= portletDisplayDDMTemplateId > 0 %>">
		<%= PortletDisplayTemplateUtil.renderDDMTemplate(request, response, portletDisplayDDMTemplateId, rssFeedContexts) %>
	</c:when>
	<c:otherwise>

		<%
		for (int i = 0; i < rssFeedContexts.size(); i++) {
			RSSFeedContext rssFeedContext = rssFeedContexts.get(i);

			boolean last = false;

			if (i == (rssFeedContexts.size() - 1)) {
				last = true;
			}

			SyndFeed syndFeed = rssFeedContext.getSyndFeed();
		%>

			<%@ include file="/html/portlet/rss/feed.jspf" %>

		<%
		}
		%>

	</c:otherwise>
</c:choose>