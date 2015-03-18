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

package com.liferay.portal.settings.util;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.kernel.settings.definition.SettingsDefinition;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Iván Zaera
 */
public class SettingsDefinitionUtil {

	public static String getConfigurationBeanClassName(
		SettingsDefinition<?, ?> settingsDefinition) {

		Class<?> configurationBeanClass =
			settingsDefinition.getConfigurationBeanClass();

		return configurationBeanClass.getName();
	}

	public static String getConfigurationPid(
			SettingsDefinition<?, ?> settingsDefinition)
		throws RuntimeException {

		try {
			Class<?> configurationBeanClass =
				settingsDefinition.getConfigurationBeanClass();

			Annotation[] annotations = configurationBeanClass.getAnnotations();

			for (Annotation annotation : annotations) {
				Class<? extends Annotation> clazz = annotation.getClass();

				Class<?> iface = clazz.getInterfaces()[0];

				String name = iface.getName();

				if (name.equals(Meta.OCD.class.getName())) {
					Method method = iface.getMethod("id");

					return (String)method.invoke(annotation);
				}
			}

			return null;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String getSettingsClassName(
		SettingsDefinition<?, ?> settingsDefinition) {

		Class<?> settingsClass = settingsDefinition.getSettingsClass();

		return settingsClass.getName();
	}

}