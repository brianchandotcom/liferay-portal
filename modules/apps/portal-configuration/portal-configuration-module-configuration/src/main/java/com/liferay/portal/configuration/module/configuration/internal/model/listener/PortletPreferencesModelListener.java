/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.module.configuration.internal.model.listener;

import com.liferay.portal.configuration.module.configuration.internal.ConfigurationOverrideInstance;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsLocatorHelper;
import com.liferay.portal.kernel.settings.definition.ConfigurationPidMapping;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(service = ModelListener.class)
public class PortletPreferencesModelListener
	extends BaseModelListener<PortletPreferences> {

	@Override
	public void onAfterCreate(PortletPreferences portletPreferences)
		throws ModelListenerException {

		_clearConfigurationOverrideInstance(portletPreferences);
	}

	@Override
	public void onAfterRemove(PortletPreferences portletPreferences)
		throws ModelListenerException {

		_clearConfigurationOverrideInstance(portletPreferences);
	}

	@Override
	public void onAfterUpdate(
			PortletPreferences originalPortletPreferences,
			PortletPreferences portletPreferences)
		throws ModelListenerException {

		_clearConfigurationOverrideInstance(portletPreferences);
	}

	private void _clearConfigurationOverrideInstance(
		PortletPreferences portletPreferences) {

		if ((portletPreferences == null) ||
			(portletPreferences.getPortletId() == null)) {

			return;
		}

		ConfigurationPidMapping configurationPidMapping =
			_getConfigurationPidMapping(portletPreferences.getPortletId());

		if (configurationPidMapping == null) {
			return;
		}

		ConfigurationOverrideInstance.clearConfigurationOverrideInstance(
			configurationPidMapping.getConfigurationBeanClass());
	}

	private ConfigurationPidMapping _getConfigurationPidMapping(
		String configurationId) {

		ConfigurationPidMapping configurationPidMapping =
			_settingsLocatorHelper.getConfigurationPidMapping(configurationId);

		if (configurationPidMapping == null) {
			return null;
		}

		Class<?> clazz = configurationPidMapping.getConfigurationBeanClass();

		if (clazz.getAnnotation(Settings.Config.class) == null) {
			return configurationPidMapping;
		}

		return null;
	}

	@Reference
	private SettingsLocatorHelper _settingsLocatorHelper;

}