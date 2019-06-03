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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Iván Zaera
 */
public class ConfigurationBeanSettings
	extends BaseSettings implements Settings {

	public ConfigurationBeanSettings(
		LocationVariableResolver locationVariableResolver,
		Object configurationBean, Settings parentSettings) {

		super(parentSettings);

		if (locationVariableResolver == null) {
			throw new NullPointerException(
				"Location variable resolver is null");
		}

		if (configurationBean == null) {
			throw new NullPointerException("Configuration bean is null");
		}

		_locationVariableResolver = locationVariableResolver;
		_configurationBean = configurationBean;

		Class<?> clazz = configurationBean.getClass();

		for (Method method : clazz.getMethods()) {
			_methods.put(method.getName(), method);
		}
	}

	@Override
	protected String doGetValue(String key) {
		Object object = _getProperty(key);

		if (object == null) {
			return null;
		}

		String value = null;

		if (object instanceof LocalizedValuesMap) {
			LocalizedValuesMap localizedValuesMap = (LocalizedValuesMap)object;

			value = localizedValuesMap.getDefaultValue();
		}
		else {
			value = object.toString();
		}

		if (_locationVariableResolver.isLocationVariable(value)) {
			return _locationVariableResolver.resolve(value);
		}

		return value;
	}

	@Override
	protected String[] doGetValues(String key) {
		Object object = _getProperty(key);

		if (object == null) {
			return null;
		}

		return GetterUtil.getStringValues(object);
	}

	private Object _getProperty(String key) {
		try {
			Method method = _methods.get(key);

			if (method == null) {
				return null;
			}

			return method.invoke(_configurationBean);
		}
		catch (InvocationTargetException ite) {
			throw new SystemException("Unable to read property " + key, ite);
		}
		catch (IllegalAccessException iae) {
			throw new SystemException("Unable to read property " + key, iae);
		}
	}

	private final Object _configurationBean;
	private final LocationVariableResolver _locationVariableResolver;
	private final Map<String, Method> _methods = new HashMap<>();

}