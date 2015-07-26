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

package com.liferay.portal.kernel.configuration.module;

import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.settings.SettingsLocator;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.settings.SettingsInvocationHandler;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Jorge Ferrer
 */
public class ModuleConfigurationFactoryImpl
	implements ModuleConfigurationFactory {

	@Override
	public <T> T getConfiguration(
			Class<T> clazz, SettingsLocator settingsLocator)
		throws SettingsException {

		Settings settings = SettingsFactoryUtil.getSettings(settingsLocator);

		Class<?> settingsOverrideClass = getOverrideClass(clazz);

		try {
			TypedSettings typedSettings = new TypedSettings(settings);

			Object settingsOverrideInstance = null;

			if (settingsOverrideClass != null) {
				Constructor<?> constructor =
					settingsOverrideClass.getConstructor(TypedSettings.class);

				settingsOverrideInstance = constructor.newInstance(
					typedSettings);
			}

			SettingsInvocationHandler<T> settingsInvocationHandler =
				new SettingsInvocationHandler<>(
					clazz, settingsOverrideInstance, typedSettings);

			return settingsInvocationHandler.createProxy();
		}
		catch (NoSuchMethodException | InvocationTargetException |
			InstantiationException | IllegalAccessException e) {

			throw new SettingsException(
				"Unable to load settings of type " + clazz.getName(), e);
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