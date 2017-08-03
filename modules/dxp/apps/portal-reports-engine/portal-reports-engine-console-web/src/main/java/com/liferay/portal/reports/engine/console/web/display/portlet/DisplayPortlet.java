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

package com.liferay.portal.reports.engine.console.web.display.portlet;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.reports.engine.console.constants.ReportsEngineConsolePortletKeys;
import com.liferay.portal.reports.engine.console.web.admin.portlet.AdminPortlet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gavin Wan
 */
@Component(
	immediate = true,
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
		"javax.portlet.supports.mime-type=text/html"
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

		if (!actionName.equals("archiveRequest") &&
			!actionName.equals("deleteReport") &&
			!actionName.equals("deliverReport") &&
			!actionName.equals("unscheduleReportRequest")) {

			return false;
		}

		return super.callActionMethod(actionRequest, actionResponse);
	}

}