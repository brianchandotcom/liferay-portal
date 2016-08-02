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

package com.liferay.portal.cache.ehcache.internal.configurator;

import com.liferay.portal.cache.configuration.PortalCacheConfiguration;
import com.liferay.portal.cache.configuration.PortalCacheManagerConfiguration;
import com.liferay.portal.cache.ehcache.EhcacheConstants;
import com.liferay.portal.cache.ehcache.internal.EhcachePortalCacheConfiguration;
import com.liferay.portal.kernel.cache.PortalCacheListenerScope;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.net.URL;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.CacheConfiguration.CacheEventListenerFactoryConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ConfigurationFactory;
import net.sf.ehcache.config.FactoryConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration;
import net.sf.ehcache.event.NotificationScope;

/**
 * @author Tina Tian
 */
public abstract class BaseEhcachePortalCacheManagerConfigurator {

	public ObjectValuePair
		<Configuration, PortalCacheManagerConfiguration>
			getConfigurationObjectValuePair(
				String portalCacheManagerName, URL configurationURL,
				boolean usingDefault) {

		if (configurationURL == null) {
			throw new NullPointerException("Configuration path is null");
		}

		Configuration configuration = ConfigurationFactory.parseConfiguration(
			configurationURL);

		configuration.setName(portalCacheManagerName);

		PortalCacheManagerConfiguration portalCacheManagerConfiguration =
			parseListenerConfigurations(configuration, usingDefault);

		clearListenerConfigrations(configuration);

		return new ObjectValuePair<>(
			configuration, portalCacheManagerConfiguration);
	}

	protected void clearListenerConfigrations(
		CacheConfiguration cacheConfiguration) {

		if (cacheConfiguration == null) {
			return;
		}

		cacheConfiguration.bootstrapCacheLoaderFactory(null);

		List<?> factoryConfigurations =
			cacheConfiguration.getCacheEventListenerConfigurations();

		factoryConfigurations.clear();
	}

	protected void clearListenerConfigrations(Configuration configuration) {
		List<?> listenerFactoryConfigurations =
			configuration.getCacheManagerPeerListenerFactoryConfigurations();

		listenerFactoryConfigurations.clear();

		List<?> providerFactoryConfigurations =
			configuration.getCacheManagerPeerProviderFactoryConfiguration();

		providerFactoryConfigurations.clear();

		FactoryConfiguration<?> factoryConfiguration =
			configuration.getCacheManagerEventListenerFactoryConfiguration();

		if (factoryConfiguration != null) {
			factoryConfiguration.setClass(null);
		}

		clearListenerConfigrations(
			configuration.getDefaultCacheConfiguration());

		Map<String, CacheConfiguration> cacheConfigurations =
			configuration.getCacheConfigurations();

		for (CacheConfiguration cacheConfiguration :
				cacheConfigurations.values()) {

			clearListenerConfigrations(cacheConfiguration);
		}
	}

	@SuppressWarnings("deprecation")
	protected boolean isRequireSerialization(
		CacheConfiguration cacheConfiguration) {

		if (cacheConfiguration.isOverflowToDisk() ||
			cacheConfiguration.isOverflowToOffHeap() ||
			cacheConfiguration.isDiskPersistent()) {

			return true;
		}

		PersistenceConfiguration persistenceConfiguration =
			cacheConfiguration.getPersistenceConfiguration();

		if (persistenceConfiguration != null) {
			PersistenceConfiguration.Strategy strategy =
				persistenceConfiguration.getStrategy();

			if (!strategy.equals(PersistenceConfiguration.Strategy.NONE)) {
				return true;
			}
		}

		return false;
	}

