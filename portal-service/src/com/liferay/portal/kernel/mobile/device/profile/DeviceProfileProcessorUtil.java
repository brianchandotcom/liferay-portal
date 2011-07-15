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

package com.liferay.portal.kernel.mobile.device.profile;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.mobile.device.profile.action.DeviceProfileActionHandler;
import com.liferay.portal.kernel.mobile.device.profile.rule.DeviceProfileRuleHandler;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Edward Han
 */
public class DeviceProfileProcessorUtil {
	public static void applyDeviceProfile(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException, SystemException {

		_deviceProfileProcessor.applyDeviceProfile(
			httpServletRequest, httpServletResponse);
	}

	public static DeviceProfileActionHandler getDeviceProfileActionHandler(
		String actionType) {

		return _deviceProfileProcessor.getDeviceProfileActionHandler(
			actionType);
	}

	public static DeviceProfileRuleHandler getDeviceProfileRuleHandler(
		String ruleType) {

		return _deviceProfileProcessor.getDeviceProfileRuleHandler(ruleType);
	}

	public static Collection<DeviceProfileActionHandler>
		getDeviceProfileActionHandlers() {

		return _deviceProfileProcessor.getDeviceProfileActionHandlers();
	}

	public DeviceProfileProcessor getDeviceProfileProcessor() {
		return _deviceProfileProcessor;
	}

	public static Collection<DeviceProfileRuleHandler>
			getDeviceProfileRuleHandlers() {

		return _deviceProfileProcessor.getDeviceProfileRuleHandlers();
	}

	public static void registerDeviceActionHandler(
		DeviceProfileActionHandler deviceActionHandler) {

		_deviceProfileProcessor.registerDeviceActionHandler(
			deviceActionHandler);
	}

	public static void registerDeviceRuleHandler(
		DeviceProfileRuleHandler deviceProfileRuleHandler) {

		_deviceProfileProcessor.registerDeviceRuleHandler(
			deviceProfileRuleHandler);
	}

	public void setDeviceProfileProcessor(
		DeviceProfileProcessor deviceProfileProcessor) {

		_deviceProfileProcessor = deviceProfileProcessor;
	}

	public static DeviceProfileActionHandler unregisterDeviceActionHandler(
		String deviceProfileActionType) {

		return _deviceProfileProcessor.unregisterDeviceActionHandler(
			deviceProfileActionType);
	}

	public static DeviceProfileRuleHandler unregisterDeviceProfileRuleHandler(
		String deviceProfileRuleType) {

		return _deviceProfileProcessor.unregisterDeviceProfileRuleHandler(
			deviceProfileRuleType);
	}

	private static DeviceProfileProcessor _deviceProfileProcessor;
}