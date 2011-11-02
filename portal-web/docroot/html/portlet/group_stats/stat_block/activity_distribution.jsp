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
List<SocialActivityCounter> counters = (List<SocialActivityCounter>)request.getAttribute("group-statistics:counters");
int counterIndex = (Integer)request.getAttribute("group-statistics:counter-index");

String chartType = PrefsParamUtil.getString(preferences, request, "chartType" + counterIndex, "area");

int[] categories = new int[counters.size()];
int[] values = new int[counters.size()];
int total = 0;
int totalDays = 0;

for (int i=0; i < counters.size(); i++) {
	SocialActivityCounter counter = counters.get(i);

	categories[i] = i + 1;

	values[i] = counter.getCurrentValue();

	total = total + values[i];
}

int infoBlockHeight = (Integer)request.getAttribute("group-statistics:info-block-height");
%>

<aui:layout>
	<aui:column columnWidth="70">
		<div class="group-stats-chart" style="height: <%= infoBlockHeight - 2 %>px;">
			<div id="groupStatsChart<%= counterIndex %>"></div>
		</div>
	</aui:column>

	<aui:column columnWidth="30">
		<div class="group-stats-info">
			<strong><liferay-ui:message key="activities-by-area" />:</strong>

			<table>

			<%
				for (int i=0; i < counters.size(); i++) {
					String model = "model.resource." + PortalUtil.getClassName(counters.get(i).getClassNameId());
					double percentage = 0;

					if (total > 0) {
						percentage = (double)counters.get(i).getCurrentValue() / (double)total;
					}
			%>

				<tr>
					<td>
						<div class="group-stats-color-marker" style="background-color: <%= colors[i % colors.length] %>"></div>
					</td>

					<td>
						<liferay-ui:message key="<%= model %>" />
					</td>

					<td>:</td>

					<td align="right">
						<%= formatter.format(percentage) %>
					</td>
				</tr>

			<% } %>

			</table>
		</div>
	</aui:column>
</aui:layout>

<aui:script use="charts">
	var categories = [<%= StringUtil.merge(categories) %>];
	var values = [<%= StringUtil.merge(values) %>];

	var data = [];

	for(var i = 0; i < categories.length; i++) {
		data.push(
			{
				category: categories[i],
				values: values[i]
			}
		);
	}

	var tooltip = {
		markerLabelFunction: function(categoryItem, valueItem, itemIndex, series, seriesIndex) {
			return valueItem.value;
		},

		styles: {
			backgroundColor: '#FFF',
			borderColor: '#4572A7',
			borderWidth: 2,
			color: "#000",
			textAlign: 'center'
		}
	};

	var chartContainer = A.one('#groupStatsChart<%= counterIndex %>');

	chartContainer.setStyles(
		{
			height: <%= infoBlockHeight - 2 %>,
			margin: 'auto',
			width: <%= infoBlockHeight - 2 %>
		}
	);

	var chart = new A.Chart(
		{
			dataProvider: data,
			seriesCollection: [
				{
					categoryKey: 'category',
					styles: {
						fill: {
							colors: ['<%= StringUtil.merge(colors, "', '") %>']
						}
					},
					valueKey: 'values'
				}
			],
			tooltip: tooltip,
			type: 'pie'
		}
	).render(chartContainer);
</aui:script>