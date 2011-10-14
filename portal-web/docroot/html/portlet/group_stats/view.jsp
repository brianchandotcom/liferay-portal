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

<%@ include file="/html/portlet/group_stats/init.jsp" %>

<%

for (int displayCounterIndex : displayCounterIndexes) {
	String counter = PrefsParamUtil.getString(preferences, request, "displayCounter" + displayCounterIndex);
	String chartType = PrefsParamUtil.getString(preferences, request, "chartType" + displayCounterIndex, "area");
	String dataRange = PrefsParamUtil.getString(preferences, request, "dataRange" + displayCounterIndex, "year");

	String labelArg = LanguageUtil.format(pageContext, "assets", StringPool.BLANK);
	String title = LanguageUtil.format(pageContext, "social.counter." + counter, new Object[] {labelArg});

	request.setAttribute("group-statistics:counter-index", displayCounterIndex);

	List<?> stats = null;

	if (chartType.equals("pie")) {
		if (dataRange.equals("year")) {
			stats = SocialActivityCounterLocalServiceUtil.getActivityCounterDistribution(scopeGroupId, counter, SocialCounterPeriodUtil.getFirstActivityDayOfYear(), SocialCounterPeriodUtil.getEndPeriod());
		}
		else {
			stats = SocialActivityCounterLocalServiceUtil.getActivityCounterDistribution(scopeGroupId, counter, 11, true);
		}
	}
	else if (chartType.equals("tagCloud")) {
		if (dataRange.equals("year")) {
			stats = AssetTagLocalServiceUtil.getTags(scopeGroupId, counter, SocialCounterPeriodUtil.getFirstActivityDayOfYear(), SocialCounterPeriodUtil.getEndPeriod());
		}
		else {
			stats = AssetTagLocalServiceUtil.getTags(scopeGroupId, counter, 11, true);
		}

		title = LanguageUtil.format(pageContext, "tag-cloud-based-on-x", new Object[] {title});
	}
	else {
		if (dataRange.equals("year")) {
			stats = SocialActivityCounterLocalServiceUtil.getActivityCounters(scopeGroupId, counter, SocialCounterPeriodUtil.getFirstActivityDayOfYear(), SocialCounterPeriodUtil.getEndPeriod());
		}
		else {
			stats = SocialActivityCounterLocalServiceUtil.getActivityCounters(scopeGroupId, counter, 11, true);
		}
	}

	if (stats == null || stats.size() == 0) {
		continue;
	}

	request.setAttribute("group-statistics:counters", stats);



	int infoBlockHeight = 0;

	if (chartType.equals("pie")) {
		infoBlockHeight = (stats.size() + 1) * 18;
	}

	if (infoBlockHeight < 80) {
		infoBlockHeight = 80;
	}

	request.setAttribute("group-statistics:info-block-height", infoBlockHeight);
%>

<div class="group-stats-container">
	<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" persistState="<%= true %>" title="<%=title %>">
	<div class="group-stats-body" style="height: <%=infoBlockHeight %>px;">
		<c:choose>
			<c:when test='<%=chartType.equals("tagCloud") %>'>
				<liferay-util:include page="/html/portlet/group_stats/stat_block/tag_cloud.jsp" />
			</c:when>
			<c:when test='<%=chartType.equals("pie") %>'>
				<liferay-util:include page="/html/portlet/group_stats/stat_block/activity_distribution.jsp" />
			</c:when>
			<c:otherwise>
				<liferay-util:include page="/html/portlet/group_stats/stat_block/counter_chart.jsp" />
			</c:otherwise>
		</c:choose>
	</div>
	</liferay-ui:panel>
</div>
<%
}
%>