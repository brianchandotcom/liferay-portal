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
import com.liferay.mobile.device.rules.model.MDRRuleGroup;
import com.liferay.mobile.device.rules.service.MDRRuleGroupService;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Locale;
import java.util.Map;

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
		"mvc.command.name=/mobile_device_rules/edit_rule_group"
	},
	service = MVCActionCommand.class
)
public class EditRuleGroupMVCActionCommand extends BaseMVCActionCommand {

	protected MDRRuleGroup copyRuleGroup(ActionRequest actionRequest)
		throws Exception {

		long ruleGroupId = ParamUtil.getLong(actionRequest, "ruleGroupId");

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		return _mdrRuleGroupService.copyRuleGroup(
			ruleGroupId, groupId, serviceContext);
	}

	protected void deleteRuleGroups(ActionRequest actionRequest)
		throws Exception {

		long[] deleteRuleGroupIds = null;

		long ruleGroupId = ParamUtil.getLong(actionRequest, "ruleGroupId");

		if (ruleGroupId > 0) {
			deleteRuleGroupIds = new long[] {ruleGroupId};
		}
		else {
			deleteRuleGroupIds = ParamUtil.getLongValues(
				actionRequest, "rowIds");
		}

		for (long deleteRuleGroupId : deleteRuleGroupIds) {
			_mdrRuleGroupService.deleteRuleGroup(deleteRuleGroupId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateRuleGroup(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteRuleGroups(actionRequest);
			}
			else if (cmd.equals(Constants.COPY)) {
				copyRuleGroup(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
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

	protected String getAddOrCopyRedirect(
		ActionRequest actionRequest, ActionResponse actionResponse,
		MDRRuleGroup ruleGroup) {

		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(actionResponse);

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/mobile_device_rules/edit_rule_group");

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		portletURL.setParameter("redirect", redirect);

		portletURL.setParameter(
			"ruleGroupId", String.valueOf(ruleGroup.getRuleGroupId()));

		return portletURL.toString();
	}

	@Reference(unbind = "-")
	protected void setMDRRuleGroupService(
		MDRRuleGroupService mdrRuleGroupService) {

		_mdrRuleGroupService = mdrRuleGroupService;
	}

	protected MDRRuleGroup updateRuleGroup(ActionRequest actionRequest)
		throws Exception {

		long ruleGroupId = ParamUtil.getLong(actionRequest, "ruleGroupId");

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		MDRRuleGroup ruleGroup = null;

		if (ruleGroupId <= 0) {
			ruleGroup = _mdrRuleGroupService.addRuleGroup(
				groupId, nameMap, descriptionMap, serviceContext);
		}
		else {
			ruleGroup = _mdrRuleGroupService.updateRuleGroup(
				ruleGroupId, nameMap, descriptionMap, serviceContext);
		}

		return ruleGroup;
	}

	private MDRRuleGroupService _mdrRuleGroupService;

	@Reference
	private Portal _portal;

}