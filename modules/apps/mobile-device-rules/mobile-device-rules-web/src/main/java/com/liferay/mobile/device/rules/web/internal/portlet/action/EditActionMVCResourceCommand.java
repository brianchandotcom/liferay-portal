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

package com.liferay.mobile.device.rules.web.internal.portlet.action;

import com.liferay.mobile.device.rules.constants.MDRPortletKeys;
import com.liferay.mobile.device.rules.model.MDRAction;
import com.liferay.mobile.device.rules.service.MDRActionService;
import com.liferay.mobile.device.rules.web.internal.constants.MDRWebKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + MDRPortletKeys.MOBILE_DEVICE_RULES,
		"mvc.command.name=/mobile_device_rules/edit_action"
	},
	service = MVCResourceCommand.class
)
public class EditActionMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		long actionId = ParamUtil.getLong(resourceRequest, "actionId");

		MDRAction action = _mdrActionService.fetchAction(actionId);

		resourceRequest.setAttribute(
			MDRWebKeys.MOBILE_DEVICE_RULES_RULE_GROUP_ACTION, action);

		String type = ParamUtil.getString(resourceRequest, "type");

		ActionUtil.includeEditorJSP(
			getPortletConfig(resourceRequest), resourceRequest,
			resourceResponse, ActionUtil.getActionEditorJSP(type));
	}

	@Reference(unbind = "-")
	protected void setMDRActionService(MDRActionService mdrActionService) {
		_mdrActionService = mdrActionService;
	}

	private MDRActionService _mdrActionService;

}