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

package com.liferay.portal.spring.extender.internal.services.impl;

import com.liferay.portal.cache.configurator.PortalCacheConfigurator;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ServiceComponentLocalService;
import com.liferay.portal.spring.extender.internal.loader.ModuleResourceLoader;
import com.liferay.portal.spring.extender.internal.services.ServicesConfigurator;
import com.liferay.util.log4j.Log4JUtil;

import java.net.URL;

import java.util.List;
import java.util.Properties;

import org.eclipse.gemini.blueprint.context.BundleContextAware;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.wiring.BundleWiring;

/**
 * @author Miguel Pastor
 */
public class ServicesConfiguratorImpl
	implements BundleContextAware, ServicesConfigurator {

	@Override
	public void destroy() throws Exception {
		destroyServiceComponent();
	}

	@Override
	public void init() throws Exception {
		initLogger();
		initServiceComponent();

		reconfigureCaches();

		processServiceResourceActions();
	}

	@Override
	public void setBundleContext(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_classLoader = _getBundleClassLoader(_bundleContext.getBundle());
	}

	public void setServiceComponentLocalService(
		ServiceComponentLocalService serviceComponentLocalService) {

		_serviceComponentLocalService = serviceComponentLocalService;
	}

	protected void destroyServiceComponent() throws Exception {
		_serviceComponentLocalService.destroyServiceComponent(
			new ModuleResourceLoader(_bundleContext.getBundle()), _classLoader);
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

	protected PortalCacheConfigurator getPortalCacheConfigurator() {
		ServiceReference<PortalCacheConfigurator> serviceReference =
			_bundleContext.getServiceReference(PortalCacheConfigurator.class);

		return _bundleContext.getService(serviceReference);
	}

	protected void initLogger() {
		Log4JUtil.configureLog4J(
			_classLoader.getResource("META-INF/portal-log4j.xml"));
	}

	protected void initServiceComponent() {
		Configuration serviceBuilderPropertiesConfiguration = null;

		try {
			serviceBuilderPropertiesConfiguration =
				ConfigurationFactoryUtil.getConfiguration(
					_classLoader, "service");
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to read service.properties");
			}

			return;
		}

		Properties serviceBuilderProperties =
			serviceBuilderPropertiesConfiguration.getProperties();

		if (serviceBuilderProperties.isEmpty()) {
			return;
		}

		String buildNamespace = GetterUtil.getString(
			serviceBuilderProperties.getProperty("build.namespace"));
		long buildNumber = GetterUtil.getLong(
			serviceBuilderProperties.getProperty("build.number"));
		long buildDate = GetterUtil.getLong(
			serviceBuilderProperties.getProperty("build.date"));
		boolean buildAutoUpgrade = GetterUtil.getBoolean(
			serviceBuilderProperties.getProperty("build.auto.upgrade"), true);

		if (_log.isDebugEnabled()) {
			_log.debug("Build namespace " + buildNamespace);
			_log.debug("Build number " + buildNumber);
			_log.debug("Build date " + buildDate);
			_log.debug("Build auto upgrade " + buildAutoUpgrade);
		}

		if (Validator.isNull(buildNamespace)) {
			return;
		}

		try {
			_serviceComponentLocalService.initServiceComponent(
				new ModuleResourceLoader(_bundleContext.getBundle()),
				_classLoader, buildNamespace, buildNumber, buildDate,
				buildAutoUpgrade);
		}
		catch (PortalException pe) {
			_log.error("Unable to register component", pe);
		}
	}

	protected void processServiceResourceActions() {
		Configuration configuration = ConfigurationFactoryUtil.getConfiguration(
			_classLoader, "portlet");

		String resourceActionsConfigs = configuration.get(
			PropsKeys.RESOURCE_ACTIONS_CONFIGS);

		String[] resourceActionConfigs = resourceActionsConfigs.split(",");

		for (String resourceActionConfig : resourceActionConfigs) {
			try {
				ResourceActionsUtil.read(
					"", _classLoader, resourceActionConfig);
			}
			catch (Exception e) {
				_log.error(
					"Unable to process resource config actions defined in " +
						resourceActionConfig,
					e);
			}
		}

		String portletIds = configuration.get("portlet.ids");

		String[] portletsIds = portletIds.split(",");

		for (String portletId : portletsIds) {
			List<String> modelNames =
				ResourceActionsUtil.getPortletModelResources(portletId);

			List<String> portletActions =
				ResourceActionsUtil.getPortletResourceActions(portletId);

			ResourceActionLocalServiceUtil.checkResourceActions(
				portletId, portletActions);

			for (String modelName : modelNames) {
				List<String> modelActions =
					ResourceActionsUtil.getModelResourceActions(modelName);

				ResourceActionLocalServiceUtil.checkResourceActions(
					modelName, modelActions);
			}
		}
	}

	protected void reconfigureCaches() throws Exception {
		Configuration portletPropertiesConfiguration = null;

		try {
			portletPropertiesConfiguration =
				ConfigurationFactoryUtil.getConfiguration(
					_classLoader, "portlet");
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to read portlet.properties");
			}

			return;
		}

		PortalCacheConfigurator portalCacheConfigurator =
			getPortalCacheConfigurator();

		portalCacheConfigurator.reconfigureCaches(
			_classLoader,
			getPortalCacheConfigurationURL(
				portletPropertiesConfiguration, _classLoader,
				PropsKeys.EHCACHE_SINGLE_VM_CONFIG_LOCATION));

		portalCacheConfigurator.reconfigureCaches(
			_classLoader,
			getPortalCacheConfigurationURL(
				portletPropertiesConfiguration, _classLoader,
				PropsKeys.EHCACHE_MULTI_VM_CONFIG_LOCATION));

		portalCacheConfigurator.reconfigureHibernateCache(
			getPortalCacheConfigurationURL(
				portletPropertiesConfiguration, _classLoader,
				PropsKeys.NET_SF_EHCACHE_CONFIGURATION_RESOURCE_NAME));
	}

	private ClassLoader _getBundleClassLoader(Bundle bundle) {
		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		return bundleWiring.getClassLoader();
	}

	private static Log _log = LogFactoryUtil.getLog(
		ServicesConfiguratorImpl.class);

	private BundleContext _bundleContext;
	private ClassLoader _classLoader;
	private ServiceComponentLocalService _serviceComponentLocalService;

}