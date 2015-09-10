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
Group group = GroupLocalServiceUtil.getGroup(scopeGroupId);

List<SocialActivity> socialActivities = null;

if (group.isOrganization()) {
	socialActivities = SocialActivityLocalServiceUtil.getOrganizationActivities(group.getOrganizationId(), 0, max);
}
else if (group.isRegularSite()) {
	socialActivities = SocialActivityLocalServiceUtil.getGroupActivities(group.getGroupId(), 0, max);
}
else if (group.isUser()) {
	socialActivities = SocialActivityLocalServiceUtil.getUserActivities(group.getClassPK(), 0, max);
}

String feedTitle = LanguageUtil.format(request, "x's-activities", HtmlUtil.escape(group.getDescriptiveName(locale)), false);

ResourceURL rssURL = renderResponse.createResourceURL();

rssURL.setParameter("feedTitle", feedTitle);
rssURL.setResourceID("rss");

String taglibFeedTitle = LanguageUtil.format(request, "subscribe-to-x's-activities", HtmlUtil.escape(group.getDescriptiveName(locale)), false);
%>

<liferay-ui:social-activities
	activities="<%= socialActivities %>"
	feedDisplayStyle="<%= rssDisplayStyle %>"
	feedEnabled="<%= enableRSS %>"
	feedLinkMessage="<%= taglibFeedTitle %>"
	feedResourceURL="<%= rssURL %>"
	feedTitle="<%= taglibFeedTitle %>"
	feedType="<%= rssFeedType %>"
/>