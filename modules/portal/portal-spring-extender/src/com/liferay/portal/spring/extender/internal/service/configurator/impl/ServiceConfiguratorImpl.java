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

package com.liferay.portal.spring.extender.internal.service.configurator.impl;

import com.liferay.portal.cache.configurator.PortalCacheConfigurator;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ServiceComponentLocalService;
import com.liferay.portal.spring.extender.internal.loader.ModuleResourceLoader;
import com.liferay.portal.spring.extender.internal.service.configurator.ServiceConfigurator;
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
public class ServiceConfiguratorImpl
	implements BundleContextAware, ServiceConfigurator {

	@Override
	public void destroy() throws Exception {
		destroyServiceComponent();
	}

	@Override
	public void init() throws Exception {
		initLog4J();

		initServiceComponent();

		reconfigureCaches();

		readResourceActions();
	}

	@Override
	public void setBundleContext(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_classLoader = getBundleClassLoader(_bundleContext.getBundle());
	}

	public void setServiceComponentLocalService(
		ServiceComponentLocalService serviceComponentLocalService) {

		_serviceComponentLocalService = serviceComponentLocalService;
	}

	protected void destroyServiceComponent() throws Exception {
		_serviceComponentLocalService.destroyServiceComponent(
			new ModuleResourceLoader(_bundleContext.getBundle()), _classLoader);
	}

	protected ClassLoader getBundleClassLoader(Bundle bundle) {
		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		return bundleWiring.getClassLoader();
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

	protected void initLog4J() {
		Log4JUtil.configureLog4J(
			_classLoader.getResource("META-INF/portal-log4j.xml"));
	}

	protected void initServiceComponent() {
		Configuration configuration = null;

		try {
			configuration = ConfigurationFactoryUtil.getConfiguration(
				_classLoader, "service");
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
		boolean buildAutoUpgrade = GetterUtil.getBoolean(
			properties.getProperty("build.auto.upgrade"), true);

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
			_log.error("Unable to initialize service component", pe);
		}
	}

	protected void readResourceActions() {
		Configuration configuration = ConfigurationFactoryUtil.getConfiguration(
			_classLoader, "portlet");

		String[] resourceActionsConfigs = StringUtil.split(
			configuration.get(PropsKeys.RESOURCE_ACTIONS_CONFIGS));

		for (String resourceActionsConfig : resourceActionsConfigs) {
			try {
				ResourceActionsUtil.read(
					null, _classLoader, resourceActionsConfig);
			}
			catch (Exception e) {
				_log.error(
					"Unable to read resource actions config in " +
						resourceActionsConfig,
					e);
			}
		}

		String[] portletIds = StringUtil.split(
			configuration.get("portlet.ids"));

		for (String portletId : portletIds) {
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
		Configuration configuration = null;

		try {
			configuration = ConfigurationFactoryUtil.getConfiguration(
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
				configuration, _classLoader,
				PropsKeys.EHCACHE_SINGLE_VM_CONFIG_LOCATION));

		portalCacheConfigurator.reconfigureCaches(
			_classLoader,
			getPortalCacheConfigurationURL(
				configuration, _classLoader,
				PropsKeys.EHCACHE_MULTI_VM_CONFIG_LOCATION));

		portalCacheConfigurator.reconfigureHibernateCache(
			getPortalCacheConfigurationURL(
				configuration, _classLoader,
				PropsKeys.NET_SF_EHCACHE_CONFIGURATION_RESOURCE_NAME));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServiceConfiguratorImpl.class);

	private BundleContext _bundleContext;
	private ClassLoader _classLoader;
	private ServiceComponentLocalService _serviceComponentLocalService;

}