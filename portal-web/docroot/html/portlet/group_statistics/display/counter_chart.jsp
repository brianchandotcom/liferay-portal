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

SocialActivityCounter highestActivityCounter = null;
SocialActivityCounter lowestActivityCounter = null;

int[] categories = new int[activityCounters.size()];
int[] values = new int[activityCounters.size()];
int total = 0;
int totalDays = 0;
int divHeight = 0;
int currentValue = 0;

for (int i=0; i < activityCounters.size(); i++) {
	SocialActivityCounter activityCounter = activityCounters.get(i);

	categories[i] = i + 1;

	values[i] = activityCounter.getCurrentValue();

	total = total + values[i];

	if (activityCounter.getEndPeriod() == -1) {
		totalDays = totalDays + SocialCounterPeriodUtil.getActivityDay() - activityCounter.getStartPeriod() + 1;

		currentValue = activityCounter.getCurrentValue();
	}
	else {
		totalDays = totalDays + activityCounter.getEndPeriod() - activityCounter.getStartPeriod() + 1;
	}

	if (highestActivityCounter == null || highestActivityCounter.getCurrentValue() < values[i]) {
		highestActivityCounter = activityCounter;
	}

	if (lowestActivityCounter == null || lowestActivityCounter.getCurrentValue() > values[i]) {
		lowestActivityCounter = activityCounter;
	}
}

int infoBlockHeight = (Integer)request.getAttribute("group-statistics:info-block-height");
%>

<aui:layout>
	<aui:column columnWidth="70" first="<%= true %>">
		<div class="group-statistics-chart" id="groupStatisticsChart<%= counterIndex %>" style="height: <%= infoBlockHeight - 2 %>px;"></div>
	</aui:column>

	<aui:column columnWidth="30">
		<div class="group-statistics-info">
			<liferay-ui:message key="current-value" />: <%= currentValue %><br />

			<liferay-ui:message key="average-activity-per-day" />: <%= Math.round(total / totalDays * 100) / 100 %><br />

			<liferay-ui:message key="highest-activity-period" />: <span class="group-statistics-activity-period">
				<strong>
					<%= dateFormat.format(SocialCounterPeriodUtil.getDate(highestActivityCounter.getStartPeriod())) %>
						-
					<c:if test="<%= highestActivityCounter.getEndPeriod() != -1 %>">
						<%= dateFormat.format(SocialCounterPeriodUtil.getDate(highestActivityCounter.getEndPeriod())) %>
					</c:if>

					<c:if test="<%= highestActivityCounter.getEndPeriod() == -1 %>">
						<%= dateFormat.format(new Date()) %>
					</c:if>
				</strong>
			</span>

			(<%= highestActivityCounter.getCurrentValue() %>)<br />

			<liferay-ui:message key="lowest-activity-period" />: <span class="group-statistics-activity-period">
				<strong>
					<%= dateFormat.format(SocialCounterPeriodUtil.getDate(lowestActivityCounter.getStartPeriod())) %>
						-
					<c:if test="<%= lowestActivityCounter.getEndPeriod() != -1 %>">
						<%= dateFormat.format(SocialCounterPeriodUtil.getDate(lowestActivityCounter.getEndPeriod())) %>
					</c:if>

					<c:if test="<%= lowestActivityCounter.getEndPeriod() == -1 %>">
						<%= dateFormat.format(new Date()) %>
					</c:if>
				</strong>
			</span>

			(<%= lowestActivityCounter.getCurrentValue() %>)<br />
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

	<c:choose>
		<c:when test='<%= chartType.equals("area") %>'>
			var chartType = 'combo';

			var customConfig = {
				showAreaFill: true,
				showMarkers: true,
				styles: {
					series: {
						values: {
							area: {
								color: '#5CC0FF',
								alpha: 0.4
							},
							line: {
								color: '#4572A7',
								weight: 2
							},
							marker: {
								fill: {
									color: '#3CCFFF'
								},
								height: 6
							}
						}
					}
				}
			};
		</c:when>
		<c:otherwise>
			var chartType = '<%= chartType %>';

			var customConfig = {
				showAreaFill: true,
				showMarkers: true
			};
		</c:otherwise>
	</c:choose>

	var chartContainer = A.one('#groupStatisticsChart<%= counterIndex %>');

	var defaultConfig = {
		axes: {
			category: {
				styles:
				{
					label: {
						display: 'none'
					}
				}
			},
			values: {
				styles: {
					majorUnit: {
						count: 6
					}
				}
			}
		},
		dataProvider: data,
		height: <%= infoBlockHeight - 2 %>,
		horizontalGridlines: true,
		tooltip: tooltip,
		type: chartType,
		width: chartContainer.width()
	};

	A.mix(defaultConfig, customConfig);

	var chart = new A.Chart(defaultConfig).render(chartContainer);

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