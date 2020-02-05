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

<%@ include file="/input_scheduler/init.jsp" %>

<ul class="hide options portlet-list select-options" id="<portlet:namespace />selectSchedule">
	<li>
		<liferay-ui:error exception="<%= com.liferay.portal.kernel.scheduler.SchedulerException.class %>" message="a-wrong-end-date-was-specified-the-scheduled-process-will-never-run" />

		<aui:input name="jobName" type="hidden" />

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

		String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-repeat:cssClass"));

		Recurrence recurrence = null;

		int recurrenceType = ParamUtil.getInteger(request, "recurrenceType", Recurrence.NO_RECURRENCE);
		int dailyType = ParamUtil.getInteger(request, "dailyType");
		int dailyInterval = ParamUtil.getInteger(request, "dailyInterval", 1);
		int weeklyInterval = ParamUtil.getInteger(request, "weeklyInterval", 1);
		int monthlyType = ParamUtil.getInteger(request, "monthlyType");
		int monthlyDay0 = ParamUtil.getInteger(request, "monthlyDay0", 15);
		int monthlyInterval0 = ParamUtil.getInteger(request, "monthlyInterval0", 1);
		int monthlyPos = ParamUtil.getInteger(request, "monthlyPos", 1);
		int monthlyDay1 = ParamUtil.getInteger(request, "monthlyDay1", Calendar.SUNDAY);
		int monthlyInterval1 = ParamUtil.getInteger(request, "monthlyInterval1", 1);
		int yearlyType = ParamUtil.getInteger(request, "yearlyType");
		int yearlyMonth0 = ParamUtil.getInteger(request, "yearlyMonth0", Calendar.JANUARY);
		int yearlyDay0 = ParamUtil.getInteger(request, "yearlyDay0", 15);
		int yearlyInterval0 = ParamUtil.getInteger(request, "yearlyInterval0", 1);
		int yearlyPos = ParamUtil.getInteger(request, "yearlyPos", 1);
		int yearlyDay1 = ParamUtil.getInteger(request, "yearlyDay1", Calendar.SUNDAY);
		int yearlyMonth1 = ParamUtil.getInteger(request, "yearlyMonth1", Calendar.JANUARY);
		int yearlyInterval1 = ParamUtil.getInteger(request, "yearlyInterval1", 1);

		int[] monthIds = CalendarUtil.getMonthIds();
		String[] months = CalendarUtil.getMonths(locale);
		String timeZoneID = timeZone.getID();
		%>

		<table class="staging-publish-schedule">
			<tbody>
				<tr>
					<th class="staging-scheduler-title">
						<liferay-ui:message key="start-date" />:
					</th>
					<td class="staging-scheduler-content">
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
					</td>
				</tr>
			</tbody>

			<tbody>
				<tr>
					<th class="staging-scheduler-title">
						<liferay-ui:message key="time-zone" />:
					</th>
					<td class="staging-scheduler-content">
						<aui:input cssClass="calendar-portlet-time-zone-field" label="" name="timeZoneId" type="timeZone" value="<%= timeZoneID %>" />
					</td>
				</tr>
			</tbody>

			<tbody>
				<tr>
					<th class="staging-scheduler-title">
						<liferay-ui:message key="end-date" />:
					</th>
					<td class="bottom-gap staging-scheduler-content">
						<aui:input checked="<%= true %>" id="schedulerNoEndDate" inlineField="<%= true %>" label="no-end-date" name="endDateType" type="radio" value="0" />
						<aui:input first="<%= true %>" id="schedulerEndBy" inlineField="<%= true %>" label="end-by" name="endDateType" type="radio" value="1" />

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
					</td>
				</tr>
			</tbody>

			<tbody>
				<tr>
					<th class="staging-scheduler-title">
						<liferay-ui:message key="repeat" />:
					</th>
					<td class="staging-scheduler-content">
						<aui:select label="" name="recurrenceType">
							<aui:option checked="<%= recurrenceType == Recurrence.NO_RECURRENCE %>" id="recurrenceTypeNever" label="never" value="<%= Recurrence.NO_RECURRENCE %>" />
							<aui:option checked="<%= recurrenceType == Recurrence.DAILY %>" id="recurrenceTypeDaily" label="daily" value="<%= Recurrence.DAILY %>" />
							<aui:option checked="<%= recurrenceType == Recurrence.WEEKLY %>" id="recurrenceTypeWeekly" label="weekly" value="<%= Recurrence.WEEKLY %>" />
							<aui:option checked="<%= recurrenceType == Recurrence.MONTHLY %>" id="recurrenceTypeMonthly" label="monthly" value="<%= Recurrence.MONTHLY %>" />
							<aui:option checked="<%= recurrenceType == Recurrence.YEARLY %>" id="recurrenceTypeYearly" label="yearly" value="<%= Recurrence.YEARLY %>" />
						</aui:select>
					</td>
				</tr>
			</tbody>

			<tbody class="<%= (recurrenceType != Recurrence.DAILY) ? "hide" : StringPool.BLANK %>" id="<portlet:namespace />recurrenceTypeDailyTable">
				<tr>
					<th class="staging-scheduler-title">
						<liferay-ui:message key="recur-every" />:
					</th>
					<td class="staging-scheduler-content">
						<aui:input checked="<%= dailyType == 0 %>" cssClass="input-container" label="days" name="dailyType" type="radio" value="0" />
					</td>
				</tr>
				<tr>
					<th class="staging-scheduler-title">
					</th>
					<td class="staging-scheduler-content">
						<aui:input cssClass="number-input" label="" maxlength="3" name="dailyInterval" suffix="day-s" type="number" value="<%= dailyInterval %>">
							<aui:validator name="digit" />
						</aui:input>
					</td>
				</tr>
				<tr>
					<th class="staging-scheduler-title">
					</th>
					<td class="staging-scheduler-content">
						<aui:input checked="<%= dailyType == 1 %>" label="every-weekday" name="dailyType" type="radio" value="1" />
					</td>
				</tr>
			</tbody>

			<tbody class="<%= (recurrenceType != Recurrence.WEEKLY) ? "hide" : StringPool.BLANK %>" id="<portlet:namespace />recurrenceTypeWeeklyTable">
				<tr>
					<th class="staging-scheduler-title">
						<liferay-ui:message key="repeat-every" />:
					</th>
					<td class="staging-scheduler-content">
						<aui:input cssClass="number-input" inlineField="<%= false %>" inlineLabel="right" label="" maxlength="2" name="weeklyInterval" type="number" value="<%= weeklyInterval %>">
							<aui:validator name="digit" />
						</aui:input>
					</td>
				</tr>
				<tr>
					<th class="staging-scheduler-title">
						<liferay-ui:message key="repeat-on" />:
					</th>
					<td class="staging-scheduler-content">

						<%
						String[] days = CalendarUtil.getDays(locale);
						%>

						<div class="row weekdays">

							<%
							int firstDayOfWeek = cal.getFirstDayOfWeek();

							Weekday[] weekdaysArray = Weekday.values();

							Collections.rotate(Arrays.asList(weekdaysArray), -firstDayOfWeek);

							for (Weekday weekday : weekdaysArray) {
							%>

								<div class="col-md-3">
									<aui:input inlineLabel="right" label="<%= days[weekday.getCalendarWeekday() - 1] %>" name='<%= "weeklyDayPos" + weekday.getCalendarWeekday() %>' type="checkbox" value="<%= _getWeeklyDayPos(request, weekday.getCalendarWeekday(), recurrence) %>" />
								</div>

							<%
							}
							%>

						</div>
					</td>
				</tr>
			</tbody>

			<tbody class="<%= (recurrenceType != Recurrence.MONTHLY) ? "hide" : StringPool.BLANK %>" id="<portlet:namespace />recurrenceTypeMonthlyTable">
				<tr>
					<th class="staging-scheduler-title">
						<liferay-ui:message key="repeat-type" />:
					</th>
					<td class="staging-scheduler-content">
						<aui:input checked="<%= monthlyType == 0 %>" cssClass="input-container" id="monthlyTypeDayOfMonth" inlineField="<%= true %>" label="day-of-month" name="monthlyType" type="radio" value="0" />
						<aui:input checked="<%= monthlyType == 1 %>" cssClass="input-container" id="monthlyTypeDayOfWeek" inlineField="<%= true %>" label="day-of-week" name="monthlyType" type="radio" value="1" />
					</td>
				</tr>
				<tr class="hide" id="<portlet:namespace />schedulerMonthlyDayOfMonthTypeDay">
					<th class="staging-scheduler-title">
						<liferay-ui:message key="day" />:
					</th>
					<td class="staging-scheduler-content">
						<aui:input cssClass="number-input" label="" maxlength="2" name="monthlyDay0" type="number" value="<%= monthlyDay0 %>">
							<aui:validator name="digit" />
						</aui:input>
					</td>
				</tr>
				<tr class="hide" id="<portlet:namespace />schedulerMonthlyDayOfMonthTypeMonth">
					<th class="staging-scheduler-title">
						<liferay-ui:message key="recur-every" />:
					</th>
					<td class="staging-scheduler-content">
						<aui:input cssClass="number-input" label="" maxlength="2" name="monthlyInterval0" suffix="month" type="number" value="<%= monthlyInterval0 %>">
							<aui:validator name="digit" />
						</aui:input>
					</td>
				</tr>
				<tr class="hide" id="<portlet:namespace />schedulerMonthlyDayOfWeekTypeDay">
					<th class="staging-scheduler-title">
						<liferay-ui:message key="day" />:
					</th>
					<td class="staging-scheduler-content">
						<aui:select cssClass="input-container" inlineField="<%= true %>" inlineLabel="left" label="" name="monthlyPos" title="month-position" value="<%= monthlyPos %>">
							<aui:option label="first" value="1" />
							<aui:option label="second" value="2" />
							<aui:option label="third" value="3" />
							<aui:option label="fourth" value="4" />
							<aui:option label="last" value="-1" />
						</aui:select>

						<aui:select cssClass="input-container" inlineField="<%= true %>" label="" name="monthlyDay1" title="first-day-of-week" value="<%= monthlyDay1 %>">
							<aui:option label="<%= days[0] %>" value="<%= Calendar.SUNDAY %>" />
							<aui:option label="<%= days[1] %>" value="<%= Calendar.MONDAY %>" />
							<aui:option label="<%= days[2] %>" value="<%= Calendar.TUESDAY %>" />
							<aui:option label="<%= days[3] %>" value="<%= Calendar.WEDNESDAY %>" />
							<aui:option label="<%= days[4] %>" value="<%= Calendar.THURSDAY %>" />
							<aui:option label="<%= days[5] %>" value="<%= Calendar.FRIDAY %>" />
							<aui:option label="<%= days[6] %>" value="<%= Calendar.SATURDAY %>" />
						</aui:select>
					</td>
				</tr>
				<tr class="hide" id="<portlet:namespace />schedulerMonthlyDayOfWeekTypeMonth">
					<th class="staging-scheduler-title">
						<liferay-ui:message key="recur-every" />:
					</th>
					<td class="staging-scheduler-content">
						<aui:input cssClass="number-input" inlineField="<%= false %>" inlineLabel="left" label="" maxlength="2" name="monthlyInterval1" suffix="month" type="number" value="<%= monthlyInterval1 %>">
							<aui:validator name="digit" />
						</aui:input>
					</td>
				</tr>
			</tbody>

			<tbody class="<%= (recurrenceType != Recurrence.YEARLY) ? "hide" : StringPool.BLANK %>" id="<portlet:namespace />recurrenceTypeYearlyTable">
				<tr>
					<th class="staging-scheduler-title">
						<liferay-ui:message key="repeat-type" />:
					</th>
					<td class="staging-scheduler-content">
						<aui:input checked="<%= yearlyType == 0 %>" cssClass="input-container" id="yearlyTypeDayOfMonth" inlineField="<%= true %>" label="day-of-month" name="yearlyType" type="radio" value="0" />
						<aui:input checked="<%= yearlyType == 1 %>" cssClass="input-container" id="yearlyTypeDayOfWeek" inlineField="<%= true %>" label="day-of-week" name="yearlyType" type="radio" value="1" />
					</td>
				</tr>
				<tr class="hide" id="<portlet:namespace />schedulerYearlyDayOfMonthTypeDay">
					<th class="staging-scheduler-title">
						<liferay-ui:message key="day" />:
					</th>
					<td class="staging-scheduler-content">
						<aui:input cssClass="number-input" inlineField="<%= false %>" inlineLabel="right" label="" maxlength="2" name="yearlyDay0" type="number" value="<%= yearlyDay0 %>">
							<aui:validator name="digit" />
						</aui:input>
					</td>
				</tr>
				<tr class="hide" id="<portlet:namespace />schedulerYearlyDayOfMonthTypeMonth">
					<th class="staging-scheduler-title">
						<liferay-ui:message key="month" />:
					</th>
					<td class="staging-scheduler-content">
						<aui:select cssClass="input-container" inlineField="<%= false %>" inlineLabel="left" label="" name="yearlyMonth0" title="first-month-of-year">

							<%
							for (int i = 0; i < 12; i++) {
							%>

								<aui:option label="<%= months[i] %>" selected="<%= monthIds[i] == yearlyMonth0 %>" value="<%= monthIds[i] %>" />

							<%
							}
							%>

						</aui:select>
					</td>
				</tr>
				<tr class="hide" id="<portlet:namespace />schedulerYearlyDayOfMonthTypeYear">
					<th class="staging-scheduler-title">
						<liferay-ui:message key="year-s" />:
					</th>
					<td class="staging-scheduler-content">
						<aui:input cssClass="number-input" inlineField="<%= false %>" inlineLabel="right" label="" maxlength="2" name="yearlyInterval0" type="number" value="<%= yearlyInterval0 %>">
							<aui:validator name="digit" />
						</aui:input>
					</td>
				</tr>
				<tr class="hide" id="<portlet:namespace />schedulerYearlyDayOfWeekTypeDay">
					<th class="staging-scheduler-title">
						<liferay-ui:message key="day" />:
					</th>
					<td class="staging-scheduler-content">
						<aui:select cssClass="input-container" inlineField="<%= true %>" label="" name="yearlyPos" title="year-position">
							<aui:option label="first" selected="<%= yearlyPos == 1 %>" value="1" />
							<aui:option label="second" selected="<%= yearlyPos == 2 %>" value="2" />
							<aui:option label="third" selected="<%= yearlyPos == 3 %>" value="3" />
							<aui:option label="fourth" selected="<%= yearlyPos == 4 %>" value="4" />
							<aui:option label="last" selected="<%= yearlyPos == -1 %>" value="-1" />
						</aui:select>

						<aui:select cssClass="input-container" inlineField="<%= true %>" label="" name="yearlyDay1">
							<aui:option label="<%= days[0] %>" selected="<%= yearlyDay1 == Calendar.SUNDAY %>" value="<%= Calendar.SUNDAY %>" />
							<aui:option label="<%= days[1] %>" selected="<%= yearlyDay1 == Calendar.MONDAY %>" value="<%= Calendar.MONDAY %>" />
							<aui:option label="<%= days[2] %>" selected="<%= yearlyDay1 == Calendar.TUESDAY %>" value="<%= Calendar.TUESDAY %>" />
							<aui:option label="<%= days[3] %>" selected="<%= yearlyDay1 == Calendar.WEDNESDAY %>" value="<%= Calendar.WEDNESDAY %>" />
							<aui:option label="<%= days[4] %>" selected="<%= yearlyDay1 == Calendar.THURSDAY %>" value="<%= Calendar.THURSDAY %>" />
							<aui:option label="<%= days[5] %>" selected="<%= yearlyDay1 == Calendar.FRIDAY %>" value="<%= Calendar.FRIDAY %>" />
							<aui:option label="<%= days[6] %>" selected="<%= yearlyDay1 == Calendar.SATURDAY %>" value="<%= Calendar.SATURDAY %>" />
						</aui:select>
					</td>
				</tr>
				<tr class="hide" id="<portlet:namespace />schedulerYearlyDayOfWeekTypeMonth">
					<th class="staging-scheduler-title">
						<liferay-ui:message key="month" />:
					</th>
					<td class="staging-scheduler-content">
						<aui:select cssClass="input-container" inlineField="<%= false %>" label="" name="yearlyMonth1">

							<%
							for (int i = 0; i < 12; i++) {
							%>

								<aui:option label="<%= months[i] %>" selected="<%= monthIds[i] == yearlyMonth1 %>" value="<%= monthIds[i] %>" />

							<%
							}
							%>

						</aui:select>
					</td>
				</tr>
				<tr class="hide" id="<portlet:namespace />schedulerYearlyDayOfWeekTypeYear">
					<th class="staging-scheduler-title">
						<liferay-ui:message key="year-s" />:
					</th>
					<td class="staging-scheduler-content">
						<aui:input cssClass="number-input" label="" maxlength="2" name="yearlyInterval1" type="number" value="<%= yearlyInterval1 %>">
							<aui:validator name="digit" />
						</aui:input>
					</td>
				</tr>
			</tbody>

			<tbody class="<%= (recurrenceType != Recurrence.NO_RECURRENCE) ? "hide" : StringPool.BLANK %>" id="<portlet:namespace />recurrenceTypeNeverTable">
				<tr>
					<th class="staging-scheduler-title">
					</th>
					<td class="staging-scheduler-content">
						<liferay-ui:message key="do-not-repeat-this-event" />
					</td>
				</tr>
			</tbody>
		</table>

		<script>
			(function() {
				var tables = document.querySelectorAll(
					'#<portlet:namespace />recurrenceTypeDailyTable, #<portlet:namespace />recurrenceTypeMonthlyTable, #<portlet:namespace />recurrenceTypeNeverTable, #<portlet:namespace />recurrenceTypeWeeklyTable, #<portlet:namespace />recurrenceTypeYearlyTable'
				);
				var recurrenceTypeSelect = document.getElementById(
					'<portlet:namespace />recurrenceType'
				);

				if (recurrenceTypeSelect) {
					recurrenceTypeSelect.addEventListener('change', function(event) {
						var selectedTableId =
							'<portlet:namespace />' +
							recurrenceTypeSelect[recurrenceTypeSelect.selectedIndex].id +
							'Table';

						Array.prototype.forEach.call(tables, function(table) {
							if (table.id !== selectedTableId) {
								table.classList.add('hide');
							}
							else {
								table.classList.remove('hide');
							}
						});
					});
				}
			})();
		</script>

		<%!
		private boolean _getWeeklyDayPos(HttpServletRequest req, int day, Recurrence recurrence) {
			return ParamUtil.getBoolean(req, "weeklyDayPos" + day);
		}
		%>

	</li>
