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

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.reports.engine.console.constants.ReportsEngineConsolePortletKeys;
import com.liferay.portal.reports.engine.console.model.Source;
import com.liferay.portal.reports.engine.console.service.SourceService;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gavin Wan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ReportsEngineConsolePortletKeys.REPORTS_ADMIN,
		"mvc.command.name=editDataSource"
	},
	service = MVCActionCommand.class
)
public class EditDataSourceMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long sourceId = ParamUtil.getLong(actionRequest, "sourceId");

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		String driverClassName = ParamUtil.getString(
			actionRequest, "driverClassName");
		String driverUrl = ParamUtil.getString(actionRequest, "driverUrl");
		String driverUserName = ParamUtil.getString(
			actionRequest, "driverUserName");
		String driverPassword = ParamUtil.getString(
			actionRequest, "driverPassword");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Source.class.getName(), actionRequest);

		if (sourceId <= 0) {
			_sourceService.addSource(
				themeDisplay.getScopeGroupId(), nameMap, driverClassName,
				driverUrl, driverUserName, driverPassword, serviceContext);
		}
		else {
			_sourceService.updateSource(
				sourceId, nameMap, driverClassName, driverUrl, driverUserName,
				driverPassword, serviceContext);
		}
	}

	@Reference
	private SourceService _sourceService;

}