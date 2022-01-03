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
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Edward Han
 * @author Máté Thurzó
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

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				_updateRuleGroup(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				_deleteRuleGroups(actionRequest);
			}
			else if (cmd.equals(Constants.COPY)) {
				_copyRuleGroup(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchRuleGroupException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				throw exception;
			}
		}
	}

	@Reference(unbind = "-")
	protected void setMDRRuleGroupService(
		MDRRuleGroupService mdrRuleGroupService) {

		_mdrRuleGroupService = mdrRuleGroupService;
	}

	private MDRRuleGroup _copyRuleGroup(ActionRequest actionRequest)
		throws Exception {

		long ruleGroupId = ParamUtil.getLong(actionRequest, "ruleGroupId");

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		return _mdrRuleGroupService.copyRuleGroup(
			ruleGroupId, groupId, serviceContext);
	}

	private void _deleteRuleGroups(ActionRequest actionRequest)
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

	private String _getAddOrCopyRedirect(
		ActionRequest actionRequest, ActionResponse actionResponse,
		MDRRuleGroup ruleGroup) {

		return PortletURLBuilder.createRenderURL(
			_portal.getLiferayPortletResponse(actionResponse)
		).setMVCRenderCommandName(
			"/mobile_device_rules/edit_rule_group"
		).setRedirect(
			ParamUtil.getString(actionRequest, "redirect")
		).setParameter(
			"ruleGroupId", ruleGroup.getRuleGroupId()
		).buildString();
	}

	private MDRRuleGroup _updateRuleGroup(ActionRequest actionRequest)
		throws Exception {

		long ruleGroupId = ParamUtil.getLong(actionRequest, "ruleGroupId");

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		MDRRuleGroup ruleGroup = null;

		if (ruleGroupId <= 0) {
			long groupId = ParamUtil.getLong(actionRequest, "groupId");

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