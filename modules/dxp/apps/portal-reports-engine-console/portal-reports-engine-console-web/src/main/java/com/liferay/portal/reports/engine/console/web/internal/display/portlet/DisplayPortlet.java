/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine.console.web.internal.display.portlet;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.reports.engine.console.constants.ReportsEngineConsolePortletKeys;
import com.liferay.portal.reports.engine.console.web.internal.admin.portlet.AdminPortlet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gavin Wan
 */
@Component(
	property = {
		"com.liferay.portlet.css-class-wrapper=reports-portlet",
		"com.liferay.portlet.display-category=category.bi",
		"com.liferay.portlet.footer-portlet-javascript=/admin/js/main.js",
		"com.liferay.portlet.header-portlet-css=/admin/css/main.css",
		"com.liferay.portlet.icon=/icons/display.png",
		"javax.portlet.display-name=Report Display",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.copy-request-parameters=true",
		"javax.portlet.init-param.mvc-action-command-package-prefix=com.liferay.portal.reports.engine.console.web.admin.portlet.action",
		"javax.portlet.init-param.view-template=/display/reports_display.jsp",
		"javax.portlet.name=" + ReportsEngineConsolePortletKeys.DISPLAY_REPORTS,
		"javax.portlet.portlet-info.keywords=Reports Display",
		"javax.portlet.portlet-info.short-title=Reports Display",
		"javax.portlet.portlet-info.title=Reports Display",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user",
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class DisplayPortlet extends AdminPortlet {

	@Override
	protected boolean callActionMethod(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		String actionName = ParamUtil.getString(
			actionRequest, ActionRequest.ACTION_NAME);

		if (!actionName.equals("/reports_admin/archive_request") &&
			!actionName.equals("/reports_admin/delete_report") &&
			!actionName.equals("/reports_admin/deliver_report") &&
			!actionName.equals("/reports_admin/unschedule_report_request")) {

			return false;
		}

		return super.callActionMethod(actionRequest, actionResponse);
	}

}