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

package com.liferay.portal.settings;

import com.liferay.portal.kernel.resource.manager.ClassLoaderResourceManager;
import com.liferay.portal.kernel.resource.manager.ResourceManager;
import com.liferay.portal.kernel.settings.SettingsDescriptor;
import com.liferay.portal.kernel.settings.definition.SettingsDefinition;

import java.lang.reflect.Method;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Iván Zaera
 */
public class SettingsDefinitionSettingsDescriptor
	implements SettingsDescriptor {

	public SettingsDefinitionSettingsDescriptor(
		SettingsDefinition<?, ?> settingsDefinition, Object configurationBean) {

		_settingsDefinition = settingsDefinition;
		_configurationBean = configurationBean;

		Class<?> settingsClass = settingsDefinition.getSettingsClass();

		_resourceManager = new ClassLoaderResourceManager(
			settingsClass.getClassLoader());

		_initAllKeys();
		_initMultiValuedKeys();
		_initSettingsIds();
	}

	@Override
	public Set<String> getAllKeys() {
		return _allKeys;
	}

	@Override
	public Object getConfigurationBean() {
		return _configurationBean;
	}

	@Override
	public Set<String> getMultiValuedKeys() {
		return _multiValuedKeys;
	}

	public ResourceManager getResourceManager() {
		return _resourceManager;
	}

	@Override
	public Class<?> getSettingsClass() {
		return _settingsDefinition.getSettingsClass();
	}

	@Override
	public Set<String> getSettingsIds() {
		return _settingsIds;
	}

	private void _initAllKeys() {
		Class<?> configurationBeanClass =
			_settingsDefinition.getConfigurationBeanClass();

		Method[] methods = configurationBeanClass.getMethods();

		for (Method method : methods) {
			_allKeys.add(method.getName());
		}
	}

	private void _initMultiValuedKeys() {
		Class<?> configurationBeanClass =
			_settingsDefinition.getConfigurationBeanClass();

		Method[] methods = configurationBeanClass.getMethods();

		for (Method method : methods) {
			Class<?> returnType = method.getReturnType();

			if (returnType.equals(String[].class)) {
				_multiValuedKeys.add(method.getName());
			}
		}
	}

	private void _initSettingsIds() {
		Collections.addAll(_settingsIds, _settingsDefinition.getSettingsIds());
	}

	private final Set<String> _allKeys = new HashSet<>();
	private final Object _configurationBean;
	private final Set<String> _multiValuedKeys = new HashSet<>();
	private final ResourceManager _resourceManager;
	private final SettingsDefinition<?, ?> _settingsDefinition;
	private final Set<String> _settingsIds = new HashSet<>();

}