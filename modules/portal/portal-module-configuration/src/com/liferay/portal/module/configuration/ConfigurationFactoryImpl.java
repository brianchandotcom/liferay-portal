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

package com.liferay.portal.module.configuration;

import com.liferay.portal.kernel.configuration.module.ConfigurationException;
import com.liferay.portal.kernel.configuration.module.ConfigurationFactory;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.settings.SettingsLocator;
import com.liferay.portal.kernel.settings.TypedSettings;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = ConfigurationFactory.class)
public class ConfigurationFactoryImpl implements ConfigurationFactory {

	@Override
	public <T> T getConfiguration(
			Class<T> clazz, SettingsLocator settingsLocator)
		throws ConfigurationException {

		try {
			Settings settings = SettingsFactoryUtil.getSettings(
				settingsLocator);

			Class<?> configurationOverrideClass = getOverrideClass(clazz);

			TypedSettings typedSettings = new TypedSettings(settings);

			Object configurationOverrideInstance = null;

			if (configurationOverrideClass != null) {
				Constructor<?> constructor =
					configurationOverrideClass.getConstructor(
						TypedSettings.class);

				configurationOverrideInstance = constructor.newInstance(
					typedSettings);
			}

			ConfigurationInvocationHandler<T>
				configurationInvocationHandler =
					new ConfigurationInvocationHandler<>(
						clazz, configurationOverrideInstance, typedSettings);

			return configurationInvocationHandler.createProxy();
		}
		catch (NoSuchMethodException | InvocationTargetException |
			InstantiationException | IllegalAccessException |
			SettingsException e) {

			throw new ConfigurationException(
				"Unable to load configuration of type " + clazz.getName(), e);
		}
	}

	protected <T> Class<?> getOverrideClass(Class<T> clazz) {
		Settings.OverrideClass overrideClass = clazz.getAnnotation(
			Settings.OverrideClass.class);

		if (overrideClass == null) {
			return null;
		}

		if (overrideClass.value() == Object.class) {
			return null;
		}

		return overrideClass.value();
	}

}