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
List<SocialActivityStatsEntry> stats = (List<SocialActivityStatsEntry>)request.getAttribute("group-statistics:stats-entries");
int counterIndex = (Integer)request.getAttribute("group-statistics:counter-index");

String chartType = PrefsParamUtil.getString(preferences, request, "chartType" + counterIndex, "area");

int[] categories = new int[stats.size()];
int[] values = new int[stats.size()];
int total = 0;
int totalDays = 0;

for (int i=0; i < stats.size(); i++) {
	SocialActivityStatsEntry entry = stats.get(i);

	categories[i] = i + 1;

	values[i] = entry.getCurrentValue();

	total = total + values[i];
}

int infoBlockHeight = (Integer)request.getAttribute("group-statistics:info-block-height");
%>
<div class="group-stats-chart" id="group-stats-chart-<%=counterIndex %>" style="height: <%=infoBlockHeight - 2 %>px;"></div>
<div class="group-stats-info">
	<strong><liferay-ui:message key="activities-by-area" />:</strong>
	<table>
	<%
		for (int i=0; i<stats.size(); i++) {
			String model = "model.resource." + PortalUtil.getClassName(stats.get(i).getClassNameId());
			double percentage = (double)stats.get(i).getCurrentValue() / (double)total;
	%>
		<tr>
			<td><div class="group-stats-color-marker" style="background-color: <%=colors[i % colors.length] %>"></div></td>
			<td><liferay-ui:message key="<%=model %>" /></td>
			<td>:</td>
			<td align="right"><%=formatter.format(percentage) %></td>
		</tr>

	<% } %>
	</table>
</div>

<aui:script>
	var chart = new Highcharts.Chart({
		chart: {renderTo: 'group-stats-chart-<%=counterIndex %>', type: '<%=chartType %>'},
		xAxis: {
			categories: [<%=StringUtil.merge(categories) %>]
		},
		series: [{
			name: '',
			data: [<%=StringUtil.merge(values) %>]
		}]
	});
</aui:script>