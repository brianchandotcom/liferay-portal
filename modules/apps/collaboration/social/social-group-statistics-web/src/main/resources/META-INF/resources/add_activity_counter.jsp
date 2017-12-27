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
String portletResource = ParamUtil.getString(request, "portletResource");

String portletResourceNamespace = PortalUtil.getPortletNamespace(portletResource);

int index = ParamUtil.getInteger(request, "index");

socialGroupStatisticsPortletInstanceConfiguration = ConfigurationProviderUtil.getConfiguration(SocialGroupStatisticsPortletInstanceConfiguration.class, new ParameterMapSettingsLocator(request.getParameterMap(), new PortletInstanceSettingsLocator(themeDisplay.getLayout(), portletDisplay.getPortletResource())));

String displayActivityCounterName = "";
String chartType = "";
int chartWidth = 35;
String dataRange = "";

String[] displayActivityCounterNames = socialGroupStatisticsPortletInstanceConfiguration.displayActivityCounterName();

if (index < displayActivityCounterNames.length) {
	displayActivityCounterName = displayActivityCounterNames[index];

	String[] chartTypes = socialGroupStatisticsPortletInstanceConfiguration.chartType();

	chartType = chartTypes[index];

	String[] chartWidths = socialGroupStatisticsPortletInstanceConfiguration.chartWidth();

	chartWidth = GetterUtil.getInteger(chartWidths[index], chartWidth);

	String[] dataRanges = socialGroupStatisticsPortletInstanceConfiguration.dataRange();

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

	<aui:select inlineField="<%= true %>" label="" name='<%= portletResourceNamespace + "displayActivityCounterName" + index %>' title="display-activity-counter-name" useNamespace="<%= false %>">

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

	<aui:select inlineField="<%= true %>" label="" name='<%= portletResourceNamespace + "chartType" + index %>' title="chart-type" useNamespace="<%= false %>" value="<%= chartType %>">
		<aui:option label="group-statistics-chart-type-area-diagram" value="area" />
		<aui:option label="group-statistics-chart-type-column-diagram" value="column" />
		<aui:option label="group-statistics-chart-type-activity-distribution" value="pie" />
		<aui:option label="group-statistics-chart-type-tag-cloud" value="tag-cloud" />
	</aui:select>

	<span class="field field-inline inline-text">
		<liferay-ui:message key="group-statistics-add-counter-third-text" />
	</span>

	<aui:select inlineField="<%= true %>" label="" name='<%= portletResourceNamespace + "dataRange" + index %>' title="date-range" useNamespace="<%= false %>" value="<%= dataRange %>">
		<aui:option label="group-statistics-data-range-this-year" value="year" />
		<aui:option label="group-statistics-data-range-last-12-months" value="12months" />
	</aui:select>
</div>

<div class="field-row">
	<span class="field field-inline inline-text">
		<liferay-ui:message key="chart-width" />:
	</span>

	<aui:select inlineField="<%= true %>" label="" name='<%= portletResourceNamespace + "chartWidth" + index %>' title="chart-width" useNamespace="<%= false %>">

		<%
		for (int i = 5; i < 100; i = i + 5) {
		%>

			<aui:option label="<%= i + StringPool.PERCENT %>" selected="<%= chartWidth == i %>" value="<%= i %>" />

		<%
		}
		%>

	</aui:select>
</div>