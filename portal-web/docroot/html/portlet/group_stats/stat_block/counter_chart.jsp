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

SocialActivityCounter highestActivity = null;
SocialActivityCounter lowestActivity = null;

int[] categories = new int[counters.size()];
int[] values = new int[counters.size()];
int total = 0;
int totalDays = 0;
int divHeight = 0;
int currentValue = 0;

for (int i=0; i < counters.size(); i++) {
	SocialActivityCounter counter = counters.get(i);

	categories[i] = i + 1;

	values[i] = counter.getCurrentValue();

	total = total + values[i];

	if (counter.getEndPeriod() == -1) {
		totalDays = totalDays + SocialCounterPeriodUtil.getActivityDay() - counter.getStartPeriod() + 1;

		currentValue = counter.getCurrentValue();
	}
	else {
		totalDays = totalDays + counter.getEndPeriod() - counter.getStartPeriod() + 1;
	}

	if (highestActivity == null || highestActivity.getCurrentValue() < values[i]) {
		highestActivity = counter;
	}

	if (lowestActivity == null || lowestActivity.getCurrentValue() > values[i]) {
		lowestActivity = counter;
	}
}

int infoBlockHeight = (Integer)request.getAttribute("group-statistics:info-block-height");
%>

<aui:layout>
	<aui:column columnWidth="70">
		<div class="group-stats-chart" id="groupStatsChart<%=counterIndex %>" style="height: <%=infoBlockHeight - 2 %>px;"></div>
	</aui:column>
	<aui:column columnWidth="30">
		<div class="group-stats-info">
			<liferay-ui:message key="current-value" />: <%=currentValue %><br />
			<liferay-ui:message key="average-activity-per-day" />: <%=Math.round(total / totalDays * 100) / 100 %><br />
			<liferay-ui:message key="highest-activity-period" />: <span class="group-stats-activity-period"><strong>
				<%=df.format(SocialCounterPeriodUtil.getDate(highestActivity.getStartPeriod())) %>
					-
				<c:if test="<%=highestActivity.getEndPeriod() != -1 %>">
					<%=df.format(SocialCounterPeriodUtil.getDate(highestActivity.getEndPeriod())) %>
				</c:if>
				<c:if test="<%=highestActivity.getEndPeriod() == -1 %>">
					<%=df.format(new Date()) %>
				</c:if>
				</strong>
			</span> (<%=highestActivity.getCurrentValue() %>)<br />
			<liferay-ui:message key="lowest-activity-period" />: <span class="group-stats-activity-period"><strong>
				<%=df.format(SocialCounterPeriodUtil.getDate(lowestActivity.getStartPeriod())) %>
					-
				<c:if test="<%=lowestActivity.getEndPeriod() != -1 %>">
					<%=df.format(SocialCounterPeriodUtil.getDate(lowestActivity.getEndPeriod())) %>
				</c:if>
				<c:if test="<%=lowestActivity.getEndPeriod() == -1 %>">
					<%=df.format(new Date()) %>
				</c:if>
				</strong>
			</span> (<%=lowestActivity.getCurrentValue() %>)<br />
		</div>
	</aui:column>
</aui:layout>

<aui:script use="charts">
	var categories = [<%=StringUtil.merge(categories) %>];
	var values = [<%=StringUtil.merge(values) %>];

	var data = [];

	for(var i = 0, len = categories.length; i < len; i++) {
		data.push(
			{
				category: categories[i],
				values: values[i]
			}
		);
	}

	var stylesDef = {
        series: {
            values: {
				color: '#FFB700'
            }
        }
    };

	var tooltip = {
		styles: {
			backgroundColor: '#FFF',
			borderColor: '#4572A7',
			color: "#000",
			borderWidth: 2,
			textAlign: 'center'
		},

		<c:choose>
		<c:when test='<%=chartType.equals("area") %>'>
		planarLabelFunction: function(categoryItem, valueItem, itemIndex, series, seriesIndex) {
			return valueItem[0].value;
		}
		</c:when>

		<c:otherwise>
		markerLabelFunction: function(categoryItem, valueItem, itemIndex, series, seriesIndex) {
			return valueItem.value;
		}
		</c:otherwise>
		</c:choose>
	};

	var chart = new A.Chart(
		{
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
			horizontalGridlines: true,
			<c:if test='<%=chartType.equals("area") %>'>
			interactionType: 'planar',
			</c:if>
			render: '#groupStatsChart<%=counterIndex %>',
			tooltip: tooltip,
			type: '<%=chartType %>',
			stacked: true,
			styles: stylesDef
		}
	);
</aui:script>