	protected Set<Properties> parseCacheEventListenerConfigurations(
		List<CacheEventListenerFactoryConfiguration>
			cacheEventListenerConfigurations,
		boolean usingDefault) {

		if (usingDefault) {
			return Collections.emptySet();
		}

		Set<Properties> portalCacheListenerPropertiesSet = new HashSet<>();

		for (CacheEventListenerFactoryConfiguration
				cacheEventListenerFactoryConfiguration :
					cacheEventListenerConfigurations) {

			Properties properties = parseProperties(
				cacheEventListenerFactoryConfiguration.getProperties(),
				cacheEventListenerFactoryConfiguration.getPropertySeparator());

			String factoryClassName =
				cacheEventListenerFactoryConfiguration.
					getFullyQualifiedClassPath();

			properties.put(
				EhcacheConstants.CACHE_EVENT_LISTENER_FACTORY_CLASS_NAME,
				factoryClassName);

			PortalCacheListenerScope portalCacheListenerScope =
				_portalCacheListenerScopes.get(
					cacheEventListenerFactoryConfiguration.getListenFor());

			properties.put(
				PortalCacheConfiguration.PORTAL_CACHE_LISTENER_SCOPE,
				portalCacheListenerScope);

			portalCacheListenerPropertiesSet.add(properties);
		}

		return portalCacheListenerPropertiesSet;
	}

	protected PortalCacheConfiguration parseCacheListenerConfigurations(
		CacheConfiguration cacheConfiguration, boolean usingDefault) {

		Set<Properties> portalCacheListenerPropertiesSet =
			parseCacheEventListenerConfigurations(
				(List<CacheEventListenerFactoryConfiguration>)
					cacheConfiguration.getCacheEventListenerConfigurations(),
				usingDefault);

		boolean requireSerialization = isRequireSerialization(
			cacheConfiguration);

		return new EhcachePortalCacheConfiguration(
			cacheConfiguration.getName(), portalCacheListenerPropertiesSet,
			null, requireSerialization);
	}

	protected Set<Properties> parseCacheManagerEventListenerConfigurations(
		FactoryConfiguration<?> factoryConfiguration) {

		if (factoryConfiguration == null) {
			return Collections.emptySet();
		}

		Properties properties = parseProperties(
			factoryConfiguration.getProperties(),
			factoryConfiguration.getPropertySeparator());

		properties.put(
			EhcacheConstants.CACHE_MANAGER_LISTENER_FACTORY_CLASS_NAME,
			factoryConfiguration.getFullyQualifiedClassPath());

		return Collections.singleton(properties);
	}

	protected PortalCacheManagerConfiguration parseListenerConfigurations(
		Configuration configuration, boolean usingDefault) {

		Set<Properties> cacheManagerListenerPropertiesSet =
			parseCacheManagerEventListenerConfigurations(
				configuration.
					getCacheManagerEventListenerFactoryConfiguration());

		CacheConfiguration defaultCacheConfiguration =
			configuration.getDefaultCacheConfiguration();

		if (defaultCacheConfiguration == null) {
			defaultCacheConfiguration = new CacheConfiguration();
		}

		defaultCacheConfiguration.setName(
			PortalCacheConfiguration.DEFAULT_PORTAL_CACHE_NAME);

		PortalCacheConfiguration defaultPortalCacheConfiguration =
			parseCacheListenerConfigurations(
				defaultCacheConfiguration, usingDefault);

		Set<PortalCacheConfiguration> portalCacheConfigurations =
			new HashSet<>();

		Map<String, CacheConfiguration> cacheConfigurations =
			configuration.getCacheConfigurations();

		for (Map.Entry<String, CacheConfiguration> entry :
				cacheConfigurations.entrySet()) {

			portalCacheConfigurations.add(
				parseCacheListenerConfigurations(
					entry.getValue(), usingDefault));
		}

		return new PortalCacheManagerConfiguration(
			cacheManagerListenerPropertiesSet, defaultPortalCacheConfiguration,
			portalCacheConfigurations);
	}

	protected Properties parseProperties(
		String propertiesString, String propertySeparator) {

		Properties properties = new Properties();

		if (propertiesString == null) {
			return properties;
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

	protected Props props;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseEhcachePortalCacheManagerConfigurator.class);

	private static final Map<NotificationScope, PortalCacheListenerScope>
		_portalCacheListenerScopes = new EnumMap<>(NotificationScope.class);

	static {
		_portalCacheListenerScopes.put(
			NotificationScope.ALL, PortalCacheListenerScope.ALL);
		_portalCacheListenerScopes.put(
			NotificationScope.LOCAL, PortalCacheListenerScope.LOCAL);
		_portalCacheListenerScopes.put(
			NotificationScope.REMOTE, PortalCacheListenerScope.REMOTE);
	}

}