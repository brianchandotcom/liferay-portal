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

SocialActivityStatsEntry highestActivity = null;
SocialActivityStatsEntry lowestActivity = null;

int[] categories = new int[stats.size()];
int[] values = new int[stats.size()];
int total = 0;
int totalDays = 0;
int divHeight = 0;
int currentValue = 0;

for (int i=0; i < stats.size(); i++) {
	SocialActivityStatsEntry entry = stats.get(i);

	categories[i] = i + 1;

	values[i] = entry.getCurrentValue();

	total = total + values[i];

	if (entry.getStatPeriodEnd() == -1) {
		totalDays = totalDays + SocialStatsUtil.getActivityDay() - entry.getStatPeriodStart() + 1;

		currentValue = entry.getCurrentValue();
	}
	else {
		totalDays = totalDays + entry.getStatPeriodEnd() - entry.getStatPeriodStart() + 1;
	}

	if (highestActivity == null || highestActivity.getCurrentValue() < values[i]) {
		highestActivity = entry;
	}

	if (lowestActivity == null || lowestActivity.getCurrentValue() > values[i]) {
		lowestActivity = entry;
	}
}

int infoBlockHeight = (Integer)request.getAttribute("group-statistics:info-block-height");
%>
<div class="group-stats-chart" id="group-stats-chart-<%=counterIndex %>" style="height: <%=infoBlockHeight - 2 %>px;"></div>
<div class="group-stats-info">
	<liferay-ui:message key="current-value" />: <%=currentValue %><br />
	<liferay-ui:message key="average-activity-per-day" />: <%=Math.round(total / totalDays * 100) / 100 %><br />
	<liferay-ui:message key="highest-activity-period" />: <span class="group-stats-activity-period"><strong>
		<%=df.format(SocialStatsUtil.getDate(highestActivity.getStatPeriodStart())) %>
			-
		<c:if test="<%=highestActivity.getStatPeriodEnd() != -1 %>">
			<%=df.format(SocialStatsUtil.getDate(highestActivity.getStatPeriodEnd())) %>
		</c:if>
		<c:if test="<%=highestActivity.getStatPeriodEnd() == -1 %>">
			<%=df.format(new Date()) %>
		</c:if>
		</strong>
	</span> (<%=highestActivity.getCurrentValue() %>)<br />
	<liferay-ui:message key="lowest-activity-period" />: <span class="group-stats-activity-period"><strong>
		<%=df.format(SocialStatsUtil.getDate(lowestActivity.getStatPeriodStart())) %>
			-
		<c:if test="<%=lowestActivity.getStatPeriodEnd() != -1 %>">
			<%=df.format(SocialStatsUtil.getDate(lowestActivity.getStatPeriodEnd())) %>
		</c:if>
		<c:if test="<%=lowestActivity.getStatPeriodEnd() == -1 %>">
			<%=df.format(new Date()) %>
		</c:if>
		</strong>
	</span> (<%=lowestActivity.getCurrentValue() %>)<br />
</div>

<aui:script>
	var chart = new Highcharts.Chart({
		chart: {backgroundColor: '#ffffff', renderTo: 'group-stats-chart-<%=counterIndex %>', type: '<%=chartType %>'},
		xAxis: {
			<c:if test='<%=chartType.equals("column") %>'>
			startOnTick: false,
			endOnTick: false,
			</c:if>
			categories: [<%=StringUtil.merge(categories) %>]
		},
		series: [{
			name: '',
			data: [<%=StringUtil.merge(values) %>]
		}]
	});
</aui:script>