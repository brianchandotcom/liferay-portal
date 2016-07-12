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

package com.liferay.mobile.device.rules.internal.events;

import com.liferay.mobile.device.rules.action.ActionHandlerManagerUtil;
import com.liferay.mobile.device.rules.internal.MDRRuleGroupInstanceImpl;
import com.liferay.mobile.device.rules.model.MDRAction;
import com.liferay.mobile.device.rules.model.MDRRuleGroupInstance;
import com.liferay.mobile.device.rules.rule.RuleGroupProcessorUtil;
import com.liferay.mobile.device.rules.service.MDRActionLocalService;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mobile.device.Device;
import com.liferay.portal.kernel.mobile.device.DeviceDetectionUtil;
import com.liferay.portal.kernel.mobile.device.UnknownDevice;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.TransientValue;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Edward Han
 */
@Component(
	property = {"key=servlet.service.events.pre"},
	service = LifecycleAction.class
)
public class MDRServicePreAction extends Action {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Device device = null;

		if (PropsValues.MOBILE_DEVICE_SESSION_CACHE_ENABLED) {
			TransientValue<Device> transientValue =
				(TransientValue<Device>)session.getAttribute(WebKeys.DEVICE);

			if (transientValue != null) {
				device = transientValue.getValue();
			}
		}

		if (device == null) {
			device = DeviceDetectionUtil.detectDevice(request);

			if (PropsValues.MOBILE_DEVICE_SESSION_CACHE_ENABLED) {
				session.setAttribute(
					WebKeys.DEVICE, new TransientValue<Device>(device));
			}
		}

		themeDisplay.setDevice(device);

		UnknownDevice unknownDevice = UnknownDevice.getInstance();

		if (device.equals(unknownDevice)) {
			return;
		}

		MDRRuleGroupInstance mdrRuleGroupInstance = null;

		try {
			mdrRuleGroupInstance = RuleGroupProcessorUtil.evaluateRuleGroups(
				themeDisplay);

			if (_log.isDebugEnabled()) {
				String logMessage =
					"Rule group evaluation returned rule group instance ";

				if (mdrRuleGroupInstance != null) {
					logMessage += mdrRuleGroupInstance.getRuleGroupInstanceId();
				}
				else {
					logMessage += "null";
				}

				_log.debug(logMessage);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to retrieve rule group", e);
			}

			return;
		}

		themeDisplay.setMDRRuleGroupInstance(
			new MDRRuleGroupInstanceImpl(mdrRuleGroupInstance));

		if (mdrRuleGroupInstance == null) {
			return;
		}

		try {
			List<MDRAction> mdrActions = _mdrActionLocalService.getActions(
				mdrRuleGroupInstance.getRuleGroupInstanceId());

			ActionHandlerManagerUtil.applyActions(
				mdrActions, request, response);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to apply device profile", e);
			}
		}
	}

	@Reference(unbind = "-")
	protected void setMDRActionLocalService(
		MDRActionLocalService mdrActionLocalService) {

		_mdrActionLocalService = mdrActionLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MDRServicePreAction.class);

	private MDRActionLocalService _mdrActionLocalService;

}