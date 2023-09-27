/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine.console.web.internal.admin.portlet.action;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cal.Recurrence;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.scheduler.CronTextUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
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

import java.util.Calendar;
import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Gavin Wan
 */
@Component(
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

		String cronText = CronTextUtil.getCronText(
			actionRequest, startCalendar, true, recurrenceType);

		JSONArray entryReportParametersJSONArray =
			_jsonFactory.createJSONArray();

		Definition definition = _definitionService.getDefinition(definitionId);

		JSONArray reportParametersJSONArray = _jsonFactory.createJSONArray(
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

	@Reference
	private DefinitionService _definitionService;

	@Reference
	private EntryService _entryService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}