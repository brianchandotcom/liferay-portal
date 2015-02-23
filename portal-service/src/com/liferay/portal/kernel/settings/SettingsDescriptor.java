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

package com.liferay.portal.kernel.settings;

import com.liferay.portal.kernel.util.StringUtil;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Iván Zaera
 */
public class SettingsDescriptor<T> {

	public SettingsDescriptor(Class<T> settingsClass) {
		_settingsClass = settingsClass;
	}

	public Set<String> getAllKeys() {
		if (_allKeys == null) {
			_allKeys = new HashSet<>();

			Method[] methods = _getPropertyMethods();

			for (Method method : methods) {
				_allKeys.add(_getPropertyName(method));
			}
		}

		return _allKeys;
	}

	public Set<String> getIds() {
		if (_ids == null) {
			_ids = new HashSet<>();

			Settings.Config settingsConfig = _settingsClass.getAnnotation(
				Settings.Config.class);

			for (String id : settingsConfig.ids()) {
				_ids.add(id);
			}
		}

		return _ids;
	}

	public Set<String> getMultiValuedKeys() {
		if (_multiValuedKeys == null) {
			_multiValuedKeys = new HashSet<>();

			Method[] methods = _getPropertyMethods();

			for (Method method : methods) {
				Class<?> propertyClass = method.getReturnType();

				if (propertyClass == String[].class) {
					_multiValuedKeys.add(_getPropertyName(method));
				}
			}
		}

		return _multiValuedKeys;
	}

	private Method[] _getPropertyMethods() {
		List<Method> propertyMethods = new ArrayList<>();

		Method[] methods = _settingsClass.getMethods();

		for (Method method : methods) {
			Settings.Property settingsProperty = method.getAnnotation
				(Settings.Property.class);

			if ((settingsProperty != null) && settingsProperty.ignore()) {
				continue;
			}

			String name = method.getName();

			if (name.equals("getClass")) {
				continue;
			}

			if (name.startsWith("get") || name.startsWith("is")) {
				propertyMethods.add(method);
			}
		}

		return propertyMethods.toArray(new Method[propertyMethods.size()]);
	}

	private String _getPropertyName(Method method) {
		Settings.Property settingsProperty = method.getAnnotation
			(Settings.Property.class);

		if (settingsProperty!= null) {
			String name = settingsProperty.name();

			if (!name.isEmpty()) {
				return name;
			}
		}

		String name = method.getName();

		if (name.startsWith("get")) {
			name = name.substring(3);
		}
		else if (name.startsWith("is")) {
			name = name.substring(2);
		}
		else {
			throw new IllegalArgumentException(
				"Invalid method name for getter " + method.getName());
		}

		String nameFirstLetter = StringUtil.toLowerCase(name.substring(0, 1));

		String nameRest = name.substring(1);

		return nameFirstLetter + nameRest;
	}

	private Set<String> _allKeys;
	private Set<String> _ids;
	private Set<String> _multiValuedKeys;
	private final Class<T> _settingsClass;

}