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