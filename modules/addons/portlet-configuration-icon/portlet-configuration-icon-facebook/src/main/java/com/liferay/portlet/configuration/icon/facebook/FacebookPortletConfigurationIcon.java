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

package com.liferay.portlet.configuration.icon.facebook;

import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

/**
 * @author Eudaldo Alonso
 */
public class FacebookPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public FacebookPortletConfigurationIcon(PortletRequest portletRequest) {
		super(portletRequest);
	}

	@Override
	public String getMessage() {
		return "add-to-facebook";
	}

	@Override
	public String getMethod() {
		return "get";
	}

	@Override
	public String getURL() {
		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletPreferences portletSetup =
			PortletPreferencesFactoryUtil.getStrictLayoutPortletSetup(
				themeDisplay.getLayout(), portletDisplay.getId());

		String lfrFacebookAPIKey = portletSetup.getValue(
			"lfrFacebookApiKey", StringPool.BLANK);

		return "http://www.facebook.com/add.php?api_key=" + lfrFacebookAPIKey +
			"&ref=pd";
	}

	@Override
	public boolean isLabel() {
		return true;
	}

	@Override
	public boolean isShow() {
		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletPreferences portletSetup =
			PortletPreferencesFactoryUtil.getStrictLayoutPortletSetup(
				themeDisplay.getLayout(), portletDisplay.getId());

		String lfrFacebookAPIKey = portletSetup.getValue(
			"lfrFacebookApiKey", StringPool.BLANK);
		String lfrFacebookCanvasPageURL = portletSetup.getValue(
			"lfrFacebookCanvasPageUrl", StringPool.BLANK);
		boolean facebookShowAddAppLink = GetterUtil.getBoolean(
			portletSetup.getValue("lfrFacebookShowAddAppLink", null), true);

		if (Validator.isNull(lfrFacebookCanvasPageURL) ||
			Validator.isNull(lfrFacebookAPIKey)) {

			facebookShowAddAppLink = false;
		}

		if (facebookShowAddAppLink) {
			return true;
		}

		return false;
	}

}