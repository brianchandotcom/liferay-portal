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

import com.liferay.mobile.device.rules.action.ActionHandler;
import com.liferay.mobile.device.rules.action.ActionHandlerManagerUtil;
import com.liferay.mobile.device.rules.constants.MDRPortletKeys;
import com.liferay.mobile.device.rules.exception.ActionTypeException;
import com.liferay.mobile.device.rules.exception.NoSuchActionException;
import com.liferay.mobile.device.rules.exception.NoSuchRuleGroupException;
import com.liferay.mobile.device.rules.service.MDRActionService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

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
		"mvc.command.name=/mobile_device_rules/edit_action"
	},
	service = MVCActionCommand.class
)
public class EditActionMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				_updateAction(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				_deleteActions(actionRequest);
			}
		}
		catch (Exception exception) {
			if (exception instanceof PrincipalException) {
				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (exception instanceof ActionTypeException ||
					 exception instanceof NoSuchActionException ||
					 exception instanceof NoSuchRuleGroupException) {

				SessionErrors.add(actionRequest, exception.getClass());
			}
			else {
				throw exception;
			}
		}
	}

	@Reference(unbind = "-")
	protected void setMDRActionService(MDRActionService mdrActionService) {
		_mdrActionService = mdrActionService;
	}

	private void _deleteActions(ActionRequest actionRequest) throws Exception {
		long[] deleteActionIds = null;

		long actionId = ParamUtil.getLong(actionRequest, "actionId");

		if (actionId > 0) {
			deleteActionIds = new long[] {actionId};
		}
		else {
			deleteActionIds = ParamUtil.getLongValues(actionRequest, "rowIds");
		}

		for (long deleteActionId : deleteActionIds) {
			_mdrActionService.deleteAction(deleteActionId);
		}
	}

	private void _updateAction(ActionRequest actionRequest) throws Exception {
		String type = ParamUtil.getString(actionRequest, "type");

		ActionHandler actionHandler = ActionHandlerManagerUtil.getActionHandler(
			type);

		if (actionHandler == null) {
			throw new ActionTypeException();
		}

		long actionId = ParamUtil.getLong(actionRequest, "actionId");

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");

		UnicodeProperties typeSettingsUnicodeProperties =
			ActionUtil.getTypeSettingsUnicodeProperties(
				actionRequest, actionHandler.getPropertyNames());

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		if (actionId <= 0) {
			long ruleGroupInstanceId = ParamUtil.getLong(
				actionRequest, "ruleGroupInstanceId");

			_mdrActionService.addAction(
				ruleGroupInstanceId, nameMap, descriptionMap, type,
				typeSettingsUnicodeProperties, serviceContext);
		}
		else {
			_mdrActionService.updateAction(
				actionId, nameMap, descriptionMap, type,
				typeSettingsUnicodeProperties, serviceContext);
		}
	}

	private MDRActionService _mdrActionService;

}