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

package com.liferay.portal.kernel.mobile.device;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.mobile.device.action.DeviceProfileActionHandler;
import com.liferay.portal.kernel.mobile.device.rule.DeviceProfileRuleHandler;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Edward Han
 */
public interface DeviceProfileProcessor {
	public void applyDeviceProfile(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException, SystemException;

	public DeviceProfileActionHandler getDeviceProfileActionHandler(
		String actionType);

	public DeviceProfileRuleHandler getDeviceProfileRuleHandler(
		String ruleType);

	public Collection<DeviceProfileActionHandler>
		getDeviceProfileActionHandlers();

	public Collection<DeviceProfileRuleHandler>
		getDeviceProfileRuleHandlers();

	public void registerDeviceActionHandler(
		DeviceProfileActionHandler deviceActionHandler);

	public void registerDeviceRuleHandler(
		DeviceProfileRuleHandler deviceProfileRuleHandler);

	public DeviceProfileActionHandler unregisterDeviceActionHandler(
		String deviceProfileActionType);

	public DeviceProfileRuleHandler unregisterDeviceProfileRuleHandler(
		String deviceProfileRuleType);
}