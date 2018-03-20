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

<%@ include file="/html/taglib/init.jsp" %>

<%
Calendar cal = CalendarFactoryUtil.getCalendar(timeZone, locale);

int endAmPm = ParamUtil.get(request, "schedulerEndDateAmPm", cal.get(Calendar.AM_PM));
int endDay = ParamUtil.get(request, "schedulerEndDateDay", cal.get(Calendar.DATE));
int endHour = ParamUtil.get(request, "schedulerEndDateHour", cal.get(Calendar.HOUR));
int endMinute = ParamUtil.get(request, "schedulerEndDateMinute", cal.get(Calendar.MINUTE));
int endMonth = ParamUtil.get(request, "schedulerEndDateMonth", cal.get(Calendar.MONTH));
int endYear = ParamUtil.get(request, "schedulerEndDateYear", cal.get(Calendar.YEAR));

int startAmPm = ParamUtil.get(request, "schedulerStartDateAmPm", cal.get(Calendar.AM_PM));
int startDay = ParamUtil.get(request, "schedulerStartDateDay", cal.get(Calendar.DATE));
int startHour = ParamUtil.get(request, "schedulerStartDateHour", cal.get(Calendar.HOUR));
int startMinute = ParamUtil.get(request, "schedulerStartDateMinute", cal.get(Calendar.MINUTE));
int startMonth = ParamUtil.get(request, "schedulerStartDateMonth", cal.get(Calendar.MONTH));
int startYear = ParamUtil.get(request, "schedulerStartDateYear", cal.get(Calendar.YEAR));
%>

<aui:fieldset>
	<aui:field-wrapper label="start-date">
		<div class="flex-container">
			<liferay-ui:input-date
				cssClass="form-group form-group-inline"
				dayParam="schedulerStartDateDay"
				dayValue="<%= startDay %>"
				disabled="<%= false %>"
				firstDayOfWeek="<%= cal.getFirstDayOfWeek() - 1 %>"
				monthParam="schedulerStartDateMonth"
				monthValue="<%= startMonth %>"
				name="schedulerStartDate"
				yearParam="schedulerStartDateYear"
				yearValue="<%= startYear %>"
			/>

			<liferay-ui:icon
				icon="calendar"
				markupView="lexicon"
			/>

			<liferay-ui:input-time
				amPmParam="schedulerStartDateAmPm"
				amPmValue="<%= startAmPm %>"
				cssClass="form-group form-group-inline"
				dateParam="schedulerStartTimeDate"
				hourParam="schedulerStartDateHour"
				hourValue="<%= startHour %>"
				minuteParam="schedulerStartDateMinute"
				minuteValue="<%= startMinute %>"
				name="schedulerStartTime"
			/>
		</div>
	</aui:field-wrapper>

	<aui:field-wrapper label="end-date">
		<aui:input checked="<%= true %>" id="schedulerNoEndDate" label="no-end-date" name="endDateType" type="radio" value="0" />
		<aui:input first="<%= true %>" id="schedulerEndBy" label="end-by" name="endDateType" type="radio" value="1" />

		<div class="flex-container hide" id="<portlet:namespace />schedulerEndDateType">
			<liferay-ui:input-date
				cssClass="form-group form-group-inline"
				dayParam="schedulerEndDateDay"
				dayValue="<%= endDay %>"
				disabled="<%= false %>"
				firstDayOfWeek="<%= cal.getFirstDayOfWeek() - 1 %>"
				monthParam="schedulerEndDateMonth"
				monthValue="<%= endMonth %>"
				name="schedulerEndDate"
				yearParam="schedulerEndDateYear"
				yearValue="<%= endYear %>"
			/>

			<liferay-ui:icon
				icon="calendar"
				markupView="lexicon"
			/>

			<liferay-ui:input-time
				amPmParam="schedulerEndDateAmPm"
				amPmValue="<%= endAmPm %>"
				cssClass="form-group form-group-inline"
				dateParam="schedulerEndTimeDate"
				hourParam="schedulerEndDateHour"
				hourValue="<%= endHour %>"
				minuteParam="schedulerEndDateMinute"
				minuteValue="<%= endMinute %>"
				name="schedulerEndTime"
			/>
		</div>
	</aui:field-wrapper>
</aui:fieldset>

<liferay-ui:input-repeat />

<aui:script>
	function <portlet:namespace />showTable(id) {
		document.getElementById('<portlet:namespace />neverTable').style.display = 'none';
		document.getElementById('<portlet:namespace />dailyTable').style.display = 'none';
		document.getElementById('<portlet:namespace />weeklyTable').style.display = 'none';
		document.getElementById('<portlet:namespace />monthlyTable').style.display = 'none';
		document.getElementById('<portlet:namespace />yearlyTable').style.display = 'none';

		document.getElementById(id).style.display = 'block';
	}

	Liferay.Util.toggleRadio('<portlet:namespace />schedulerEndBy', '<portlet:namespace />schedulerEndDateType');
	Liferay.Util.toggleRadio('<portlet:namespace />schedulerNoEndDate', '', ['<portlet:namespace />schedulerEndDateType']);
</aui:script>