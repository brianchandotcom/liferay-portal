<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		<aui:a cssClass="btn btn-secondary" href="<%= socialActivitiesDisplayContext.getPaginationURL() %>">
			<liferay-ui:message key="see-more" />
		</aui:a>
	</div>
</c:if>