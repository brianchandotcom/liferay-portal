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

package com.liferay.portal.cache.ehcache;

import com.liferay.portal.cache.cluster.ClusterLinkCallbackFactory;
import com.liferay.portal.kernel.cache.CacheListenerScope;
import com.liferay.portal.kernel.cache.configuration.CallbackConfiguration;
import com.liferay.portal.kernel.cache.configuration.PortalCacheConfiguration;
import com.liferay.portal.kernel.cache.configuration.PortalCacheManagerConfiguration;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.net.URL;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.CacheConfiguration.BootstrapCacheLoaderFactoryConfiguration;
import net.sf.ehcache.config.CacheConfiguration.CacheEventListenerFactoryConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ConfigurationFactory;
import net.sf.ehcache.config.FactoryConfiguration;
import net.sf.ehcache.event.NotificationScope;

/**
 * @author Tina Tian
 */
public class EhcacheConfigurationHelperUtil {

	public static ObjectValuePair
		<Configuration, PortalCacheManagerConfiguration>getConfiguration(
			String configurationPath) {

		return getConfiguration(configurationPath, false, false);
	}

	public static ObjectValuePair
		<Configuration, PortalCacheManagerConfiguration>getConfiguration(
			String configurationPath, boolean clusterAware) {

		return getConfiguration(configurationPath, clusterAware, false);
	}

	public static ObjectValuePair
		<Configuration, PortalCacheManagerConfiguration>getConfiguration(
			String configurationPath, boolean clusterAware,
			boolean usingDefault) {

		if (configurationPath == null) {
			throw new NullPointerException("Configuration path is null");
		}

		return getConfiguration(
			EhcacheConfigurationHelperUtil.class.getResource(configurationPath),
			clusterAware, usingDefault);
	}

	public static ObjectValuePair
		<Configuration, PortalCacheManagerConfiguration>getConfiguration(
			URL configurationURL) {

		return getConfiguration(configurationURL, false, false);
	}

	public static ObjectValuePair
		<Configuration, PortalCacheManagerConfiguration>getConfiguration(
			URL configurationURL, boolean clusterAware) {

		return getConfiguration(configurationURL, clusterAware, false);
	}

	public static ObjectValuePair
		<Configuration, PortalCacheManagerConfiguration>getConfiguration(
			URL configurationURL, boolean clusterAware, boolean usingDefault) {

		if (configurationURL == null) {
			throw new NullPointerException("Configuration path is null");
		}

		Configuration ehcacheConfiguration =
			ConfigurationFactory.parseConfiguration(configurationURL);

		List<?> peerProviderConfiguration =
			ehcacheConfiguration.
				getCacheManagerPeerProviderFactoryConfiguration();

		if (!peerProviderConfiguration.isEmpty() &&
			(!clusterAware || !PropsValues.CLUSTER_LINK_ENABLED ||
			 PropsValues.EHCACHE_CLUSTER_LINK_REPLICATION_ENABLED)) {

			peerProviderConfiguration.clear();
		}

		peerProviderConfiguration =
			ehcacheConfiguration.
				getCacheManagerPeerListenerFactoryConfigurations();

		if (!peerProviderConfiguration.isEmpty() &&
			(!clusterAware || !PropsValues.CLUSTER_LINK_ENABLED ||
			 PropsValues.EHCACHE_CLUSTER_LINK_REPLICATION_ENABLED)) {

			peerProviderConfiguration.clear();
		}

		Set<CallbackConfiguration> cacheManagerListenerConfigurations =
			_getCacheManagerListenerConfigurations(ehcacheConfiguration);

		PortalCacheConfiguration defaultPortalCacheConfiguration =
			_parseCacheConfiguration(
				ehcacheConfiguration.getDefaultCacheConfiguration(),
				clusterAware, usingDefault);

		Set<PortalCacheConfiguration> portalCacheConfigurations =
			new HashSet<>();

		Map<String, CacheConfiguration> cacheConfigurations =
			ehcacheConfiguration.getCacheConfigurations();

		for (Map.Entry<String, CacheConfiguration> entry :
				cacheConfigurations.entrySet()) {

			portalCacheConfigurations.add(
				_parseCacheConfiguration(
					entry.getValue(), clusterAware, usingDefault));
		}

		PortalCacheManagerConfiguration portalCacheManagerConfiguration =
			new PortalCacheManagerConfiguration(
				cacheManagerListenerConfigurations,
				defaultPortalCacheConfiguration, portalCacheConfigurations);

		return new ObjectValuePair
			<Configuration, PortalCacheManagerConfiguration>(
				ehcacheConfiguration, portalCacheManagerConfiguration);
	}

	private static CacheListenerScope _getCacheListenerScope(
		NotificationScope notificationScope) {

		if (notificationScope == NotificationScope.ALL) {
			return CacheListenerScope.ALL;
		}
		else if (notificationScope == NotificationScope.LOCAL) {
			return CacheListenerScope.LOCAL;
		}
		else if (notificationScope == NotificationScope.REMOTE) {
			return CacheListenerScope.REMOTE;
		}

		throw new IllegalArgumentException(
			"Unable to parse notification scope " + notificationScope);
	}

