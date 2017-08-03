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