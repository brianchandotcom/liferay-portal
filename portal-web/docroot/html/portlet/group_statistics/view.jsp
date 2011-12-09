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

	if (Validator.isNull(displayActivityCounterName)){
		continue;
	}

	String chartType = PrefsParamUtil.getString(preferences, request, "chartType" + displayActivityCounterNameIndex, "area");
	int chartWidth = PrefsParamUtil.getInteger(preferences, request, "chartWidth" + displayActivityCounterNameIndex, 35);
	String dataRange = PrefsParamUtil.getString(preferences, request, "dataRange" + displayActivityCounterNameIndex, "year");

	List<AssetTag> assetTags = null;

	List<SocialActivityCounter> activityCounters = null;

	String title = LanguageUtil.format(pageContext, "social.counter." + displayActivityCounterName, new Object[] {LanguageUtil.get(pageContext, "assets")});

	int dataSize = 0;
	int displayHeight = 80;

	if (chartType.equals("tag-cloud")) {
		if (dataRange.equals("year")) {
			assetTags = AssetTagLocalServiceUtil.getTags(scopeGroupId, displayActivityCounterName, SocialCounterPeriodUtil.getFirstActivityDayOfYear(), SocialCounterPeriodUtil.getEndPeriod());
		}
		else {
			assetTags = AssetTagLocalServiceUtil.getTagsByOffset(scopeGroupId, displayActivityCounterName, -12, 0);
		}

		title = LanguageUtil.format(pageContext, "tag-cloud-based-on-x", new Object[] {title});

		dataSize = assetTags.size();
	}
	else {
		if (chartType.equals("pie")) {
			if (dataRange.equals("year")) {
				activityCounters = SocialActivityCounterLocalServiceUtil.getActivityCounterDistributionByPeriod(scopeGroupId, displayActivityCounterName, SocialCounterPeriodUtil.getFirstActivityDayOfYear(), SocialCounterPeriodUtil.getEndPeriod());
			}
			else {
				activityCounters = SocialActivityCounterLocalServiceUtil.getActivityCounterDistributionByOffset(scopeGroupId, displayActivityCounterName, -12, 0);
			}

			displayHeight = Math.max((activityCounters.size() + 1) * 18, displayHeight);
		}
		else {
			if (dataRange.equals("year")) {
				activityCounters = SocialActivityCounterLocalServiceUtil.getActivityCountersByPeriod(scopeGroupId, displayActivityCounterName, SocialCounterPeriodUtil.getFirstActivityDayOfYear(), SocialCounterPeriodUtil.getEndPeriod());
			}
			else {
				activityCounters = SocialActivityCounterLocalServiceUtil.getActivityCountersByOffset(scopeGroupId, displayActivityCounterName, -12, 0);
			}
		}

		dataSize = activityCounters.size();
	}

	if (dataSize == 0) {
		displayHeight = 40;
	}
%>

	<div class="group-statistics-container">
		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id='<%= "groupStatisticsPanel" + displayActivityCounterNameIndex %>' persistState="<%= true %>" title="<%= title %>">
			<div class="group-statistics-body chart-<%= chartType %>" style="height: <%= displayHeight %>px;">
				<c:choose>
					<c:when test="<%= dataSize > 0 %>">
						<c:choose>
							<c:when test='<%= chartType.equals("pie") %>'>
								<%@ include file="/html/portlet/group_statistics/chart/pie.jspf" %>
							</c:when>
							<c:when test='<%= chartType.equals("tag-cloud") %>'>
								<%@ include file="/html/portlet/group_statistics/chart/tag_cloud.jspf" %>
							</c:when>
							<c:otherwise>
								<%@ include file="/html/portlet/group_statistics/chart/other.jspf" %>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<div class="portlet-configuration portlet-msg-info">
							<liferay-ui:message key="there-is-not-enough-data-to-display-for-this-counter" />
						</div>
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