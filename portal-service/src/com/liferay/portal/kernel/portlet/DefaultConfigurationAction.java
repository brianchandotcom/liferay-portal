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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.settings.PortletPreferencesSettings;
import com.liferay.portal.settings.Settings;

import javax.portlet.ActionRequest;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.ReadOnlyException;

/**
 * @author Iván Zaera
 */
public class DefaultConfigurationAction extends SettingsConfigurationAction
	implements ConfigurationAction, ResourceServingConfigurationAction {

	public static final String PREFERENCES_PREFIX = "preferences--";

	public DefaultConfigurationAction() {
		setConfigurationParametersPrefix(PREFERENCES_PREFIX);
	}

	@Override
	protected Settings getSettings(ActionRequest actionRequest) {
		return new PortletPreferencesSettings(actionRequest.getPreferences());
	}

	protected void postProcess(
			long companyId, PortletRequest portletRequest,
			PortletPreferences portletPreferences)
		throws PortalException, SystemException {
	}

	@Override
	protected void postProcess(
			long companyId, PortletRequest portletRequest, Settings settings)
		throws PortalException, SystemException {

		PortletPreferencesSettings portletPreferencesSettings =
			(PortletPreferencesSettings)settings;

		postProcess(
			companyId, portletRequest,
			portletPreferencesSettings.getPortletPreferences());
	}

	protected void removeDefaultValue(
		PortletRequest portletRequest, PortletPreferences portletPreferences,
		String key, String defaultValue) throws SystemException {

		String value = getParameter(portletRequest, key);

		if (defaultValue.equals(value) ||
			StringUtil.equalsIgnoreBreakLine(defaultValue, value)) {

			try {
				portletPreferences.reset(key);
			}
			catch (ReadOnlyException roe) {
				throw new SystemException(roe);
			}
		}
	}

}