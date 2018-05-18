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

package com.liferay.commerce.dashboard.web.internal.portlet.action;

import com.liferay.commerce.dashboard.web.internal.constants.CommerceDashboardPortletKeys;
import com.liferay.commerce.dashboard.web.internal.util.CommerceDashboardUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceDashboardPortletKeys.COMMERCE_DASHBOARD_DATE_RANGE_SELECTOR,
		"mvc.command.name=editCommerceDashboardDateRange"
	},
	service = MVCActionCommand.class
)
public class EditCommerceDashboardDateRangeMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		CommerceDashboardUtil.setSessionInteger(actionRequest, "endDateDay");
		CommerceDashboardUtil.setSessionInteger(actionRequest, "endDateMonth");
		CommerceDashboardUtil.setSessionInteger(actionRequest, "endDateYear");
		CommerceDashboardUtil.setSessionInteger(actionRequest, "startDateDay");
		CommerceDashboardUtil.setSessionInteger(
			actionRequest, "startDateMonth");
		CommerceDashboardUtil.setSessionInteger(actionRequest, "startDateYear");

		hideDefaultSuccessMessage(actionRequest);
	}

}