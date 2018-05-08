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

package com.liferay.portal.configuration.settings.internal;

import com.liferay.portal.kernel.settings.SettingsDescriptor;

import java.lang.reflect.Method;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Iván Zaera
 */
public class ConfigurationBeanClassSettingsDescriptor
	implements SettingsDescriptor {

	public ConfigurationBeanClassSettingsDescriptor(
		Class<?> configurationBeanClass) {

		_configurationBeanClass = configurationBeanClass;

		_initAllKeys();
		_initMultiValuedKeys();
	}

	@Override
	public Set<String> getAllKeys() {
		return _allKeys;
	}

	@Override
	public Set<String> getMultiValuedKeys() {
		return _multiValuedKeys;
	}

	private void _initAllKeys() {
		Method[] methods = _configurationBeanClass.getMethods();

		for (Method method : methods) {
			_allKeys.add(method.getName());
		}
	}

	private void _initMultiValuedKeys() {
		Method[] methods = _configurationBeanClass.getMethods();

		for (Method method : methods) {
			Class<?> returnType = method.getReturnType();

			if (returnType.equals(String[].class)) {
				_multiValuedKeys.add(method.getName());
			}
		}
	}

	private final Set<String> _allKeys = new HashSet<>();
	private final Class<?> _configurationBeanClass;
	private final Set<String> _multiValuedKeys = new HashSet<>();

}