	private static Set<CallbackConfiguration>
		_getCacheManagerListenerConfigurations(
			Configuration ehcacheConfiguration) {

		FactoryConfiguration<?> factoryConfiguration =
			ehcacheConfiguration.
				getCacheManagerEventListenerFactoryConfiguration();

		if (factoryConfiguration == null) {
			return Collections.emptySet();
		}

		Properties properties = _parseProperties(
			factoryConfiguration.getProperties(),
			factoryConfiguration.getPropertySeparator());

		properties.put(
			EhcacheConstants.CACHE_MANAGER_LISTENER_FACTORY_CLASS_NAME,
			factoryConfiguration.getFullyQualifiedClassPath());
		properties.put(
			EhcacheConstants.PORTAL_CACHE_MANAGER_NAME,
			ehcacheConfiguration.getName());

		return Collections.singleton(
			new CallbackConfiguration(
				EhcacheCallbackFactory.INSTANCE, properties));
	}

	private static PortalCacheConfiguration _parseCacheConfiguration(
		CacheConfiguration cacheConfiguration, boolean clusterAware,
		boolean usingDefault) {

		if (cacheConfiguration == null) {
			return null;
		}

		String portalCacheName = cacheConfiguration.getName();

		if (portalCacheName == null) {
			portalCacheName =
				PortalCacheConfiguration.DEFAULT_PORTAL_CACHE_NAME;
		}

		Map<CallbackConfiguration, CacheListenerScope>
			cacheListenerConfigurations = new HashMap<>();

		List<CacheEventListenerFactoryConfiguration>
			cacheEventListenerConfigurations =
				cacheConfiguration.getCacheEventListenerConfigurations();

		for (CacheEventListenerFactoryConfiguration
				cacheEventListenerFactoryConfiguration :
					cacheEventListenerConfigurations) {

			String fullyQualifiedClassPath =
				cacheEventListenerFactoryConfiguration.
					getFullyQualifiedClassPath();

			Properties properties = _parseProperties(
				cacheEventListenerFactoryConfiguration.getProperties(),
				cacheEventListenerFactoryConfiguration. getPropertySeparator());

			CacheListenerScope cacheListenerScope = _getCacheListenerScope(
				cacheEventListenerFactoryConfiguration.getListenFor());

			if (fullyQualifiedClassPath.contains(
					"LiferayCacheEventListenerFactory") ||
				fullyQualifiedClassPath.contains(
					"net.sf.ehcache.distribution")) {

				if (clusterAware && PropsValues.CLUSTER_LINK_ENABLED) {
					if (PropsValues.EHCACHE_CLUSTER_LINK_REPLICATION_ENABLED) {
						cacheListenerConfigurations.put(
							new CallbackConfiguration(
								ClusterLinkCallbackFactory.INSTANCE,
								properties),
							cacheListenerScope);
					}
					else {
						properties.put(
							EhcacheConstants.
								CACHE_EVENT_LISTENER_FACTORY_CLASS_NAME,
							cacheEventListenerFactoryConfiguration.
								getFullyQualifiedClassPath());

						cacheListenerConfigurations.put(
							new CallbackConfiguration(
								EhcacheCallbackFactory.INSTANCE, properties),
							cacheListenerScope);
					}
				}
			}
			else if (!usingDefault) {
				properties.put(
					EhcacheConstants.CACHE_EVENT_LISTENER_FACTORY_CLASS_NAME,
					cacheEventListenerFactoryConfiguration.
						getFullyQualifiedClassPath());

				cacheListenerConfigurations.put(
					new CallbackConfiguration(
						EhcacheCallbackFactory.INSTANCE, properties),
					cacheListenerScope);
			}
		}

		cacheEventListenerConfigurations.clear();

		CallbackConfiguration bootstrapLoaderConfiguration = null;

		BootstrapCacheLoaderFactoryConfiguration
			bootstrapCacheLoaderFactoryConfiguration =
				cacheConfiguration.
					getBootstrapCacheLoaderFactoryConfiguration();

		if (bootstrapCacheLoaderFactoryConfiguration != null) {
			Properties properties = _parseProperties(
				bootstrapCacheLoaderFactoryConfiguration.getProperties(),
				bootstrapCacheLoaderFactoryConfiguration.
					getPropertySeparator());

			if (clusterAware && PropsValues.CLUSTER_LINK_ENABLED) {
				if (PropsValues.EHCACHE_CLUSTER_LINK_REPLICATION_ENABLED) {
					bootstrapLoaderConfiguration = new CallbackConfiguration(
						ClusterLinkCallbackFactory.INSTANCE, properties);
				}
				else {
					properties.put(
						EhcacheConstants.
							BOOTSTRAP_CACHE_LOADER_FACTORY_CLASS_NAME,
						bootstrapCacheLoaderFactoryConfiguration.
							getFullyQualifiedClassPath());

					bootstrapLoaderConfiguration = new CallbackConfiguration(
						EhcacheCallbackFactory.INSTANCE, properties);
				}
			}

			cacheConfiguration.addBootstrapCacheLoaderFactory(null);
		}

		return new PortalCacheConfiguration(
			portalCacheName, cacheListenerConfigurations,
			bootstrapLoaderConfiguration);
	}

	private static Properties _parseProperties(
		String propertiesString, String propertySeparator) {

		Properties properties = new Properties();

		if (propertiesString == null) {
			return properties;
		}

		if (propertySeparator == null) {
			propertySeparator = StringPool.COMMA;
		}

		String propertyLines = propertiesString.trim();

		propertyLines = StringUtil.replace(
			propertyLines, propertySeparator, StringPool.NEW_LINE);

		try {
			properties.load(new UnsyncStringReader(propertyLines));
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		return properties;
	}

}