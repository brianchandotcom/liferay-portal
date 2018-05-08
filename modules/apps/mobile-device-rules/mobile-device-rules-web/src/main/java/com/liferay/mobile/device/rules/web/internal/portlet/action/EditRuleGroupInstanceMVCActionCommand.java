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
import com.liferay.mobile.device.rules.exception.NoSuchRuleGroupException;
import com.liferay.mobile.device.rules.service.MDRRuleGroupInstanceService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Edward Han
 * @author Mate Thurzo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + MDRPortletKeys.MOBILE_DEVICE_RULES,
		"mvc.command.name=/mobile_device_rules/edit_rule_group_instance"
	},
	service = MVCActionCommand.class
)
public class EditRuleGroupInstanceMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteRuleGroupInstance(ActionRequest actionRequest)
		throws PortalException {

		long ruleGroupInstanceId = ParamUtil.getLong(
			actionRequest, "ruleGroupInstanceId");

		_mdrRuleGroupInstanceService.deleteRuleGroupInstance(
			ruleGroupInstanceId);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteRuleGroupInstance(actionRequest);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				updateRuleGroupInstancesPriorities(actionRequest);
			}

			sendRedirect(
				actionRequest, actionResponse,
				getRedirect(actionRequest, actionResponse));
		}
		catch (Exception e) {
			if (e instanceof NoSuchRuleGroupException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				throw e;
			}
		}
	}

	protected String getRedirect(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(actionResponse);

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName",
			"/mobile_device_rules/edit_rule_group_instance");

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		portletURL.setParameter("redirect", redirect);

		return portletURL.toString();
	}

	@Reference(unbind = "-")
	protected void setMDRRuleGroupInstanceService(
		MDRRuleGroupInstanceService mdrRuleGroupInstanceService) {

		_mdrRuleGroupInstanceService = mdrRuleGroupInstanceService;
	}

	protected void updateRuleGroupInstancesPriorities(
			ActionRequest actionRequest)
		throws PortalException {

		String ruleGroupsInstancesJSON = ParamUtil.getString(
			actionRequest, "ruleGroupsInstancesJSON");

		JSONArray ruleGroupsInstancesJSONArray =
			JSONFactoryUtil.createJSONArray(ruleGroupsInstancesJSON);

		for (int i = 0; i < ruleGroupsInstancesJSONArray.length(); i++) {
			JSONObject ruleGroupInstanceJSONObject =
				ruleGroupsInstancesJSONArray.getJSONObject(i);

			long ruleGroupInstanceId = ruleGroupInstanceJSONObject.getLong(
				"ruleGroupInstanceId");

			int priority = ruleGroupInstanceJSONObject.getInt("priority");

			_mdrRuleGroupInstanceService.updateRuleGroupInstance(
				ruleGroupInstanceId, priority);
		}
	}

	private MDRRuleGroupInstanceService _mdrRuleGroupInstanceService;

	@Reference
	private Portal _portal;

}