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

package com.liferay.portal.mobile.device;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mobile.device.Device;
import com.liferay.portal.kernel.mobile.device.DeviceProfileProcessor;
import com.liferay.portal.kernel.mobile.device.action.DeviceProfileActionHandler;
import com.liferay.portal.kernel.mobile.device.rule.DeviceProfileRuleHandler;
import com.liferay.portal.mobile.model.DeviceProfile;
import com.liferay.portal.mobile.model.DeviceProfileRule;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Edward Han
 */
public class DefaultDeviceProfileProcessorImpl
	implements DeviceProfileProcessor {

	public void applyDeviceProfile(
			HttpServletRequest request, HttpServletResponse response)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay) request.
			getAttribute(WebKeys.THEME_DISPLAY);

		Device device = themeDisplay.getDevice();

		DeviceProfile deviceProfile = themeDisplay.getDeviceProfile();

		if (device != null && deviceProfile != null) {
			Collection<DeviceProfileRule> deviceProfileRules =
				deviceProfile.getDeviceProfileRules();

			for (DeviceProfileRule deviceProfileRule : deviceProfileRules) {
				processDeviceProfileRule(
					deviceProfileRule, device, request, response);
			}
		}
	}

	public DeviceProfileActionHandler getDeviceProfileActionHandler(
		String actionType) {

		return _deviceProfileActionHandler.get(actionType);
	}

	public DeviceProfileRuleHandler getDeviceProfileRuleHandler(
		String ruleType) {

		return _deviceProfileRuleHandlers.get(ruleType);
	}

	public Collection<DeviceProfileActionHandler>
		getDeviceProfileActionHandlers() {

		return Collections.unmodifiableCollection(
			_deviceProfileActionHandler.values());
	}

	public Collection<DeviceProfileRuleHandler> getDeviceProfileRuleHandlers() {
		return Collections.unmodifiableCollection(
			_deviceProfileRuleHandlers.values());
	}

	public void registerDeviceActionHandler(
		DeviceProfileActionHandler deviceActionHandler) {

		DeviceProfileActionHandler oldDeviceActionHandler =
			_deviceProfileActionHandler.put(
				deviceActionHandler.getType(), deviceActionHandler);

		if (oldDeviceActionHandler != null) {
			if (_log.isWarnEnabled()) {
				_log.warn("DeviceProfileActionHandler with key: " +
					deviceActionHandler.getType() +
					" has already been registering. Replacing with " +
					deviceActionHandler.getType());
			}
		}
	}

	public void registerDeviceRuleHandler(
		DeviceProfileRuleHandler deviceProfileRuleHandler) {

		deviceProfileRuleHandler.setDeviceProfileActionHandlers(
			Collections.unmodifiableMap(_deviceProfileActionHandler));

		DeviceProfileRuleHandler oldDeviceRuleHandler =
			_deviceProfileRuleHandlers.put(
				deviceProfileRuleHandler.getType(), deviceProfileRuleHandler);

		if (oldDeviceRuleHandler != null) {
			if (_log.isWarnEnabled()) {
				_log.warn("DeviceProfileRuleHandler with key: " +
					deviceProfileRuleHandler.getType() +
					" has already been registering. Replacing with " +
					deviceProfileRuleHandler.getType());
			}
		}
	}

	public void setDeviceProfileActionHandlers(
		Collection<DeviceProfileActionHandler> deviceActionHandlers) {

		for (DeviceProfileActionHandler deviceActionHandler :
				deviceActionHandlers) {

			registerDeviceActionHandler(deviceActionHandler);
		}
	}

	public void setDeviceProfileRuleHandlers(
		Collection<DeviceProfileRuleHandler> deviceProfileRuleHandlers) {

		for (DeviceProfileRuleHandler deviceProfileRuleHandler :
				deviceProfileRuleHandlers) {

			registerDeviceRuleHandler(deviceProfileRuleHandler);
		}
	}

	public DeviceProfileActionHandler unregisterDeviceActionHandler(
		String deviceProfileActionType) {

		return _deviceProfileActionHandler.remove(deviceProfileActionType);
	}

	public DeviceProfileRuleHandler unregisterDeviceProfileRuleHandler(
		String deviceProfileRuleType) {

		return _deviceProfileRuleHandlers.remove(deviceProfileRuleType);
	}

	protected void processDeviceProfileRule(
			DeviceProfileRule deviceProfileRule, Device device,
			HttpServletRequest request, HttpServletResponse response)
		throws PortalException, SystemException {

		DeviceProfileRuleHandler deviceProfileRuleHandler =
			_deviceProfileRuleHandlers.get(deviceProfileRule.getRuleType());

		if (deviceProfileRuleHandler != null) {
			deviceProfileRuleHandler.evaluateDeviceProfileRule(
				deviceProfileRule, device, request, response);
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No DeviceProfileRuleHandler registered for rule type:" +
					deviceProfileRule.getRuleType());
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DefaultDeviceProfileProcessorImpl.class);

	private Map<String, DeviceProfileActionHandler> _deviceProfileActionHandler =
		new HashMap<String, DeviceProfileActionHandler>();
	private Map<String, DeviceProfileRuleHandler> _deviceProfileRuleHandlers =
		new HashMap<String, DeviceProfileRuleHandler>();
}