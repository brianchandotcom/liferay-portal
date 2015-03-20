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

package com.liferay.portal.settings.impl;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.settings.definition.SettingsDefinition;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.settings.util.SettingsDefinitionUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.Dictionary;

/**
 * @author Iván Zaera
 */
public class ConfigurationBeanBuilder<C> {

	public ConfigurationBeanBuilder(
		SettingsDefinition<?, C> settingsDefinition,
		Dictionary<String, ?> properties) {

		_settingsDefinition = settingsDefinition;

		_configurationPid = SettingsDefinitionUtil.getConfigurationPid(
			settingsDefinition);

		_configurable = Configurable.createConfigurable(
			_settingsDefinition.getConfigurationBeanClass(), properties);

		Class<C> configurationBeanClass =
			_settingsDefinition.getConfigurationBeanClass();

		_configurationBean = (C)ProxyUtil.newProxyInstance(
			configurationBeanClass.getClassLoader(),
			new Class[] {configurationBeanClass},
			new InvocationHandler() {
				@Override
				public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {

					return method.invoke(_configurable, args);
				}
			});
	}

	public C getConfigurationBean() {
		return _configurationBean;
	}

	public String getConfigurationPid() {
		return _configurationPid;
	}

	public void updateProperties(Dictionary<String, ?> properties) {
		if (_log.isInfoEnabled()) {
			_log.info(
				"Configuration with configurationPid " +
					SettingsDefinitionUtil.getConfigurationPid(
						_settingsDefinition) +
					" changed");
		}

		_configurable = Configurable.createConfigurable(
			_settingsDefinition.getConfigurationBeanClass(), properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ConfigurationBeanBuilder.class);

	private volatile C _configurable;
	private final C _configurationBean;
	private final String _configurationPid;
	private final SettingsDefinition<?, C> _settingsDefinition;

}