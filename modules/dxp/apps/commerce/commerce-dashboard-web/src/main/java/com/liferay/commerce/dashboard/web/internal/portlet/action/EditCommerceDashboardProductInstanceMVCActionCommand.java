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
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceDashboardPortletKeys.COMMERCE_DASHBOARD_PRODUCT_INSTANCE_SELECTOR,
		"mvc.command.name=editCommerceDashboardProductInstance"
	},
	service = MVCActionCommand.class
)
public class EditCommerceDashboardProductInstanceMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long cpInstanceId = ParamUtil.getLong(actionRequest, "cpInstanceId");

		Map<Long, Boolean> cpInstanceIds = CommerceDashboardUtil.getSessionMap(
			actionRequest, "cpInstanceIds");

		if (cpInstanceIds.isEmpty()) {
			cpInstanceIds = new LinkedHashMap<>();
		}

		if (cmd.equals(Constants.ADD) || cmd.equals(Constants.VIEW)) {
			cpInstanceIds.put(cpInstanceId, true);
		}
		else if (cmd.equals(Constants.DEACTIVATE)) {
			cpInstanceIds.put(cpInstanceId, false);
		}
		else if (cmd.equals(Constants.REMOVE)) {
			cpInstanceIds.remove(cpInstanceId);
		}

		CommerceDashboardUtil.setSessionValue(
			actionRequest, "cpInstanceIds", cpInstanceIds);

		hideDefaultSuccessMessage(actionRequest);
	}

}