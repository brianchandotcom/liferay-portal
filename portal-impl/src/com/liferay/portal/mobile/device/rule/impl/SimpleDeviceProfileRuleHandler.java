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

package com.liferay.portal.mobile.device.rule.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mobile.device.Device;
import com.liferay.portal.kernel.mobile.device.action.DeviceProfileActionHandler;
import com.liferay.portal.kernel.mobile.device.rule.DeviceProfileRuleHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.mobile.model.DeviceProfileAction;
import com.liferay.portal.mobile.model.DeviceProfileRule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;

/**
 * @author Edward Han
 */
public class SimpleDeviceProfileRuleHandler
	implements DeviceProfileRuleHandler {

	public static final String PARAMETER_OS = "os";
	public static final String PARAMETER_TABLET = "tablet";

	public void evaluateDeviceProfileRule(
			DeviceProfileRule deviceProfileRule, Device device,
			HttpServletRequest request, HttpServletResponse response)
		throws PortalException, SystemException {

		boolean result = evaluateRule(deviceProfileRule, device);

		if (result) {
			Collection<DeviceProfileAction> deviceProfileActions =
				deviceProfileRule.getDeviceProfileActions();

			for (DeviceProfileAction deviceProfileAction :
				deviceProfileActions) {

				applyAction(deviceProfileAction, request, response);
			}
		}
	}

	public String getType() {
		return SimpleDeviceProfileRuleHandler.class.getName();
	}

	public void setDeviceProfileActionHandlers(
		Map<String, DeviceProfileActionHandler> deviceProfileActionHandlers) {

		_deviceProfileActionHandlers = deviceProfileActionHandlers;
	}

	protected void applyAction(
			DeviceProfileAction deviceProfileAction,
			HttpServletRequest request, HttpServletResponse response)
		throws PortalException, SystemException {

		DeviceProfileActionHandler deviceProfileActionHandler =
			_deviceProfileActionHandlers.get(deviceProfileAction.getType());

		if (deviceProfileActionHandler != null) {
			deviceProfileActionHandler.applyDeviceAction(
				request, response, deviceProfileAction);
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No DeviceActionHandler registered for type: " +
						deviceProfileAction.getType());
			}
		}
	}

	protected boolean evaluateRule(
		DeviceProfileRule deviceProfileRule, Device device) {

		UnicodeProperties ruleTypeSettings = deviceProfileRule.
			getRuleTypeSettingsProperties();

		String os = ruleTypeSettings.get(PARAMETER_OS);
		String tablet = ruleTypeSettings.get(PARAMETER_TABLET);

		boolean result = true;

		if (os != null) {
			result = result && os.equals(device.getOS());
		}

		if (tablet != null) {
			boolean isTablet = GetterUtil.get(tablet, false);

			result = result && (isTablet == device.isTablet());
		}

		return result;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SimpleDeviceProfileRuleHandler.class);

	private Map<String, DeviceProfileActionHandler>
		_deviceProfileActionHandlers;
}