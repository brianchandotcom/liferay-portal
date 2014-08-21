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

package com.liferay.portal.deploy.hot;

import com.liferay.portal.cache.configurator.util.PortalCacheConfiguratorUtil;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.deploy.hot.BaseHotDeployListener;
import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.plugin.PluginPackageUtil;
import com.liferay.portal.service.ServiceComponentLocalServiceUtil;
import com.liferay.portal.service.configuration.ServiceComponentConfiguration;
import com.liferay.portal.service.configuration.servlet.ServletServiceContextComponentConfiguration;
import com.liferay.util.log4j.Log4JUtil;
import com.liferay.util.portlet.PortletProps;

import java.lang.reflect.Method;

import java.net.URL;

import java.util.Properties;

import javax.servlet.ServletContext;

/**
 * @author Jorge Ferrer
 */
public class PluginPackageHotDeployListener extends BaseHotDeployListener {

	public static final String SERVICE_BUILDER_PROPERTIES =
		"SERVICE_BUILDER_PROPERTIES";

	@Override
	public void invokeDeploy(HotDeployEvent hotDeployEvent)
		throws HotDeployException {

		try {
			doInvokeDeploy(hotDeployEvent);
		}
		catch (Throwable t) {
			throwHotDeployException(
				hotDeployEvent, "Error registering plugins for ", t);
		}
	}

	@Override
	public void invokeUndeploy(HotDeployEvent hotDeployEvent)
		throws HotDeployException {

		try {
			doInvokeUndeploy(hotDeployEvent);
		}
		catch (Throwable t) {
			throwHotDeployException(
				hotDeployEvent, "Error unregistering plugins for ", t);
		}
	}

	protected void destroyServiceComponent(
			ServiceComponentConfiguration serviceComponentConfiguration,
			ClassLoader classLoader)
		throws Exception {

		ServiceComponentLocalServiceUtil.destroyServiceComponent(
			serviceComponentConfiguration, classLoader);
	}

	protected void doInvokeDeploy(HotDeployEvent hotDeployEvent)
		throws Exception {

		ServletContext servletContext = hotDeployEvent.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking deploy for " + servletContextName);
		}

		PluginPackage pluginPackage =
			PluginPackageUtil.readPluginPackageServletContext(servletContext);

		if (pluginPackage == null) {
			return;
		}

		if (servletContext.getResource(
				"/WEB-INF/liferay-theme-loader.xml") != null) {

			PluginPackageUtil.registerInstalledPluginPackage(pluginPackage);

			return;
		}

		pluginPackage.setContext(servletContextName);

		hotDeployEvent.setPluginPackage(pluginPackage);

		PluginPackageUtil.registerInstalledPluginPackage(pluginPackage);

		ClassLoader classLoader = hotDeployEvent.getContextClassLoader();

		initLogger(classLoader);
		initPortletProps(classLoader);
		initServiceComponent(servletContext, classLoader);

		registerClpMessageListeners(servletContext, classLoader);

		reconfigureCaches(classLoader);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Plugin package " + pluginPackage.getModuleId() +
					" registered successfully. It's now ready to be used.");
		}
	}

	protected void doInvokeUndeploy(HotDeployEvent hotDeployEvent)
		throws Exception {

		ServletContext servletContext = hotDeployEvent.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking undeploy for " + servletContextName);
		}

		PluginPackage pluginPackage =
			PluginPackageUtil.readPluginPackageServletContext(servletContext);

		if (pluginPackage == null) {
			return;
		}

		hotDeployEvent.setPluginPackage(pluginPackage);

		PluginPackageUtil.unregisterInstalledPluginPackage(pluginPackage);

		ServletContextPool.remove(servletContextName);

		destroyServiceComponent(
			new ServletServiceContextComponentConfiguration(servletContext),
			hotDeployEvent.getContextClassLoader());

		unregisterClpMessageListeners(servletContext);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Plugin package " + pluginPackage.getModuleId() +
					" unregistered successfully");
		}
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

	protected void initLogger(ClassLoader classLoader) {
		Log4JUtil.configureLog4J(
			classLoader.getResource("META-INF/portal-log4j.xml"));
	}

	protected void initPortletProps(ClassLoader classLoader) throws Exception {
		if (classLoader.getResourceAsStream("portlet.properties") == null) {
			return;
		}

		Class<?> clazz = classLoader.loadClass(PortletProps.class.getName());

		Method method = clazz.getMethod("get", String.class);

		method.invoke(null, "init");
	}

	protected void initServiceComponent(
			ServletContext servletContext, ClassLoader classLoader)
		throws Exception {

		Configuration serviceBuilderPropertiesConfiguration = null;

		try {
			serviceBuilderPropertiesConfiguration =
				ConfigurationFactoryUtil.getConfiguration(
					classLoader, "service");
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

		servletContext.setAttribute(
			SERVICE_BUILDER_PROPERTIES, serviceBuilderProperties);

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

		ServiceComponentLocalServiceUtil.initServiceComponent(
			new ServletServiceContextComponentConfiguration(servletContext),
			classLoader, buildNamespace, buildNumber, buildDate,
			buildAutoUpgrade);
	}

	protected void reconfigureCaches(ClassLoader classLoader) throws Exception {
		Configuration portletPropertiesConfiguration = null;

		try {
			portletPropertiesConfiguration =
				ConfigurationFactoryUtil.getConfiguration(
					classLoader, "portlet");
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to read portlet.properties");
			}

			return;
		}

		PortalCacheConfiguratorUtil.reconfigureCaches(
			classLoader,
			getPortalCacheConfigurationURL(
				portletPropertiesConfiguration, classLoader,
				PropsKeys.EHCACHE_SINGLE_VM_CONFIG_LOCATION));

		PortalCacheConfiguratorUtil.reconfigureCaches(
			classLoader,
			getPortalCacheConfigurationURL(
				portletPropertiesConfiguration, classLoader,
				PropsKeys.EHCACHE_MULTI_VM_CONFIG_LOCATION));

		PortalCacheConfiguratorUtil.reconfigureHibernateCache(
			getPortalCacheConfigurationURL(
				portletPropertiesConfiguration, classLoader,
				PropsKeys.NET_SF_EHCACHE_CONFIGURATION_RESOURCE_NAME));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PluginPackageHotDeployListener.class);

}