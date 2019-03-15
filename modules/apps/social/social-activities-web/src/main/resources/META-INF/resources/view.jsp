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

<c:if test="<%= socialActivitiesDisplayContext.isTabsVisible() %>">
	<liferay-ui:tabs
		names="<%= socialActivitiesDisplayContext.getTabsNames() %>"
		url="<%= socialActivitiesDisplayContext.getTabsURL() %>"
		value="<%= socialActivitiesDisplayContext.getSelectedTabName() %>"
	/>
</c:if>

<liferay-social-activities:social-activities
	activitySets="<%= socialActivitiesDisplayContext.getSocialActivitySets() %>"
	feedDisplayStyle="<%= socialActivitiesDisplayContext.getRSSDisplayStyle() %>"
	feedEnabled="<%= socialActivitiesDisplayContext.isRSSEnabled() %>"
	feedResourceURL="<%= socialActivitiesDisplayContext.getRSSResourceURL() %>"
	feedTitle="<%= socialActivitiesDisplayContext.getTaglibFeedTitle() %>"
	feedType="<%= socialActivitiesDisplayContext.getRSSFeedType() %>"
	feedURLMessage="<%= socialActivitiesDisplayContext.getTaglibFeedTitle() %>"
/>

<c:if test="<%= socialActivitiesDisplayContext.isSeeMoreControlVisible() %>">
	<div class="social-activities-see-more">
		<aui:a cssClass="btn btn-default" href="<%= socialActivitiesDisplayContext.getPaginationURL() %>">
			<liferay-ui:message key="see-more" />
		</aui:a>
	</div>
</c:if>