</ul>

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
		'<portlet:namespace />schedulerEndDateType'
	]);

	Liferay.Util.toggleRadio(
		'<portlet:namespace />monthlyTypeDayOfMonth',
		[
			'<portlet:namespace />schedulerMonthlyDayOfMonthTypeDay',
			'<portlet:namespace />schedulerMonthlyDayOfMonthTypeMonth'
		],
		[
			'<portlet:namespace />schedulerMonthlyDayOfWeekTypeDay',
			'<portlet:namespace />schedulerMonthlyDayOfWeekTypeMonth'
		]
	);

	Liferay.Util.toggleRadio(
		'<portlet:namespace />monthlyTypeDayOfWeek',
		[
			'<portlet:namespace />schedulerMonthlyDayOfWeekTypeDay',
			'<portlet:namespace />schedulerMonthlyDayOfWeekTypeMonth'
		],
		[
			'<portlet:namespace />schedulerMonthlyDayOfMonthTypeDay',
			'<portlet:namespace />schedulerMonthlyDayOfMonthTypeMonth'
		]
	);

	Liferay.Util.toggleRadio(
		'<portlet:namespace />yearlyTypeDayOfMonth',
		[
			'<portlet:namespace />schedulerYearlyDayOfMonthTypeDay',
			'<portlet:namespace />schedulerYearlyDayOfMonthTypeMonth',
			'<portlet:namespace />schedulerYearlyDayOfMonthTypeYear'
		],
		[
			'<portlet:namespace />schedulerYearlyDayOfWeekTypeDay',
			'<portlet:namespace />schedulerYearlyDayOfWeekTypeMonth',
			'<portlet:namespace />schedulerYearlyDayOfWeekTypeYear'
		]
	);

	Liferay.Util.toggleRadio(
		'<portlet:namespace />yearlyTypeDayOfWeek',
		[
			'<portlet:namespace />schedulerYearlyDayOfWeekTypeDay',
			'<portlet:namespace />schedulerYearlyDayOfWeekTypeMonth',
			'<portlet:namespace />schedulerYearlyDayOfWeekTypeYear'
		],
		[
			'<portlet:namespace />schedulerYearlyDayOfMonthTypeDay',
			'<portlet:namespace />schedulerYearlyDayOfMonthTypeMonth',
			'<portlet:namespace />schedulerYearlyDayOfMonthTypeYear'
		]
	);
</aui:script>