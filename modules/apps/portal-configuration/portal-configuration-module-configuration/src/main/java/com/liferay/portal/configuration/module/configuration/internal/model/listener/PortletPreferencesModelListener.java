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

package com.liferay.portal.configuration.module.configuration.internal.model.listener;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.internal.ConfigurationOverrideInstance;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsLocatorHelper;
import com.liferay.portal.kernel.settings.definition.ConfigurationPidMapping;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.service.cm.ConfigurationEvent;
import org.osgi.service.cm.ConfigurationListener;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(service = {ConfigurationListener.class, ModelListener.class})
public class PortletPreferencesModelListener
	extends BaseModelListener<PortletPreferences>
	implements ConfigurationListener {

	@Override
	public void configurationEvent(ConfigurationEvent configurationEvent) {
		String key = configurationEvent.getPid();

		String factoryPid = configurationEvent.getFactoryPid();

		if (factoryPid != null) {
			key = StringUtil.replaceLast(
				factoryPid, ".scoped", StringPool.BLANK);
		}

		ConfigurationOverrideInstance.clearConfigurationOverrideInstance(key);
	}

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