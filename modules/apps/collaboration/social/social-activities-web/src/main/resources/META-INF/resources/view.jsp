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
SocialActivityQueryHelper socialActivityQueryHelper = (SocialActivityQueryHelper)request.getAttribute(SocialActivitiesWebKeys.SOCIAL_ACTIVITY_QUERY_HELPER);
Group group = GroupLocalServiceUtil.getGroup(scopeGroupId);
%>

<liferay-ui:social-activities
	activities="<%= socialActivityQueryHelper.getSocialActivities(group, 0, socialActivitiesDisplayContext.getMax()) %>"
	feedDisplayStyle="<%= socialActivitiesDisplayContext.getRSSDisplayStyle() %>"
	feedEnabled="<%= socialActivitiesDisplayContext.isRSSEnabled() %>"
	feedResourceURL="<%= socialActivitiesDisplayContext.getRSSResourceURL() %>"
	feedTitle="<%= socialActivitiesDisplayContext.getTaglibFeedTitle() %>"
	feedType="<%= socialActivitiesDisplayContext.getRSSFeedType() %>"
	feedURLMessage="<%= socialActivitiesDisplayContext.getTaglibFeedTitle() %>"
/>