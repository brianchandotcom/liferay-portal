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

package com.liferay.analytics.settings.web.internal.util;

import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Riccardo Ferrari
 */
public class WizardModeUtil {

	public static String getNextConfigurationScreenKey(
		HttpSession httpSession) {

		return GetterUtil.getString(
			httpSession.getAttribute(
				_ANALYTICS_CONFIGURATION_NEXT_CONFIGURATION_SCREEN_KEY),
			null);
	}

	public static String getNextStepURL(
		ActionResponse actionResponse, String configurationScreenKey) {

		return PortletURLBuilder.createRenderURL(
			PortalUtil.getLiferayPortletResponse(actionResponse)
		).setMVCRenderCommandName(
			"/configuration_admin/view_configuration_screen"
		).setParameter(
			"configurationScreenKey", configurationScreenKey
		).buildPortletURL(
		).toString();
	}

	public static boolean isWizardMode(HttpSession httpSession) {
		return GetterUtil.getBoolean(
			httpSession.getAttribute(_ANALYTICS_CONFIGURATION_WIZARD_MODE));
	}

	public static void setNextConfigurationScreenKey(
		HttpSession httpSession, String configurationScreenKey) {

		httpSession.setAttribute(
			_ANALYTICS_CONFIGURATION_NEXT_CONFIGURATION_SCREEN_KEY,
			configurationScreenKey);
	}

	public static void setNextConfigurationScreenKey(
		PortletRequest portletRequest, String nextStep) {

		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(portletRequest);

		setNextConfigurationScreenKey(
			httpServletRequest.getSession(), nextStep);
	}

	public static void setWizardMode(
		HttpSession httpSession, boolean wizardMode) {

		if (!wizardMode) {
			httpSession.removeAttribute(
				_ANALYTICS_CONFIGURATION_NEXT_CONFIGURATION_SCREEN_KEY);
		}

		httpSession.setAttribute(
			_ANALYTICS_CONFIGURATION_WIZARD_MODE, wizardMode);
	}

	private static final String
		_ANALYTICS_CONFIGURATION_NEXT_CONFIGURATION_SCREEN_KEY =
			"ANALYTICS_CONFIGURATION_NEXT_CONFIGURATION_SCREEN_KEY";

	private static final String _ANALYTICS_CONFIGURATION_WIZARD_MODE =
		"ANALYTICS_CONFIGURATION_WIZARD_MODE";

}