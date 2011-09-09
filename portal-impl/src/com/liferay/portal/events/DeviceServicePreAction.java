/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mobile.device.Device;
import com.liferay.portal.kernel.mobile.device.DeviceDetectionUtil;
import com.liferay.portal.kernel.mobile.device.UnknownDevice;
import com.liferay.portal.kernel.mobile.device.rulegroup.ActionHandlerManagerUtil;
import com.liferay.portal.kernel.mobile.device.rulegroup.RuleGroupProcessorUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.mobiledevicerules.model.MDRAction;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;
import com.liferay.portlet.mobiledevicerules.service.MDRActionLocalServiceUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Edward Han
 */
public class DeviceServicePreAction extends Action {

	public void run(HttpServletRequest request, HttpServletResponse response)
		throws ActionException {

		HttpSession session = request.getSession();

		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(
			WebKeys.THEME_DISPLAY);

		// Device

		Device device = (Device)session.getAttribute(WebKeys.DEVICE);

		if (device == null) {
			device = DeviceDetectionUtil.detectDevice(request);

			session.setAttribute(WebKeys.DEVICE, device);
		}

		themeDisplay.setDevice(device);

		if (UnknownDevice.getInstance().equals(device)) {
			return;
		}

		//	Rule Group

		MDRRuleGroupInstance ruleGroupInstance = null;

		try {
			ruleGroupInstance = RuleGroupProcessorUtil.evaluateRuleGroups(
				themeDisplay);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"RuleGroup evaluation returned RuleGroupInstance: " +
						ruleGroupInstance.getRuleGroupInstanceId());
			}
		}
		catch (SystemException e) {
			throw new ActionException(
				"Error while retrieving rule group", e);
		}

		themeDisplay.setRuleGroupInstance(ruleGroupInstance);

		//	Apply actions

		if (ruleGroupInstance != null) {
			try {
				List<MDRAction> actions = MDRActionLocalServiceUtil.getActions(
					ruleGroupInstance.getRuleGroupInstanceId());

				ActionHandlerManagerUtil.applyActions(
					actions, request, response);
			}
			catch (Exception e) {
				throw new ActionException(
					"Error while applying device profile", e);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DeviceServicePreAction.class);

}