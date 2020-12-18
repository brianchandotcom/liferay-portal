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

package com.liferay.portal.spring.extender.internal.configuration;

import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ServiceComponentLocalService;
import com.liferay.portal.kernel.service.configuration.ServiceComponentConfiguration;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.internal.loader.ModuleResourceLoader;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Properties;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Preston Crary
 */
public class ServiceConfigurationInitializer {

	public ServiceConfigurationInitializer(
		Bundle bundle, ClassLoader classLoader,
		Configuration portletConfiguration, Configuration serviceConfiguration,
		ResourceActions resourceActions,
		ServiceComponentLocalService serviceComponentLocalService) {

		_bundle = bundle;
		_classLoader = classLoader;
		_portletConfiguration = portletConfiguration;
		_serviceConfiguration = serviceConfiguration;
		_resourceActions = resourceActions;
		_serviceComponentLocalService = serviceComponentLocalService;

		_serviceComponentConfiguration = new ModuleResourceLoader(bundle);
	}

	public void stop() {
		_serviceComponentLocalService.destroyServiceComponent(
			_serviceComponentConfiguration, _classLoader);

		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}

		_serviceRegistrations.clear();
	}

	protected void start() {
		BundleContext bundleContext = _bundle.getBundleContext();

		if (_portletConfiguration != null) {
			_readResourceActions();

			_registerConfiguration(
				bundleContext, _portletConfiguration, "portlet");
		}

		if (_serviceConfiguration != null) {
			_initServiceComponent();

			_registerConfiguration(
				bundleContext, _serviceConfiguration, "service");
		}
	}

	private void _initServiceComponent() {
		Properties properties = _serviceConfiguration.getProperties();

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
				_serviceComponentConfiguration, _classLoader, buildNamespace,
				buildNumber, buildDate);
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to initialize service component", portalException);
		}
	}

	private void _readResourceActions() {
		try {
			_resourceActions.populateModelResources(
				_classLoader,
				StringUtil.split(
					_portletConfiguration.get(
						PropsKeys.RESOURCE_ACTIONS_CONFIGS)));
		}
		catch (Exception exception) {
			_log.error(
				"Unable to read resource actions config in " +
					PropsKeys.RESOURCE_ACTIONS_CONFIGS,
				exception);
		}
	}

	private void _registerConfiguration(
		BundleContext bundleContext, Configuration configuration, String name) {

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("name", name);
		properties.put(
			"origin.bundle.symbolic.name", _bundle.getSymbolicName());

		_serviceRegistrations.add(
			bundleContext.registerService(
				Configuration.class, configuration, properties));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServiceConfigurationInitializer.class);

	private final Bundle _bundle;
	private final ClassLoader _classLoader;
	private final Configuration _portletConfiguration;
	private final ResourceActions _resourceActions;
	private final ServiceComponentConfiguration _serviceComponentConfiguration;
	private final ServiceComponentLocalService _serviceComponentLocalService;
	private final Configuration _serviceConfiguration;
	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new ArrayList<>();

}