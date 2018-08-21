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

package com.liferay.portal.service.configuration.configurator.impl;

import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.cache.configurator.PortalCacheConfiguratorSettings;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ServiceComponentLocalService;
import com.liferay.portal.kernel.service.configuration.ServiceComponentConfiguration;
import com.liferay.portal.kernel.service.configuration.configurator.ServiceConfigurator;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistrar;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Miguel Pastor
 */
public class ServiceConfiguratorImpl implements ServiceConfigurator {

	public void destory() {
		if (_serviceRegistrar != null) {
			_serviceRegistrar.destroy();
		}
	}

	@Override
	public void destroyServices(
			ServiceComponentConfiguration serviceComponentConfiguration,
			ClassLoader classLoader)
		throws Exception {

		destroyServiceComponent(serviceComponentConfiguration, classLoader);
	}

	@Override
	public void initServices(
			ServiceComponentConfiguration serviceComponentConfiguration,
			ClassLoader classLoader)
		throws Exception {

		_initServiceComponent(serviceComponentConfiguration, classLoader);

		Configuration configuration = null;

		try {
			configuration = ConfigurationFactoryUtil.getConfiguration(
				classLoader, "portlet");
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to read portlet.properties");
			}

			return;
		}

		_reconfigureCaches(classLoader, configuration);

		_readResourceActions(classLoader, configuration);
	}

	public void setResourceActionLocalService(
		ResourceActionLocalService resourceActionLocalService) {

		_resourceActionLocalService = resourceActionLocalService;
	}

	public void setResourceActions(ResourceActions resourceActions) {
		_resourceActions = resourceActions;
	}

	public void setServiceComponentLocalService(
		ServiceComponentLocalService serviceComponentLocalService) {

		_serviceComponentLocalService = serviceComponentLocalService;
	}

	protected void destroyServiceComponent(
			ServiceComponentConfiguration serviceComponentConfiguration,
			ClassLoader classLoader)
		throws Exception {

		_serviceComponentLocalService.destroyServiceComponent(
			serviceComponentConfiguration, classLoader);
	}

	protected URL getPortalCacheConfigurationURL(
		Configuration configuration, ClassLoader classLoader,
		String configLocation) {

		String cacheConfigurationLocation = configuration.get(configLocation);

		if (Validator.isNull(cacheConfigurationLocation)) {
			return null;
		}

		return classLoader.getResource(cacheConfigurationLocation);
	}

	private void _initServiceComponent(
		ServiceComponentConfiguration serviceComponentConfiguration,
		ClassLoader classLoader) {

		Configuration configuration = null;

		try {
			configuration = ConfigurationFactoryUtil.getConfiguration(
				classLoader, "service");
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to read service.properties");
			}

			return;
		}

		Properties properties = configuration.getProperties();

		if (properties.isEmpty()) {
			return;
		}

		String buildNamespace = GetterUtil.getString(
			properties.getProperty("build.namespace"));
		long buildNumber = GetterUtil.getLong(
			properties.getProperty("build.number"));
		long buildDate = GetterUtil.getLong(
			properties.getProperty("build.date"));

		if (_log.isDebugEnabled()) {
			_log.debug("Build namespace " + buildNamespace);
			_log.debug("Build number " + buildNumber);
			_log.debug("Build date " + buildDate);
		}

		if (Validator.isNull(buildNamespace)) {
			return;
		}

		try {
			_serviceComponentLocalService.initServiceComponent(
				serviceComponentConfiguration, classLoader, buildNamespace,
				buildNumber, buildDate);
		}
		catch (PortalException pe) {
			_log.error("Unable to initialize service component", pe);
		}
	}

	private void _readResourceActions(
		ClassLoader classLoader, Configuration configuration) {

		try {
			String portlets = configuration.get(
				"service.configurator.portlet.ids");

			if (Validator.isNull(portlets)) {
				_resourceActions.readAndCheck(
					null, classLoader,
					StringUtil.split(
						configuration.get(PropsKeys.RESOURCE_ACTIONS_CONFIGS)));
			}
			else {
				_resourceActions.read(
					null, classLoader,
					StringUtil.split(
						configuration.get(PropsKeys.RESOURCE_ACTIONS_CONFIGS)));

				for (String portletId : StringUtil.split(portlets)) {
					_resourceActions.check(portletId);
				}
			}
		}
		catch (Exception e) {
			_log.error(
				"Unable to read resource actions config in " +
					PropsKeys.RESOURCE_ACTIONS_CONFIGS,
				e);
		}
	}

	private void _reconfigureCaches(
			ClassLoader classLoader, Configuration configuration)
		throws Exception {

		String singleVMConfigurationLocation = configuration.get(
			PropsKeys.EHCACHE_SINGLE_VM_CONFIG_LOCATION);
		String multiVMConfigurationLocation = configuration.get(
			PropsKeys.EHCACHE_MULTI_VM_CONFIG_LOCATION);

		if (Validator.isNull(singleVMConfigurationLocation) &&
			Validator.isNull(multiVMConfigurationLocation)) {

			return;
		}

		if (_serviceRegistrar == null) {
			Registry registry = RegistryUtil.getRegistry();

			_serviceRegistrar = registry.getServiceRegistrar(
				PortalCacheConfiguratorSettings.class);
		}

		if (Validator.isNotNull(singleVMConfigurationLocation)) {
			Map<String, Object> properties = new HashMap<>();

			properties.put(
				"portal.cache.manager.name", PortalCacheManagerNames.SINGLE_VM);

			_serviceRegistrar.registerService(
				PortalCacheConfiguratorSettings.class,
				new PortalCacheConfiguratorSettings(
					classLoader, singleVMConfigurationLocation),
				properties);
		}

		if (Validator.isNotNull(multiVMConfigurationLocation)) {
			Map<String, Object> properties = new HashMap<>();

			properties.put(
				"portal.cache.manager.name", PortalCacheManagerNames.MULTI_VM);

			_serviceRegistrar.registerService(
				PortalCacheConfiguratorSettings.class,
				new PortalCacheConfiguratorSettings(
					classLoader, multiVMConfigurationLocation),
				properties);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServiceConfiguratorImpl.class);

	private ResourceActionLocalService _resourceActionLocalService;
	private ResourceActions _resourceActions;
	private ServiceComponentLocalService _serviceComponentLocalService;
	private volatile ServiceRegistrar<PortalCacheConfiguratorSettings>
		_serviceRegistrar;

}