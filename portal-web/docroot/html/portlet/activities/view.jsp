<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/activities/init.jsp" %>

<%
Group group = GroupLocalServiceUtil.getGroup(scopeGroupId);

int start = 0;
int end = 10;

List<SocialActivity> activities = null;

if (group.isOrganization()) {
	activities = SocialActivityLocalServiceUtil.getOrganizationActivities(group.getOrganizationId(), start, end);
}
else if (group.isRegularSite()) {
	activities = SocialActivityLocalServiceUtil.getGroupActivities(group.getGroupId(), start, end);
}
else if (group.isUser()) {
	activities = SocialActivityLocalServiceUtil.getUserActivities(group.getClassPK(), start, end);
}

ResourceURL rssURL = liferayPortletResponse.createResourceURL();

rssURL.setCacheability(ResourceURL.FULL);
rssURL.setParameter("struts_action", "/activities/rss");

String taglibFeedTitle = LanguageUtil.format(pageContext, "subscribe-to-x's-activities", group.getDescriptiveName(locale));
String taglibFeedLinkMessage = LanguageUtil.format(pageContext, "subscribe-to-x's-activities", group.getDescriptiveName(locale));
%>

<liferay-ui:social-activities
	activities="<%= activities %>"
	feedEnabled="<%= true %>"
	feedTitle="<%= HtmlUtil.escape(taglibFeedTitle) %>"
	feedLink="<%= rssURL.toString() %>"
	feedLinkMessage="<%= HtmlUtil.escape(taglibFeedLinkMessage) %>"
/>