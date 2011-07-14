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
import com.liferay.portal.kernel.mobile.device.DeviceProfileProcessorUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.mobile.device.action.impl.ThemeDeviceActionHandler;
import com.liferay.portal.mobile.device.rule.impl.SimpleDeviceProfileRuleHandler;
import com.liferay.portal.mobile.model.DeviceProfile;
import com.liferay.portal.mobile.model.DeviceProfileRule;
import com.liferay.portal.mobile.service.DeviceProfileActionLocalServiceUtil;
import com.liferay.portal.mobile.service.DeviceProfileLocalServiceUtil;
import com.liferay.portal.mobile.service.DeviceProfileRuleLocalServiceUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Edward Han
 */
public class DeviceServicePreAction extends Action {

	public void run(
			HttpServletRequest request, HttpServletResponse response)
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

		//	Device Profile

		DeviceProfile deviceProfile;

		try {
			deviceProfile = getDeviceProfile(themeDisplay);
		}
		catch (SystemException e) {
			throw new ActionException(e);
		}

		themeDisplay.setDeviceProfile(deviceProfile);

		//	todo remove
		try {
			if (deviceProfile == null) {
				Map<Locale, String> map = new HashMap<Locale, String>();
				map.put(Locale.US, "test device profile");
				deviceProfile = DeviceProfileLocalServiceUtil.addDeviceProfile(map, map);

				Layout layout = themeDisplay.getLayout();
				LayoutLocalServiceUtil.updateDeviceProfile(layout.getPlid(), deviceProfile);

				UnicodeProperties rule = new UnicodeProperties();
				rule.put(SimpleDeviceProfileRuleHandler.PARAMETER_OS, "testOS");
				rule.put(SimpleDeviceProfileRuleHandler.PARAMETER_TABLET, StringPool.TRUE);

				DeviceProfileRule dpr = DeviceProfileRuleLocalServiceUtil.addDeviceProfileRule(deviceProfile.getDeviceProfileId(), map, map, SimpleDeviceProfileRuleHandler.class.getName(), rule);

				UnicodeProperties action = new UnicodeProperties();
				action.put("colorSchemeId", "testColorScheme");
				action.put("themeId", "testThemeId");

				DeviceProfileActionLocalServiceUtil.addDeviceProfileAction(dpr.getDeviceProfileId(), dpr.getDeviceProfileRuleId(), map, map, ThemeDeviceActionHandler.class.getName(), action);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		// end

		//	Apply Device Profile

		if (deviceProfile != null) {
			try {
				DeviceProfileProcessorUtil.applyDeviceProfile(
					request, response);
			}
			catch (Exception e) {
				throw new ActionException(e);
			}
		}
	}

	protected DeviceProfile getDeviceProfile(ThemeDisplay themeDisplay)
		throws SystemException {

		Layout layout = themeDisplay.getLayout();
		LayoutSet layoutSet = themeDisplay.getLayoutSet();

		DeviceProfile deviceProfile = null;

		if (layout != null) {
			long deviceProfileId = layout.getDeviceProfileId();

			if (deviceProfileId != 0) {
				deviceProfile = DeviceProfileLocalServiceUtil.fetchByPrimaryKey(
					deviceProfileId);

				if (deviceProfile == null) {
					if (_log.isInfoEnabled()) {
						_log.info(
							"Invalid device profile id: " +
							deviceProfileId +
							".  Resetting device profile to 0");
					}

					LayoutLocalServiceUtil.updateDeviceProfile(
						layout.getPlid(), 0);
				}
			}
		}

		if ((deviceProfile == null) && (layoutSet != null)) {
			long deviceProfileId = layoutSet.getDeviceProfileId();

			if (deviceProfileId != 0) {
				deviceProfile = DeviceProfileLocalServiceUtil.fetchByPrimaryKey(
					deviceProfileId);

				if (deviceProfile == null) {
					if (_log.isInfoEnabled()) {
						_log.info(
							"Invalid device profile id: " +
							deviceProfileId +
							".  Resetting device profile to 0");
					}

					LayoutSetLocalServiceUtil.updateDeviceProfile(
						layoutSet.getLayoutSetId(), 0);
				}
			}
		}

		return deviceProfile;
	}

	private static Log _log = LogFactoryUtil.getLog(
		DeviceServicePreAction.class);
}