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

package com.liferay.portal.dao.orm.hibernate.region;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.CacheConfiguration.BootstrapCacheLoaderFactoryConfiguration;
import net.sf.ehcache.config.CacheConfiguration.CacheEventListenerFactoryConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ConfigurationFactory;
import net.sf.ehcache.config.FactoryConfiguration;

/**
 * <p>
 * See https://issues.liferay.com/browse/LPS-48535.
 * </p>
 *
 * @author Shuyang Zhou
 * @author Edward Han
 * @author Tina Tian
 */
public class EhcacheConfigurationUtil {

	public static Configuration getConfiguration(
		String configurationPath, boolean usingDefault) {

		if (Validator.isNull(configurationPath)) {
			return null;
		}

		URL configurationURL = EhcacheConfigurationUtil.class.getResource(
			configurationPath);

		return getConfiguration(configurationURL, usingDefault);
	}

	public static Configuration getConfiguration(
		URL configurationURL, boolean usingDefault) {

		if (configurationURL == null) {
			return null;
		}

		Configuration configuration = ConfigurationFactory.parseConfiguration(
			configurationURL);

		List<CacheConfiguration> cacheConfigurations =
			_getAllCacheConfigurations(configuration);

		if (!PropsValues.EHCACHE_BOOTSTRAP_CACHE_LOADER_ENABLED) {
			_clearBootstrapCacheLoaderConfigurations(cacheConfigurations);
		}

		if (PropsValues.CLUSTER_LINK_ENABLED &&
			!PropsValues.EHCACHE_CLUSTER_LINK_REPLICATION_ENABLED) {

			return configuration;
		}

		_configureEhcacheReplication(
			configuration, cacheConfigurations, usingDefault);

		return configuration;
	}

	private static void _clearBootstrapCacheLoaderConfigurations(
		List<CacheConfiguration> cacheConfigurations) {

		for (CacheConfiguration cacheConfiguration : cacheConfigurations) {
			cacheConfiguration.addBootstrapCacheLoaderFactory(null);
		}
	}

	private static String _clearCacheEventListenerConfigurations(
		CacheConfiguration cacheConfiguration, boolean usingDefault) {

		List<CacheEventListenerFactoryConfiguration>
			cacheEventListenerConfigurations =
				cacheConfiguration.getCacheEventListenerConfigurations();

		List<CacheEventListenerFactoryConfiguration>
			copyCacheEventListenerConfigurations = new ArrayList<>(
				cacheEventListenerConfigurations);

		if (usingDefault) {
			cacheEventListenerConfigurations.clear();
		}

		for (CacheEventListenerFactoryConfiguration
				cacheEventListenerFactoryConfiguration :
					copyCacheEventListenerConfigurations) {

			String fullyQualifiedClassPath =
				cacheEventListenerFactoryConfiguration.
					getFullyQualifiedClassPath();

			if (fullyQualifiedClassPath.contains(
					"LiferayCacheEventListenerFactory") ||
				fullyQualifiedClassPath.contains(
					"net.sf.ehcache.distribution")) {

				cacheEventListenerConfigurations.remove(
					cacheEventListenerFactoryConfiguration);

				return cacheEventListenerFactoryConfiguration.getProperties();
			}
		}

		return _NO_PROPERTIES;
	}

	@SuppressWarnings("rawtypes")
	private static void _configureEhcacheReplication(
		Configuration configuration,
		List<CacheConfiguration> cacheConfigurations, boolean usingDefault) {

		List<FactoryConfiguration> factoryConfigurations =
			configuration.getCacheManagerPeerListenerFactoryConfigurations();

		factoryConfigurations.clear();

		factoryConfigurations =
			configuration.getCacheManagerPeerProviderFactoryConfiguration();

		factoryConfigurations.clear();

		for (CacheConfiguration cacheConfiguration : cacheConfigurations) {
			String properties = _clearCacheEventListenerConfigurations(
				cacheConfiguration, usingDefault);

			_enableClusterLinkReplication(cacheConfiguration, properties);

			FactoryConfiguration factoryConfiguration =
				cacheConfiguration.
					getBootstrapCacheLoaderFactoryConfiguration();

			if (factoryConfiguration != null) {
				cacheConfiguration.addBootstrapCacheLoaderFactory(null);
			}

			_enableClusterLinkBootstrap(
				cacheConfiguration, factoryConfiguration);
		}
	}

	@SuppressWarnings("rawtypes")
	private static void _enableClusterLinkBootstrap(
		CacheConfiguration cacheConfiguration,
		FactoryConfiguration factoryConfiguration) {

		if ((factoryConfiguration == null) ||
			!PropsValues.EHCACHE_CLUSTER_LINK_REPLICATION_ENABLED) {

			return;
		}

		BootstrapCacheLoaderFactoryConfiguration
			bootstrapCacheLoaderFactoryConfiguration =
				new BootstrapCacheLoaderFactoryConfiguration();

		bootstrapCacheLoaderFactoryConfiguration.setClass(
			EhcacheStreamBootstrapCacheLoaderFactory.class.getName());
		bootstrapCacheLoaderFactoryConfiguration.setProperties(
			factoryConfiguration.getProperties());

		cacheConfiguration.addBootstrapCacheLoaderFactory(
			bootstrapCacheLoaderFactoryConfiguration);
	}

	private static void _enableClusterLinkReplication(
		CacheConfiguration cacheConfiguration,
		String cacheEventListenerProperties) {

		if (cacheEventListenerProperties.equals(_NO_PROPERTIES) ||
			!PropsValues.EHCACHE_CLUSTER_LINK_REPLICATION_ENABLED) {

			return;
		}

		CacheEventListenerFactoryConfiguration
			cacheEventListenerFactoryConfiguration =
				new CacheEventListenerFactoryConfiguration();

		cacheEventListenerFactoryConfiguration.setClass(
			EhcachePortalCacheClusterReplicatorFactory.class.getName());
		cacheEventListenerFactoryConfiguration.setProperties(
			cacheEventListenerProperties);

		cacheConfiguration.addCacheEventListenerFactory(
			cacheEventListenerFactoryConfiguration);
	}

	private static List<CacheConfiguration> _getAllCacheConfigurations(
		Configuration configuration) {

		List<CacheConfiguration> cacheConfigurations = new ArrayList<>();

		CacheConfiguration defaultCacheConfiguration =
			configuration.getDefaultCacheConfiguration();

		if (defaultCacheConfiguration != null) {
			cacheConfigurations.add(defaultCacheConfiguration);
		}

		Map<String, CacheConfiguration> cacheConfigurationsMap =
			configuration.getCacheConfigurations();

		for (CacheConfiguration cacheConfiguration :
				cacheConfigurationsMap.values()) {

			cacheConfigurations.add(cacheConfiguration);
		}

		return cacheConfigurations;
	}

	private static final String _NO_PROPERTIES = "_NO_PROPERTIES";

}