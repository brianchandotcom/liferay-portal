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

<%@ include file="/html/portlet/group_statistics/init.jsp" %>

<%
for (int displayActivityCounterNameIndex : displayActivityCounterNameIndexes) {
	String displayActivityCounterName = PrefsParamUtil.getString(preferences, request, "displayActivityCounterName" + displayActivityCounterNameIndex);
	String chartType = PrefsParamUtil.getString(preferences, request, "chartType" + displayActivityCounterNameIndex, "area");
	String dataRange = PrefsParamUtil.getString(preferences, request, "dataRange" + displayActivityCounterNameIndex, "year");

	List<AssetTag> assetTags = null;

	List<SocialActivityCounter> activityCounters = null;

	String title = LanguageUtil.format(pageContext, "social.counter." + displayActivityCounterName, new Object[] {LanguageUtil.get(pageContext, "assets")});

	int displayHeight = 80;

	if (chartType.equals("tagCloud")) {
		if (dataRange.equals("year")) {
			assetTags = AssetTagLocalServiceUtil.getTags(scopeGroupId, displayActivityCounterName, SocialCounterPeriodUtil.getFirstActivityDayOfYear(), SocialCounterPeriodUtil.getEndPeriod());
		}
		else {
			assetTags = AssetTagLocalServiceUtil.getTags(scopeGroupId, displayActivityCounterName, 11, true);
		}

		if (assetTags.isEmpty()) {
			continue;
		}

		title = LanguageUtil.format(pageContext, "tag-cloud-based-on-x", new Object[] {title});
	}
	else {
		if (chartType.equals("pie")) {
			if (dataRange.equals("year")) {
				activityCounters = SocialActivityCounterLocalServiceUtil.getActivityCounterDistribution(scopeGroupId, displayActivityCounterName, SocialCounterPeriodUtil.getFirstActivityDayOfYear(), SocialCounterPeriodUtil.getEndPeriod());
			}
			else {
				activityCounters = SocialActivityCounterLocalServiceUtil.getActivityCounterDistribution(scopeGroupId, displayActivityCounterName, 11, true);
			}

			displayHeight = Math.max((activityCounters.size() + 1) * 18, displayHeight);
		}
		else {
			if (dataRange.equals("year")) {
				activityCounters = SocialActivityCounterLocalServiceUtil.getActivityCounters(scopeGroupId, displayActivityCounterName, SocialCounterPeriodUtil.getFirstActivityDayOfYear(), SocialCounterPeriodUtil.getEndPeriod());
			}
			else {
				activityCounters = SocialActivityCounterLocalServiceUtil.getActivityCounters(scopeGroupId, displayActivityCounterName, 11, true);
			}
		}

		if (activityCounters.isEmpty()) {
			continue;
		}
	}
%>

	<div class="group-statistics-container">
		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id='<%= "groupStatisticsPanel" + displayActivityCounterNameIndex %>' persistState="<%= true %>" title="<%= title %>">
			<div class="group-statistics-body" style="height: <%= displayHeight %>px;">
				<c:choose>
					<c:when test='<%= chartType.equals("pie") %>'>
						<%@ include file="/html/portlet/group_statistics/chart/pie.jspf" %>
					</c:when>
					<c:when test='<%= chartType.equals("tagCloud") %>'>
						<%@ include file="/html/portlet/group_statistics/chart/tag_cloud.jspf" %>
					</c:when>
					<c:otherwise>
						<%@ include file="/html/portlet/group_statistics/chart/other.jspf" %>
					</c:otherwise>
				</c:choose>
			</div>
		</liferay-ui:panel>
	</div>

<%
}
%>

<c:if test="<%= Validator.isNull(displayActivityCounterNameIndexesParam) %>">
	<div class="portlet-configuration portlet-msg-info">
		<a href="<%= portletDisplay.getURLConfiguration() %>" onClick="<%= portletDisplay.getURLConfigurationJS() %>">
			<liferay-ui:message key="please-configure-this-portlet-and-select-at-least-one-activity-counter" />
		</a>
	</div>
</c:if>