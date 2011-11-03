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
for (int displayCounterNameIndex : displayCounterNameIndexes) {
	String counterName = PrefsParamUtil.getString(preferences, request, "displayCounterName" + displayCounterNameIndex);
	String chartType = PrefsParamUtil.getString(preferences, request, "chartType" + displayCounterNameIndex, "area");
	String dataRange = PrefsParamUtil.getString(preferences, request, "dataRange" + displayCounterNameIndex, "year");

	String assetsLocalized = LanguageUtil.format(pageContext, "assets", StringPool.BLANK);
	String title = LanguageUtil.format(pageContext, "social.counter." + counterName, new Object[] {assetsLocalized});

	request.setAttribute("group-statistics:counter-index", displayCounterNameIndex);

	List<?> data = null;

	if (chartType.equals("pie")) {
		if (dataRange.equals("year")) {
			data = SocialActivityCounterLocalServiceUtil.getActivityCounterDistribution(scopeGroupId, counterName, SocialCounterPeriodUtil.getFirstActivityDayOfYear(), SocialCounterPeriodUtil.getEndPeriod());
		}
		else {
			data = SocialActivityCounterLocalServiceUtil.getActivityCounterDistribution(scopeGroupId, counterName, 11, true);
		}
	}
	else if (chartType.equals("tagCloud")) {
		if (dataRange.equals("year")) {
			data = AssetTagLocalServiceUtil.getTags(scopeGroupId, counterName, SocialCounterPeriodUtil.getFirstActivityDayOfYear(), SocialCounterPeriodUtil.getEndPeriod());
		}
		else {
			data = AssetTagLocalServiceUtil.getTags(scopeGroupId, counterName, 11, true);
		}

		title = LanguageUtil.format(pageContext, "tag-cloud-based-on-x", new Object[] {title});
	}
	else {
		if (dataRange.equals("year")) {
			data = SocialActivityCounterLocalServiceUtil.getActivityCounters(scopeGroupId, counterName, SocialCounterPeriodUtil.getFirstActivityDayOfYear(), SocialCounterPeriodUtil.getEndPeriod());
		}
		else {
			data = SocialActivityCounterLocalServiceUtil.getActivityCounters(scopeGroupId, counterName, 11, true);
		}
	}

	if (data == null || data.size() == 0) {
		continue;
	}

	request.setAttribute("group-statistics:data", data);

	int infoBlockHeight = 0;

	if (chartType.equals("pie")) {
		infoBlockHeight = (data.size() + 1) * 18;
	}

	if (infoBlockHeight < 80) {
		infoBlockHeight = 80;
	}

	request.setAttribute("group-statistics:info-block-height", infoBlockHeight);
%>

<div class="group-statistics-container">
	<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" persistState="<%= true %>" title="<%=title %>">

	<div class="group-statistics-body" style="height: <%=infoBlockHeight %>px;">
		<c:choose>
			<c:when test='<%=chartType.equals("tagCloud") %>'>
				<liferay-util:include page="/html/portlet/group_statistics/display/tag_cloud.jsp" />
			</c:when>

			<c:when test='<%=chartType.equals("pie") %>'>
				<liferay-util:include page="/html/portlet/group_statistics/display/activity_distribution.jsp" />
			</c:when>

			<c:otherwise>
				<liferay-util:include page="/html/portlet/group_statistics/display/counter_chart.jsp" />
			</c:otherwise>
		</c:choose>
	</div>

	</liferay-ui:panel>
</div>

<%
}
%>

<c:if test="<%= Validator.isNull(displayCounterNameIndexesParam) %>">
	<div class="portlet-configuration portlet-msg-info">
		<a href="<%= portletDisplay.getURLConfiguration() %>" onClick="<%= portletDisplay.getURLConfigurationJS() %>">
	    	<liferay-ui:message key="please-configure-this-portlet-and-select-at-least-one-activity-counter" />
		</a>
	</div>
</c:if>