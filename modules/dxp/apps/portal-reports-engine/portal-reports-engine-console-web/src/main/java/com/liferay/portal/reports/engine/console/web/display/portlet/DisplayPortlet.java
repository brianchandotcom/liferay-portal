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