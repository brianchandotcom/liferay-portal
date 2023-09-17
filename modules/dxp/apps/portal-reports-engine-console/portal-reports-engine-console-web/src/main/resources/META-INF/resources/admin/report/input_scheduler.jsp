<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
Calendar cal = CalendarFactoryUtil.getCalendar(timeZone, locale);
%>

<aui:fieldset>
	<aui:field-wrapper label="start-date">
		<div class="d-flex flex-wrap">
			<liferay-ui:input-date
				cssClass="form-group form-group-inline"
				dayParam="schedulerStartDateDay"
				dayValue='<%= ParamUtil.get(request, "schedulerStartDateDay", cal.get(Calendar.DATE)) %>'
				disabled="<%= false %>"
				firstDayOfWeek="<%= cal.getFirstDayOfWeek() - 1 %>"
				monthParam="schedulerStartDateMonth"
				monthValue='<%= ParamUtil.get(request, "schedulerStartDateMonth", cal.get(Calendar.MONTH)) %>'
				name="schedulerStartDate"
				yearParam="schedulerStartDateYear"
				yearValue='<%= ParamUtil.get(request, "schedulerStartDateYear", cal.get(Calendar.YEAR)) %>'
			/>

			<liferay-ui:icon
				icon="calendar"
				markupView="lexicon"
			/>

			<liferay-ui:input-time
				amPmParam="schedulerStartDateAmPm"
				amPmValue='<%= ParamUtil.get(request, "schedulerStartDateAmPm", cal.get(Calendar.AM_PM)) %>'
				cssClass="form-group form-group-inline"
				dateParam="schedulerStartTimeDate"
				hourParam="schedulerStartDateHour"
				hourValue='<%= ParamUtil.get(request, "schedulerStartDateHour", cal.get(Calendar.HOUR)) %>'
				minuteParam="schedulerStartDateMinute"
				minuteValue='<%= ParamUtil.get(request, "schedulerStartDateMinute", cal.get(Calendar.MINUTE)) %>'
				name="schedulerStartTime"
			/>
		</div>
	</aui:field-wrapper>

	<aui:field-wrapper label="end-date">
		<aui:input checked="<%= true %>" id="schedulerNoEndDate" label="no-end-date" name="endDateType" type="radio" value="0" />
		<aui:input first="<%= true %>" id="schedulerEndBy" label="end-by" name="endDateType" type="radio" value="1" />

		<div class="d-flex flex-wrap hide" id="<portlet:namespace />schedulerEndDateType">
			<liferay-ui:input-date
				cssClass="form-group form-group-inline"
				dayParam="schedulerEndDateDay"
				dayValue='<%= ParamUtil.get(request, "schedulerEndDateDay", cal.get(Calendar.DATE)) %>'
				disabled="<%= false %>"
				firstDayOfWeek="<%= cal.getFirstDayOfWeek() - 1 %>"
				monthParam="schedulerEndDateMonth"
				monthValue='<%= ParamUtil.get(request, "schedulerEndDateMonth", cal.get(Calendar.MONTH)) %>'
				name="schedulerEndDate"
				yearParam="schedulerEndDateYear"
				yearValue='<%= ParamUtil.get(request, "schedulerEndDateYear", cal.get(Calendar.YEAR)) %>'
			/>

			<liferay-ui:icon
				icon="calendar"
				markupView="lexicon"
			/>

			<liferay-ui:input-time
				amPmParam="schedulerEndDateAmPm"
				amPmValue='<%= ParamUtil.get(request, "schedulerEndDateAmPm", cal.get(Calendar.AM_PM)) %>'
				cssClass="form-group form-group-inline"
				dateParam="schedulerEndTimeDate"
				hourParam="schedulerEndDateHour"
				hourValue='<%= ParamUtil.get(request, "schedulerEndDateHour", cal.get(Calendar.HOUR)) %>'
				minuteParam="schedulerEndDateMinute"
				minuteValue='<%= ParamUtil.get(request, "schedulerEndDateMinute", cal.get(Calendar.MINUTE)) %>'
				name="schedulerEndTime"
			/>
		</div>
	</aui:field-wrapper>
</aui:fieldset>

<liferay-util:include page="/admin/report/input_repeat.jsp" servletContext="<%= application %>" />

<aui:script>
	function <portlet:namespace />showTable(id) {
		document.getElementById('<portlet:namespace />neverTable').style.display =
			'none';
		document.getElementById('<portlet:namespace />dailyTable').style.display =
			'none';
		document.getElementById('<portlet:namespace />weeklyTable').style.display =
			'none';
		document.getElementById('<portlet:namespace />monthlyTable').style.display =
			'none';
		document.getElementById('<portlet:namespace />yearlyTable').style.display =
			'none';

		document.getElementById(id).style.display = 'block';
	}

	Liferay.Util.toggleRadio(
		'<portlet:namespace />schedulerEndBy',
		'<portlet:namespace />schedulerEndDateType'
	);
	Liferay.Util.toggleRadio('<portlet:namespace />schedulerNoEndDate', '', [
		'<portlet:namespace />schedulerEndDateType',
	]);
</aui:script>