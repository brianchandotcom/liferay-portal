/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.settings.Settings;
import com.liferay.portal.settings.SettingsFactoryUtil;
import com.liferay.portal.theme.ThemeDisplay;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.ValidatorException;

/**
 * @author Iván Zaera
 */
public class SettingsConfigurationAction
	extends BaseDefaultConfigurationAction<Settings>
	implements ConfigurationAction, ResourceServingConfigurationAction {

	public static final String SETTINGS_PREFIX = "preferences--";

	public SettingsConfigurationAction() {
		super(SETTINGS_PREFIX);
	}

	@Override
	protected Settings getConfiguration(ActionRequest actionRequest)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String serviceName = ParamUtil.getString(actionRequest, "serviceName");

		String settingsScope = ParamUtil.getString(
			actionRequest, "settingsScope");

		if (settingsScope.equals("company")) {
			return SettingsFactoryUtil.getCompanyServiceSettings(
				themeDisplay.getCompanyId(), serviceName);
		}
		else if (settingsScope.equals("group")) {
			return SettingsFactoryUtil.getGroupServiceSettings(
				themeDisplay.getSiteGroupId(), serviceName);
		}
		else if (settingsScope.equals("portletInstance")) {
			String portletResource = ParamUtil.getString(
				actionRequest, "portletResource");

			return SettingsFactoryUtil.getPortletInstanceSettings(
				themeDisplay.getLayout(), portletResource);
		}

		throw new IllegalArgumentException(
			"Invalid settings scope " + settingsScope);
	}

	@Override
	@SuppressWarnings("unused")
	protected void postProcess(
			long companyId, PortletRequest portletRequest, Settings settings)
		throws PortalException, SystemException {
	}

	@Override
	protected void reset(Settings settings, String key) {
		settings.reset(key);
	}

	@Override
	protected void setValue(Settings settings, String name, String value) {
		settings.setValue(name, value);
	}

	@Override
	protected void setValues(Settings settings, String name, String[] values) {
		settings.setValues(name, values);
	}

	@Override
	protected void store(Settings settings)
		throws IOException, ValidatorException {

		settings.store();
	}

}