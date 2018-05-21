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

package com.liferay.portal.reports.engine.console.web.admin.portlet.action;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
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
		"mvc.command.name=generateReport"
	},
	service = MVCActionCommand.class
)
public class GenerateReportMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long definitionId = ParamUtil.getLong(actionRequest, "definitionId");

		String format = ParamUtil.getString(actionRequest, "format");
		String emailNotifications = ParamUtil.getString(
			actionRequest, "emailNotifications");
		String emailDelivery = ParamUtil.getString(
			actionRequest, "emailDelivery");
		String portletId = _portal.getPortletId(actionRequest);
		String generatedReportsURL = ParamUtil.getString(
			actionRequest, "generatedReportsURL");
		String reportName = ParamUtil.getString(actionRequest, "reportName");

		JSONArray entryReportParametersJSONArray =
			JSONFactoryUtil.createJSONArray();
		JSONArray reportParametersJSONArray = JSONFactoryUtil.createJSONArray();

		Definition definition = _definitionService.getDefinition(definitionId);

		if (Validator.isNotNull(definition.getReportParameters())) {
			reportParametersJSONArray = JSONFactoryUtil.createJSONArray(
				definition.getReportParameters());
		}

		for (int i = 0; i < reportParametersJSONArray.length(); i++) {
			JSONObject definitionReportParameterJSONObject =
				reportParametersJSONArray.getJSONObject(i);

			String key = definitionReportParameterJSONObject.getString("key");

			JSONObject entryReportParameterJSONObject =
				JSONFactoryUtil.createJSONObject();

			entryReportParameterJSONObject.put("key", key);

			String value = ParamUtil.getString(
				actionRequest, "parameterValue" + key);

			String type = definitionReportParameterJSONObject.getString("type");

			if (type.equals("date")) {
				Calendar calendar = ReportsEngineConsoleUtil.getDate(
					actionRequest, key, true);

				DateFormat dateFormat =
					DateFormatFactoryUtil.getSimpleDateFormat("yyyy-MM-dd");

				value = dateFormat.format(calendar.getTime());
			}

			entryReportParameterJSONObject.put("value", value);

			entryReportParametersJSONArray.put(entryReportParameterJSONObject);
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Entry.class.getName(), actionRequest);

		_entryService.addEntry(
			themeDisplay.getScopeGroupId(), definitionId, format, false, null,
			null, false, null, emailNotifications, emailDelivery, portletId,
			generatedReportsURL, reportName,
			entryReportParametersJSONArray.toString(), serviceContext);
	}

	@Reference
	private DefinitionService _definitionService;

	@Reference
	private EntryService _entryService;

	@Reference
	private Portal _portal;

}