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

package com.liferay.portal.configuration.module.configuration.internal;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.TypedSettings;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Preston Crary
 */
public class ConfigurationOverrideInstance {

	public static final Object NULL_RESULT = new Object();

	public static void clearConfigurationOverrideInstance(Class<?> clazz) {
		clearConfigurationOverrideInstance(clazz.getName());
	}

	public static void clearConfigurationOverrideInstance(String className) {
		_configurationOverrideInstances.remove(className);
	}

	public static ConfigurationOverrideInstance
			getConfigurationOverrideInstance(
				Class<?> clazz, TypedSettings typedSettings)
		throws ConfigurationException, ReflectiveOperationException {

		Class<?> configurationOverrideClass = _getOverrideClass(clazz);

		if (configurationOverrideClass == null) {
			return null;
		}

		String key = _getKey(clazz);

		ConfigurationOverrideInstance configurationOverrideInstance =
			_configurationOverrideInstances.get(key);

		if (configurationOverrideInstance == null) {
			configurationOverrideInstance = new ConfigurationOverrideInstance(
				configurationOverrideClass, typedSettings);

			_configurationOverrideInstances.put(
				key, configurationOverrideInstance);
		}

		return configurationOverrideInstance;
	}

	public Object invoke(Method method) throws ReflectiveOperationException {
		Method overriddenMethod = _methods.get(method.getName());

		if (overriddenMethod == null) {
			return NULL_RESULT;
		}

		return overriddenMethod.invoke(_configurationOverrideInstance);
	}

	private static String _getKey(Class<?> clazz) {
		for (Class<?> interfaceClazz : clazz.getInterfaces()) {
			if (interfaceClazz.getAnnotation(Meta.OCD.class) != null) {
				return interfaceClazz.getName();
			}
		}

		return clazz.getName();
	}

	private static Class<?> _getOverrideClass(Class<?> clazz) {
		Settings.OverrideClass overrideClass = clazz.getAnnotation(
			Settings.OverrideClass.class);

		if ((overrideClass == null) ||
			(overrideClass.value() == Object.class)) {

			return null;
		}

		return overrideClass.value();
	}

	private ConfigurationOverrideInstance(
			Class<?> configurationOverrideClass, TypedSettings typedSettings)
		throws ConfigurationException, ReflectiveOperationException {

		Constructor<?> constructor = configurationOverrideClass.getConstructor(
			TypedSettings.class);

		_configurationOverrideInstance = constructor.newInstance(typedSettings);

		for (Method method : configurationOverrideClass.getMethods()) {
			_methods.put(method.getName(), method);
		}
	}

	private static final Map<String, ConfigurationOverrideInstance>
		_configurationOverrideInstances = new ConcurrentHashMap<>();

	private final Object _configurationOverrideInstance;
	private final Map<String, Method> _methods = new HashMap<>();

}