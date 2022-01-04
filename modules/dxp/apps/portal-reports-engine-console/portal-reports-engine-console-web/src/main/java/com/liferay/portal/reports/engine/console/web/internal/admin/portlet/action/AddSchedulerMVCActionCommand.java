/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.reports.engine.console.web.internal.admin.portlet.action;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cal.DayAndPosition;
import com.liferay.portal.kernel.cal.Duration;
import com.liferay.portal.kernel.cal.Recurrence;
import com.liferay.portal.kernel.cal.RecurrenceSerializer;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.reports.engine.console.constants.ReportsEngineConsolePortletKeys;
import com.liferay.portal.reports.engine.console.model.Definition;
import com.liferay.portal.reports.engine.console.model.Entry;
import com.liferay.portal.reports.engine.console.service.DefinitionService;
import com.liferay.portal.reports.engine.console.service.EntryService;
import com.liferay.portal.reports.engine.console.util.ReportsEngineConsoleUtil;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Gavin Wan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ReportsEngineConsolePortletKeys.REPORTS_ADMIN,
		"mvc.command.name=/reports_admin/add_scheduler"
	},
	service = MVCActionCommand.class
)
public class AddSchedulerMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long definitionId = ParamUtil.getLong(actionRequest, "definitionId");
		String format = ParamUtil.getString(actionRequest, "format");
		Calendar startCalendar = ReportsEngineConsoleUtil.getDate(
			actionRequest, "schedulerStartDate", true);
		String emailNotifications = ParamUtil.getString(
			actionRequest, "emailNotifications");
		String emailDelivery = ParamUtil.getString(
			actionRequest, "emailDelivery");
		String portletId = _portal.getPortletId(actionRequest);
		String generatedReportsURL = ParamUtil.getString(
			actionRequest, "generatedReportsURL");
		String reportName = ParamUtil.getString(actionRequest, "reportName");

		Date schedulerEndDate = null;

		int endDateType = ParamUtil.getInteger(actionRequest, "endDateType");

		if (endDateType == 1) {
			Calendar endCalendar = ReportsEngineConsoleUtil.getDate(
				actionRequest, "schedulerEndDate", true);

			schedulerEndDate = endCalendar.getTime();
		}

		int recurrenceType = ParamUtil.getInteger(
			actionRequest, "recurrenceType");

		String cronText = getCronText(
			actionRequest, startCalendar, true, recurrenceType);

		JSONArray entryReportParametersJSONArray =
			JSONFactoryUtil.createJSONArray();

		Definition definition = _definitionService.getDefinition(definitionId);

		JSONArray reportParametersJSONArray = JSONFactoryUtil.createJSONArray(
			definition.getReportParameters());

		for (int i = 0; i < reportParametersJSONArray.length(); i++) {
			JSONObject definitionReportParameterJSONObject =
				reportParametersJSONArray.getJSONObject(i);

			String key = definitionReportParameterJSONObject.getString("key");

			JSONObject entryReportParameterJSONObject = JSONUtil.put(
				"key", key);

			String value = StringPool.BLANK;

			DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
				"yyyy-MM-dd");

			String type = ParamUtil.getString(
				actionRequest, "useVariable" + key);

			if (type.equals("startDate")) {
				value = dateFormat.format(startCalendar.getTime());
			}
			else if (type.equals("endDate")) {
				if (schedulerEndDate != null) {
					value = dateFormat.format(schedulerEndDate.getTime());
				}
				else {
					value = StringPool.NULL;
				}
			}
			else {
				value = ParamUtil.getString(
					actionRequest, "parameterValue" + key);

				if (Validator.isNull(value)) {
					Calendar calendar = ReportsEngineConsoleUtil.getDate(
						actionRequest, key, false);

					value = dateFormat.format(calendar.getTime());
				}
			}

			entryReportParameterJSONObject.put("value", value);

			entryReportParametersJSONArray.put(entryReportParameterJSONObject);
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Entry.class.getName(), actionRequest);

		_entryService.addEntry(
			themeDisplay.getScopeGroupId(), definitionId, format, true,
			startCalendar.getTime(), schedulerEndDate,
			recurrenceType != Recurrence.NO_RECURRENCE, cronText,
			emailNotifications, emailDelivery, portletId, generatedReportsURL,
			reportName, entryReportParametersJSONArray.toString(),
			serviceContext);
	}

	protected String getCronText(
		ActionRequest actionRequest, Calendar startCalendar,
		boolean timeZoneSensitive, int recurrenceType) {

		Calendar calendar = null;

		if (timeZoneSensitive) {
			calendar = CalendarFactoryUtil.getCalendar();

			calendar.setTime(startCalendar.getTime());
		}
		else {
			calendar = (Calendar)startCalendar.clone();
		}

		Recurrence recurrence = new Recurrence(
			calendar, new Duration(1, 0, 0, 0), recurrenceType);

		recurrence.setWeekStart(Calendar.SUNDAY);

		if (recurrenceType == Recurrence.DAILY) {
			int dailyType = ParamUtil.getInteger(actionRequest, "dailyType");

			if (dailyType == 0) {
				int dailyInterval = ParamUtil.getInteger(
					actionRequest, "dailyInterval", 1);

				recurrence.setInterval(dailyInterval);
			}
			else {
				recurrence.setByDay(
					new DayAndPosition[] {
						new DayAndPosition(Calendar.MONDAY, 0),
						new DayAndPosition(Calendar.TUESDAY, 0),
						new DayAndPosition(Calendar.WEDNESDAY, 0),
						new DayAndPosition(Calendar.THURSDAY, 0),
						new DayAndPosition(Calendar.FRIDAY, 0)
					});
			}
		}
		else if (recurrenceType == Recurrence.WEEKLY) {
			int weeklyInterval = ParamUtil.getInteger(
				actionRequest, "weeklyInterval", 1);

			recurrence.setInterval(weeklyInterval);

			List<DayAndPosition> dayAndPositions = new ArrayList<>();

			_addWeeklyDayPos(actionRequest, dayAndPositions, Calendar.SUNDAY);
			_addWeeklyDayPos(actionRequest, dayAndPositions, Calendar.MONDAY);
			_addWeeklyDayPos(actionRequest, dayAndPositions, Calendar.TUESDAY);
			_addWeeklyDayPos(
				actionRequest, dayAndPositions, Calendar.WEDNESDAY);
			_addWeeklyDayPos(actionRequest, dayAndPositions, Calendar.THURSDAY);
			_addWeeklyDayPos(actionRequest, dayAndPositions, Calendar.FRIDAY);
			_addWeeklyDayPos(actionRequest, dayAndPositions, Calendar.SATURDAY);

			if (dayAndPositions.isEmpty()) {
				dayAndPositions.add(new DayAndPosition(Calendar.MONDAY, 0));
			}

			recurrence.setByDay(dayAndPositions.toArray(new DayAndPosition[0]));
		}
		else if (recurrenceType == Recurrence.MONTHLY) {
			int monthlyType = ParamUtil.getInteger(
				actionRequest, "monthlyType");

			if (monthlyType == 0) {
				int monthlyDay = ParamUtil.getInteger(
					actionRequest, "monthlyDay0", 1);

				recurrence.setByMonthDay(new int[] {monthlyDay});

				int monthlyInterval = ParamUtil.getInteger(
					actionRequest, "monthlyInterval0", 1);

				recurrence.setInterval(monthlyInterval);
			}
			else {
				int monthlyDay = ParamUtil.getInteger(
					actionRequest, "monthlyDay1");
				int monthlyPos = ParamUtil.getInteger(
					actionRequest, "monthlyPos");

				recurrence.setByDay(
					new DayAndPosition[] {
						new DayAndPosition(monthlyDay, monthlyPos)
					});

				int monthlyInterval = ParamUtil.getInteger(
					actionRequest, "monthlyInterval1", 1);

				recurrence.setInterval(monthlyInterval);
			}
		}
		else if (recurrenceType == Recurrence.YEARLY) {
			int yearlyType = ParamUtil.getInteger(actionRequest, "yearlyType");

			if (yearlyType == 0) {
				int yearlyMonth = ParamUtil.getInteger(
					actionRequest, "yearlyMonth0");

				recurrence.setByMonth(new int[] {yearlyMonth});

				int yearlyDay = ParamUtil.getInteger(
					actionRequest, "yearlyDay0", 1);

				recurrence.setByMonthDay(new int[] {yearlyDay});

				int yearlyInterval = ParamUtil.getInteger(
					actionRequest, "yearlyInterval0", 1);

				recurrence.setInterval(yearlyInterval);
			}
			else {
				int yearlyDay = ParamUtil.getInteger(
					actionRequest, "yearlyDay1");
				int yearlyPos = ParamUtil.getInteger(
					actionRequest, "yearlyPos");

				recurrence.setByDay(
					new DayAndPosition[] {
						new DayAndPosition(yearlyDay, yearlyPos)
					});

				int yearlyMonth = ParamUtil.getInteger(
					actionRequest, "yearlyMonth1");

				recurrence.setByMonth(new int[] {yearlyMonth});

				int yearlyInterval = ParamUtil.getInteger(
					actionRequest, "yearlyInterval1", 1);

				recurrence.setInterval(yearlyInterval);
			}
		}

		return RecurrenceSerializer.toCronText(recurrence);
	}

	private void _addWeeklyDayPos(
		ActionRequest actionRequest, List<DayAndPosition> dayAndPositions,
		int day) {

		boolean weeklyDayPos = ParamUtil.getBoolean(
			actionRequest, "weeklyDayPos" + day);

		if (weeklyDayPos) {
			dayAndPositions.add(new DayAndPosition(day, 0));
		}
	}

	@Reference
	private DefinitionService _definitionService;

	@Reference
	private EntryService _entryService;

	@Reference
	private Portal _portal;

}