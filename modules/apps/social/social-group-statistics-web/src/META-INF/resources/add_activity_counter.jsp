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
groupStatisticsPortletInstanceConfiguration = settingsFactory.getSettings(SocialGroupStatisticsPortletInstanceConfiguration.class, new ParameterMapSettingsLocator(request.getParameterMap(), new PortletInstanceSettingsLocator(themeDisplay.getLayout(), portletDisplay.getPortletResource())));

String displayActivityCounterName = "";
String chartType = "";
int chartWidth = 35;
String dataRange = "";

int index = ParamUtil.getInteger(request, "index");

String[] displayActivityCounterNames = groupStatisticsPortletInstanceConfiguration.displayActivityCounterName();

if (index < displayActivityCounterNames.length) {
	displayActivityCounterName = displayActivityCounterNames[index];

	String[] chartTypes = groupStatisticsPortletInstanceConfiguration.chartType();

	chartType = chartTypes[index];

	String[] chartWidths = groupStatisticsPortletInstanceConfiguration.chartWidth();

	chartWidth = GetterUtil.getInteger(chartWidths[index], chartWidth);

	String[] dataRanges = groupStatisticsPortletInstanceConfiguration.dataRange();

	dataRange = dataRanges[index];
}

List<String> activityCounterNames = SocialConfigurationUtil.getActivityCounterNames(SocialActivityCounterConstants.TYPE_ACTOR);

activityCounterNames.addAll(SocialConfigurationUtil.getActivityCounterNames(SocialActivityCounterConstants.TYPE_ASSET));

activityCounterNames.add(SocialActivityCounterConstants.NAME_ASSET_ACTIVITIES);
activityCounterNames.add(SocialActivityCounterConstants.NAME_USER_ACHIEVEMENTS);
activityCounterNames.add(SocialActivityCounterConstants.NAME_USER_ACTIVITIES);

Collections.sort(activityCounterNames, new SocialActivityCounterNameComparator(locale));
%>

<div class="field-row">
	<span class="field field-inline inline-text">
		<liferay-ui:message key="group-statistics-add-counter-first-text" />
	</span>

	<aui:select inlineField="<%= true %>" label="" name='<%= "preferences--displayActivityCounterName" + index + "--" %>' title="display-activity-counter-name">

		<%
		for (String activityCounterName : activityCounterNames) {
			if (activityCounterName.equals(SocialActivityCounterConstants.NAME_CONTRIBUTION) || activityCounterName.equals(SocialActivityCounterConstants.NAME_PARTICIPATION)) {
				continue;
			}
		%>

			<aui:option label='<%= LanguageUtil.get(request, "group.statistics.config."+ activityCounterName) %>' selected="<%= activityCounterName.equals(displayActivityCounterName) %>" value="<%= activityCounterName %>" />

		<%
		}
		%>

	</aui:select>

	<span class="field field-inline inline-text">
		<liferay-ui:message key="group-statistics-add-counter-second-text" />
	</span>

	<aui:select inlineField="<%= true %>" label="" name='<%= "preferences--chartType" + index + "--" %>' title="chart-type" value="<%= chartType %>">
		<aui:option label="group-statistics-chart-type-area-diagram" value="area" />
		<aui:option label="group-statistics-chart-type-column-diagram" value="column" />
		<aui:option label="group-statistics-chart-type-activity-distribution" value="pie" />
		<aui:option label="group-statistics-chart-type-tag-cloud" value="tag-cloud" />
	</aui:select>

	<span class="field field-inline inline-text">
		<liferay-ui:message key="group-statistics-add-counter-third-text" />
	</span>

	<aui:select inlineField="<%= true %>" label="" name='<%= "preferences--dataRange" + index + "--" %>' title="date-range" value="<%= dataRange %>">
		<aui:option label="group-statistics-data-range-this-year" value="year" />
		<aui:option label="group-statistics-data-range-last-12-months" value="12months" />
	</aui:select>
</div>

<div class="field-row">
	<span class="field field-inline inline-text">
		<liferay-ui:message key="chart-width" />:
	</span>

	<aui:select inlineField="<%= true %>" label="" name='<%= "preferences--chartWidth" + index + "--" %>' title="chart-width">

		<%
		for (int i = 5; i < 100; i = i + 5) {
		%>

			<aui:option label="<%= i + StringPool.PERCENT %>" selected="<%= chartWidth == i %>" value="<%= i %>" />

		<%
		}
		%>

	</aui:select>
</div>