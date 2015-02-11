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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.resource.manager.ResourceManager;
import com.liferay.portal.kernel.settings.BaseSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Iván Zaera
 */
public class ServiceConfigurationBeanSettings extends BaseSettings
	implements Settings {

	public ServiceConfigurationBeanSettings(
		Object serviceConfigurationBean, ResourceManager resourceManager,
		SettingsFactory settingsFactory, Settings parentSettings) {

		super(parentSettings);

		_serviceConfigurationBean = serviceConfigurationBean;

		_locationVariableResolver = new LocationVariableResolver(
			resourceManager, settingsFactory);
	}

	@Override
	protected String doGetValue(String key) {
		Object object = _getProperty(key);

		if (object == null) {
			return null;
		}

		String value = object.toString();

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

		return (String[])object;
	}

	private Object _getProperty(String key) {
		if (_serviceConfigurationBean == null) {
			return null;
		}

		Class<?> clazz = _serviceConfigurationBean.getClass();

		try {
			Method method = clazz.getMethod(key);

			return method.invoke(_serviceConfigurationBean);
		}
		catch (NoSuchMethodException nsme) {
			return null;
		}
		catch (InvocationTargetException ite) {
			throw new SystemException("Unable to read property " + key, ite);
		}
		catch (IllegalAccessException iae) {
			throw new SystemException("Unable to read property " + key, iae);
		}
	}

	private final LocationVariableResolver _locationVariableResolver;
	private final Object _serviceConfigurationBean;

}