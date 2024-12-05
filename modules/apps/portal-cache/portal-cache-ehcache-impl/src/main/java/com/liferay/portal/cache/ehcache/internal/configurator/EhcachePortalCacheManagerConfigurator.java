/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.ehcache.internal.configurator;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.cache.PortalCacheReplicator;
import com.liferay.portal.cache.configuration.PortalCacheConfiguration;
import com.liferay.portal.cache.configuration.PortalCacheManagerConfiguration;
import com.liferay.portal.cache.ehcache.internal.EhcachePortalCacheConfiguration;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.net.URL;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ConfigurationFactory;
import net.sf.ehcache.config.FactoryConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration;

/**
 * @author Tina Tian
 */
public class EhcachePortalCacheManagerConfigurator {

	public EhcachePortalCacheManagerConfigurator(
		Properties replicatorProperties,
		String defaultReplicatorPropertiesString) {

		_replicatorProperties = replicatorProperties;
		_defaultReplicatorPropertiesString = defaultReplicatorPropertiesString;
	}

	public ObjectValuePair<Configuration, PortalCacheManagerConfiguration>
		getConfigurationObjectValuePair(
			String portalCacheManagerName, URL configurationURL) {

		if (configurationURL == null) {
			throw new NullPointerException("Configuration path is null");
		}

		Configuration configuration = ConfigurationFactory.parseConfiguration(
			configurationURL);

		configuration.setName(portalCacheManagerName);

		PortalCacheManagerConfiguration portalCacheManagerConfiguration =
			_parseConfiguration(configuration);

		_clearListenerConfigrations(configuration);

		_populateCacheReplicator(portalCacheManagerConfiguration);

		return new ObjectValuePair<>(
			configuration, portalCacheManagerConfiguration);
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
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return properties;
	}

	private void _clearListenerConfigrations(
		CacheConfiguration cacheConfiguration) {

		if (cacheConfiguration == null) {
			return;
		}

		List<?> factoryConfigurations =
			cacheConfiguration.getCacheEventListenerConfigurations();

		factoryConfigurations.clear();
	}

	private void _clearListenerConfigrations(Configuration configuration) {
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

		_clearListenerConfigrations(
			configuration.getDefaultCacheConfiguration());

		Map<String, CacheConfiguration> cacheConfigurations =
			configuration.getCacheConfigurations();

		for (CacheConfiguration cacheConfiguration :
				cacheConfigurations.values()) {

			_clearListenerConfigrations(cacheConfiguration);
		}
	}

	@SuppressWarnings("deprecation")
	private boolean _isRequireSerialization(
		CacheConfiguration cacheConfiguration) {

		if (cacheConfiguration.isDiskPersistent() ||
			cacheConfiguration.isOverflowToDisk() ||
			cacheConfiguration.isOverflowToOffHeap()) {

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

	private PortalCacheManagerConfiguration _parseConfiguration(
		Configuration configuration) {

		CacheConfiguration defaultCacheConfiguration =
			configuration.getDefaultCacheConfiguration();

		if (defaultCacheConfiguration == null) {
			defaultCacheConfiguration = new CacheConfiguration();
		}

		defaultCacheConfiguration.setName(
			PortalCacheConfiguration.PORTAL_CACHE_NAME_DEFAULT);

		PortalCacheConfiguration defaultPortalCacheConfiguration =
			new EhcachePortalCacheConfiguration(
				defaultCacheConfiguration.getName(), new HashSet<>(),
				_isRequireSerialization(defaultCacheConfiguration));

		Set<PortalCacheConfiguration> portalCacheConfigurations =
			new HashSet<>();

		Map<String, CacheConfiguration> cacheConfigurations =
			configuration.getCacheConfigurations();

		for (Map.Entry<String, CacheConfiguration> entry :
				cacheConfigurations.entrySet()) {

			CacheConfiguration cacheConfiguration = entry.getValue();

			portalCacheConfigurations.add(
				new EhcachePortalCacheConfiguration(
					cacheConfiguration.getName(), new HashSet<>(),
					_isRequireSerialization(cacheConfiguration)));
		}

		return new PortalCacheManagerConfiguration(
			defaultPortalCacheConfiguration, portalCacheConfigurations);
	}

	private void _populateCacheReplicator(
		PortalCacheConfiguration portalCacheConfiguration,
		String replicatorPropertiesString) {

		Properties replicatorProperties = parseProperties(
			replicatorPropertiesString, StringPool.COMMA);

		replicatorProperties.put(PortalCacheReplicator.REPLICATOR, true);

		Set<Properties> portalCacheListenerPropertiesSet =
			portalCacheConfiguration.getPortalCacheReplicatorPropertiesSet();

		portalCacheListenerPropertiesSet.add(replicatorProperties);
	}

	private void _populateCacheReplicator(
		PortalCacheManagerConfiguration portalCacheManagerConfiguration) {

		if (_replicatorProperties == null) {
			return;
		}

		Set<String> portalCacheNames = new HashSet<>(
			_replicatorProperties.stringPropertyNames());

		portalCacheNames.addAll(
			portalCacheManagerConfiguration.getPortalCacheNames());

		for (String portalCacheName : portalCacheNames) {
			_populateCacheReplicator(
				portalCacheManagerConfiguration.getPortalCacheConfiguration(
					portalCacheName),
				GetterUtil.getString(
					_replicatorProperties.getProperty(portalCacheName),
					_defaultReplicatorPropertiesString));
		}

		_populateCacheReplicator(
			portalCacheManagerConfiguration.
				getDefaultPortalCacheConfiguration(),
			_defaultReplicatorPropertiesString);
	}

	private final String _defaultReplicatorPropertiesString;
	private final Properties _replicatorProperties;

}