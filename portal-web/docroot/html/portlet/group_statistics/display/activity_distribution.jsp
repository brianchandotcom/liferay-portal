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
List<SocialActivityCounter> activityCounters = (List<SocialActivityCounter>)request.getAttribute("group-statistics:data");
int counterIndex = (Integer)request.getAttribute("group-statistics:counter-index");

String chartType = PrefsParamUtil.getString(preferences, request, "chartType" + counterIndex, "area");

int[] categories = new int[activityCounters.size()];
int[] values = new int[activityCounters.size()];
int total = 0;
int totalDays = 0;

for (int i=0; i < activityCounters.size(); i++) {
	SocialActivityCounter activityCounter = activityCounters.get(i);

	categories[i] = i + 1;

	values[i] = activityCounter.getCurrentValue();

	total = total + values[i];
}

int infoBlockHeight = (Integer)request.getAttribute("group-statistics:info-block-height");
%>

<aui:layout>
	<aui:column columnWidth="70">
		<div class="group-statistics-chart" style="height: <%= infoBlockHeight - 2 %>px; width: <%= infoBlockHeight - 2 %>px;">
			<div id="groupStatisticsChart<%= counterIndex %>"></div>
		</div>
	</aui:column>

	<aui:column columnWidth="30">
		<div class="group-statistics-info">
			<strong><liferay-ui:message key="activities-by-area" />:</strong>

			<table>

			<%
				for (int i=0; i < activityCounters.size(); i++) {
					String model = "model.resource." + PortalUtil.getClassName(activityCounters.get(i).getClassNameId());
					double percentage = 0;

					if (total > 0) {
						percentage = (double)activityCounters.get(i).getCurrentValue() / (double)total;
					}
			%>

				<tr>
					<td>
						<div class="group-statistics-color-marker" style="background-color: <%= colors[i % colors.length] %>"></div>
					</td>

					<td>
						<liferay-ui:message key="<%= model %>" />
					</td>

					<td>:</td>

					<td align="right">
						<%= decimalFormat.format(percentage) %>
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
			borderWidth: 1,
			color: '#000',
			textAlign: 'center',
			width: 30
		}
	};

	var chartContainer = A.one('#groupStatisticsChart<%= counterIndex %>');

	var chart = new A.Chart(
		{
			dataProvider: data,
			seriesCollection: [
				{
					categoryKey: 'category',
					styles: {
						fill: {
							colors: ['<%= StringUtil.merge(colors, "', '") %>']
						},
						border: {
							alpha: 0.8,
							colors: new Array(9).join('#333,').split(','),
							weight: 1
						}
					},
					valueKey: 'values'
				}
			],
			height: <%= infoBlockHeight - 2 %>,
			tooltip: tooltip,
			type: 'pie',
			width: <%= infoBlockHeight - 2 %>
		}
	).render(chartContainer);

	Liferay.after(
		['portletMoved', 'liferaypanel:collapse'],
		function(event) {
			var width = chartContainer.width();

			if (width && (event.type == 'portletMoved' && event.portletId == '<%= portletDisplay.getId() %>') ||
				(event.type == 'liferaypanel:collapse' && event.panelId  == 'groupStatisticsPanel<%= counterIndex %>')) {

				chart.set('width', width);
			}
		}
	);
</aui:script>