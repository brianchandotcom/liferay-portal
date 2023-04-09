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

import com.liferay.petra.concurrent.DCLSingleton;
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
	}

	@Override
	public Set<String> getAllKeys() {
		return new HashSet<>(
			_allKeysDCLSingleton.getSingleton(this::_createAllKeys));
	}

	@Override
	public Set<String> getMultiValuedKeys() {
		return new HashSet<>(
			_multiValuedKeysDCLSingleton.getSingleton(
				this::_createMultiValuedKeys));
	}

	private Set<String> _createAllKeys() {
		Set<String> allKeys = new HashSet<>();

		for (Method method : _configurationBeanClass.getMethods()) {
			allKeys.add(method.getName());
		}

		return allKeys;
	}

	private Set<String> _createMultiValuedKeys() {
		Set<String> multiValuedKeys = new HashSet<>();

		for (Method method : _configurationBeanClass.getMethods()) {
			if (method.getReturnType() == String[].class) {
				multiValuedKeys.add(method.getName());
			}
		}

		return multiValuedKeys;
	}

	private final DCLSingleton<Set<String>> _allKeysDCLSingleton =
		new DCLSingleton<>();
	private final Class<?> _configurationBeanClass;
	private final DCLSingleton<Set<String>> _multiValuedKeysDCLSingleton =
		new DCLSingleton<>();

}