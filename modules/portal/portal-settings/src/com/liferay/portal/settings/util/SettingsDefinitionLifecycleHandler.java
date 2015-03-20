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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.settings.GroupServiceSettings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.settings.definition.SettingsDefinition;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.settings.impl.ConfigurationBeanBuilder;
import com.liferay.portal.settings.impl.GroupServiceSettingsProviderBuilder;
import com.liferay.portal.settings.impl.SettingsProviderBuilder;

import java.io.IOException;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;

/**
 * @author Iván Zaera
 */
public class SettingsDefinitionLifecycleHandler<S, C> {

	public SettingsDefinitionLifecycleHandler(
		SettingsDefinition<S, C> settingsDefinition,
		BundleContext bundleContext, ConfigurationAdmin configurationAdmin,
		SettingsFactory settingsFactory) {

		_settingsDefinition = settingsDefinition;

		_bundleContext = bundleContext;
		_configurationAdmin = configurationAdmin;
		_settingsFactory = settingsFactory;

		if (GroupServiceSettings.class.isAssignableFrom(
				settingsDefinition.getSettingsClass())) {

			_settingsProviderBuilder = new GroupServiceSettingsProviderBuilder(
				settingsDefinition, settingsFactory);
		}
		else {
			throw new IllegalArgumentException(
				"Unable to determine service level for class " +
					SettingsDefinitionUtil.getSettingsClassName(
						settingsDefinition));
		}

		_configurationBeanBuilder = new ConfigurationBeanBuilder<>(
			settingsDefinition, _getConfigurationProperties());
	}

	public void start() {
		if (_log.isDebugEnabled()) {
			_log.debug(
				"Starting lifecycle of settings class " +
					SettingsDefinitionUtil.getSettingsClassName(
						_settingsDefinition));
		}

		_registerManagedService();

		_registerConfigurationBean();

		_settingsFactory.registerSettingsDefinition(
			_settingsDefinition,
			_configurationBeanBuilder.getConfigurationBean());

		_registerSettingsProvider();

		if (_log.isInfoEnabled()) {
			_log.info(
				"Started lifecycle of settings class " +
					SettingsDefinitionUtil.getSettingsClassName(
						_settingsDefinition));
		}
	}

	public void stop() {
		if (_log.isDebugEnabled()) {
			_log.debug(
				"Stopping lifecycle of settings class " +
					SettingsDefinitionUtil.getSettingsClassName(
						_settingsDefinition));
		}

		_unregisterSettingsProvider();

		_settingsFactory.unregisterSettingsDefinition(_settingsDefinition);

		_unregisterConfigurationBean();

		_unregisterManagedService();

		if (_log.isInfoEnabled()) {
			_log.info(
				"Stopped lifecycle of settings class " +
					SettingsDefinitionUtil.getSettingsClassName(
						_settingsDefinition));
		}
	}

	private Dictionary<String, ?> _getConfigurationProperties() {
		try {
			Configuration configuration = _configurationAdmin.getConfiguration(
				SettingsDefinitionUtil.getConfigurationPid(
					_settingsDefinition));

			Dictionary<String, ?> properties = configuration.getProperties();

			if (properties == null) {
				properties = _EMPTY_DICTIONARY;
			}

			return properties;
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private void _registerConfigurationBean() {
		_configurationBeanServiceRegistration = _bundleContext.registerService(
			_settingsDefinition.getConfigurationBeanClass(),
			_configurationBeanBuilder.getConfigurationBean(),
			_EMPTY_DICTIONARY);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Registered configuration bean service " +
					SettingsDefinitionUtil.getConfigurationBeanClassName(
						_settingsDefinition));
		}
	}

	private void _registerManagedService() {
		ManagedService managedService = new ManagedService() {
			@Override
			public void updated(Dictionary<String, ?> properties)
				throws ConfigurationException {

				if (properties == null) {
					properties = _EMPTY_DICTIONARY;
				}

				_configurationBeanBuilder.updateProperties(properties);
			}
		};

		Dictionary<String, String> properties = new HashMapDictionary<>();

		properties.put(
			Constants.SERVICE_PID,
			SettingsDefinitionUtil.getConfigurationPid(_settingsDefinition));

		_managedServiceServiceRegistration = _bundleContext.registerService(
			ManagedService.class, managedService, properties);

		_configurationBeanBuilder.updateProperties(
			_getConfigurationProperties());

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Registered listener for changes in configurationPid " +
					SettingsDefinitionUtil.getConfigurationPid(
						_settingsDefinition));
		}
	}

	private void _registerSettingsProvider() {
		Dictionary<String, String> properties = new HashMapDictionary<>();

		properties.put(
			"class.name",
			SettingsDefinitionUtil.getSettingsClassName(_settingsDefinition));

		Class settingsProviderServiceClass =
			_settingsProviderBuilder.getSettingsProviderServiceClass();

		_settingsProviderServiceRegistration = _bundleContext.registerService(
			settingsProviderServiceClass,
			_settingsProviderBuilder.getSettingsProvider(), properties);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Registered settings provider service " +
					settingsProviderServiceClass.getName() + "<" +
					SettingsDefinitionUtil.getSettingsClassName(
						_settingsDefinition) + ">");
		}
	}

	private void _unregisterConfigurationBean() {
		_configurationBeanServiceRegistration.unregister();

		_configurationBeanServiceRegistration = null;

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Unregistered configuration bean service " +
					SettingsDefinitionUtil.getConfigurationBeanClassName(
						_settingsDefinition));
		}
	}

	private void _unregisterManagedService() {
		_managedServiceServiceRegistration.unregister();

		_managedServiceServiceRegistration = null;

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Unregistered listener for changes in configurationPid " +
					SettingsDefinitionUtil.getConfigurationPid(
						_settingsDefinition));
		}
	}

	private void _unregisterSettingsProvider() {
		_settingsProviderServiceRegistration.unregister();

		_settingsProviderServiceRegistration = null;

		if (_log.isDebugEnabled()) {
			Class<?> settingsProviderServiceClass =
				_settingsProviderBuilder.getSettingsProviderServiceClass();

			_log.debug(
				"Unregistered settings provider service " +
					settingsProviderServiceClass.getName() + "<" +
					SettingsDefinitionUtil.getSettingsClassName(
						_settingsDefinition) + ">");
		}
	}

	private static final Dictionary<String, ?> _EMPTY_DICTIONARY =
		new HashMapDictionary<>();

	private static final Log _log = LogFactoryUtil.getLog(
		SettingsDefinitionLifecycleHandler.class);

	private final BundleContext _bundleContext;
	private final ConfigurationAdmin _configurationAdmin;
	private final ConfigurationBeanBuilder<C> _configurationBeanBuilder;
	private ServiceRegistration<C> _configurationBeanServiceRegistration;
	private ServiceRegistration<ManagedService>
		_managedServiceServiceRegistration;
	private final SettingsDefinition<S, C> _settingsDefinition;
	private final SettingsFactory _settingsFactory;
	private final SettingsProviderBuilder<?> _settingsProviderBuilder;
	private ServiceRegistration<?> _settingsProviderServiceRegistration;

}