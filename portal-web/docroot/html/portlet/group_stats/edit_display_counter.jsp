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
int index = ParamUtil.getInteger(request, "index", GetterUtil.getInteger((String)request.getAttribute("configuration.jsp-index")));

String selectedCounter = PrefsParamUtil.getString(preferences, request, "displayCounter" + index);
String selectedChartType = PrefsParamUtil.getString(preferences, request, "chartType" + index);
String selectedDataRange = PrefsParamUtil.getString(preferences, request, "dataRange" + index);

Collection<String> counters = SocialConfigurationUtil.getActivityCounterNames();

counters.add(SocialActivityCounterConstants.NAME_USER_ACHIEVEMENT);
counters.add(SocialActivityCounterConstants.NAME_ASSET_ACTIVITY);
counters.add(SocialActivityCounterConstants.NAME_USER_ACTIVITY);

String labelArg = LanguageUtil.format(pageContext, "assets", StringPool.BLANK);
%>

<div class="aui-field-row">
	<span class="aui-field aui-field-inline inline-text">
		<liferay-ui:message key="show" />
	</span>

	<aui:select inlineField="<%= true %>" label="" name='<%= "preferences--displayCounter" + index + "--" %>'>

		<%
			for (String counter : counters) {
				if (counter.equals(SocialActivityCounterConstants.NAME_CONTRIBUTION) || counter.equals(SocialActivityCounterConstants.NAME_PARTICIPATION)) {
					continue;
				}
		%>

		<aui:option label='<%= LanguageUtil.format(pageContext, "social.counter."+ counter, new Object[] {labelArg}) %>' selected="<%= counter.equals(selectedCounter) %>" value="<%= counter %>" />

		<%
			}
		%>
	</aui:select>

	<span class="aui-field aui-field-inline inline-text">
		<liferay-ui:message key="as" />
	</span>

	<aui:select inlineField="<%= true %>" label="" name='<%= "preferences--chartType" + index + "--" %>'>
		<aui:option label="area-diagram" selected='<%= "area".equals(selectedChartType) %>' value="area" />
		<aui:option label="column-diagram" selected='<%= "column".equals(selectedChartType) %>' value="column" />
		<aui:option label="activity-distribution" selected='<%= "pie".equals(selectedChartType) %>' value="pie" />
		<aui:option label="tag-cloud" selected='<%= "tagCloud".equals(selectedChartType) %>' value="tagCloud" />
	</aui:select>

	<span class="aui-field aui-field-inline inline-text">
		<liferay-ui:message key="in" />
	</span>

	<aui:select inlineField="<%= true %>" label="" name='<%= "preferences--dataRange" + index + "--" %>'>
		<aui:option label="this-year" selected='<%= "year".equals(selectedDataRange) %>' value="year" />
		<aui:option label="last-12-months" selected='<%= "12months".equals(selectedDataRange) %>' value="12months" />
	</aui:select>
</div>