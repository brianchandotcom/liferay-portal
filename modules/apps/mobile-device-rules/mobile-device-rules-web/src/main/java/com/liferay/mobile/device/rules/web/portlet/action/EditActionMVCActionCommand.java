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

package com.liferay.mobile.device.rules.web.portlet.action;

import com.liferay.mobile.device.rules.action.ActionHandler;
import com.liferay.mobile.device.rules.action.ActionHandlerManagerUtil;
import com.liferay.mobile.device.rules.constants.MDRPortletKeys;
import com.liferay.mobile.device.rules.exception.ActionTypeException;
import com.liferay.mobile.device.rules.exception.NoSuchActionException;
import com.liferay.mobile.device.rules.exception.NoSuchRuleGroupException;
import com.liferay.mobile.device.rules.service.MDRActionService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

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
		"mvc.command.name=/mobile_device_rules/edit_action"
	},
	service = MVCActionCommand.class
)
public class EditActionMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteActions(ActionRequest actionRequest) throws Exception {
		long[] deleteActionIds = null;

		long actionId = ParamUtil.getLong(actionRequest, "actionId");

		if (actionId > 0) {
			deleteActionIds = new long[] {actionId};
		}
		else {
			deleteActionIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "actionIds"), 0L);
		}

		for (long deleteActionId : deleteActionIds) {
			_mdrActionService.deleteAction(deleteActionId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateAction(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteActions(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof ActionTypeException ||
					 e instanceof NoSuchActionException ||
					 e instanceof NoSuchRuleGroupException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

	@Reference(unbind = "-")
	protected void setMDRActionService(MDRActionService mdrActionService) {
		_mdrActionService = mdrActionService;
	}

	protected void updateAction(ActionRequest actionRequest) throws Exception {
		long actionId = ParamUtil.getLong(actionRequest, "actionId");

		long ruleGroupInstanceId = ParamUtil.getLong(
			actionRequest, "ruleGroupInstanceId");
		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");
		String type = ParamUtil.getString(actionRequest, "type");

		ActionHandler actionHandler = ActionHandlerManagerUtil.getActionHandler(
			type);

		if (actionHandler == null) {
			throw new ActionTypeException();
		}

		UnicodeProperties typeSettingsProperties =
			ActionUtil.getTypeSettingsProperties(
				actionRequest, actionHandler.getPropertyNames());

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		if (actionId <= 0) {
			_mdrActionService.addAction(
				ruleGroupInstanceId, nameMap, descriptionMap, type,
				typeSettingsProperties, serviceContext);
		}
		else {
			_mdrActionService.updateAction(
				actionId, nameMap, descriptionMap, type, typeSettingsProperties,
				serviceContext);
		}
	}

	private volatile MDRActionService _mdrActionService;

}