/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.ehcache.internal.configurator;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.cache.PortalCacheReplicator;
import com.liferay.portal.cache.configuration.PortalCacheConfiguration;
import com.liferay.portal.cache.configuration.PortalCacheManagerConfiguration;
import com.liferay.portal.cache.ehcache.internal.EhcacheExpiryPolicy;
import com.liferay.portal.cache.ehcache.internal.configuration.EhcachePortalCacheConfiguration;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.net.URL;

import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.Configuration;
import org.ehcache.config.FluentConfigurationBuilder;
import org.ehcache.config.ResourcePools;
import org.ehcache.config.ResourceType;
import org.ehcache.expiry.ExpiryPolicy;
import org.ehcache.xml.XmlConfiguration;

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
			URL configurationURL, ClassLoader classLoader) {

		return getConfigurationObjectValuePair(
			configurationURL, classLoader, false);
	}

	public ObjectValuePair<Configuration, PortalCacheManagerConfiguration>
		getConfigurationObjectValuePair(
			URL configurationURL, ClassLoader classLoader,
			boolean reconfigure) {

		if (configurationURL == null) {
			throw new NullPointerException("Configuration path is null");
		}

		XmlConfiguration xmlConfiguration = new XmlConfiguration(
			configurationURL, classLoader);

		PortalCacheManagerConfiguration portalCacheManagerConfiguration =
			_parseConfiguration(xmlConfiguration);

		_populateCacheReplicator(portalCacheManagerConfiguration, reconfigure);

		return new ObjectValuePair<>(
			_replaceExpiryPolicy(xmlConfiguration),
			portalCacheManagerConfiguration);
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

	private boolean _isRequireSerialization(
		CacheConfiguration<?, ?> cacheConfiguration) {

		ResourcePools resourcePools = cacheConfiguration.getResourcePools();

		for (ResourceType<?> resourceType :
				resourcePools.getResourceTypeSet()) {

			if (resourceType.requiresSerialization()) {
				return true;
			}
		}

		return false;
	}

	private PortalCacheManagerConfiguration _parseConfiguration(
		XmlConfiguration xmlConfiguration) {

		Set<PortalCacheConfiguration> portalCacheConfigurations =
			new HashSet<>();

		Map<String, CacheConfiguration<?, ?>> cacheConfigurations =
			xmlConfiguration.getCacheConfigurations();

		PortalCacheConfiguration defaultPortalCacheConfiguration = null;

		for (Map.Entry<String, CacheConfiguration<?, ?>> entry :
				cacheConfigurations.entrySet()) {

			String portalCacheName = entry.getKey();

			CacheConfiguration<?, ?> cacheConfiguration = entry.getValue();

			EhcachePortalCacheConfiguration ehcachePortalCacheConfiguration =
				new EhcachePortalCacheConfiguration(
					portalCacheName, new HashSet<>(),
					cacheConfiguration.getKeyType(),
					cacheConfiguration.getValueType(),
					_isRequireSerialization(cacheConfiguration));

			if (portalCacheName.equals(
					PortalCacheConfiguration.PORTAL_CACHE_NAME_DEFAULT)) {

				defaultPortalCacheConfiguration =
					ehcachePortalCacheConfiguration;
			}
			else {
				portalCacheConfigurations.add(ehcachePortalCacheConfiguration);
			}
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
		PortalCacheManagerConfiguration portalCacheManagerConfiguration,
		boolean reconfigure) {

		if (_replicatorProperties == null) {
			return;
		}

		Set<String> portalCacheNames = new HashSet<>(
			portalCacheManagerConfiguration.getPortalCacheNames());

		if (!reconfigure) {
			portalCacheNames.addAll(
				_replicatorProperties.stringPropertyNames());
		}

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

		if (!reconfigure) {
			_populateCacheReplicator(
				portalCacheManagerConfiguration.
					getDefaultPortalCacheConfiguration(),
				_defaultReplicatorPropertiesString);
		}
	}

	private Configuration _replaceExpiryPolicy(
		XmlConfiguration xmlConfiguration) {

		FluentConfigurationBuilder<?> fluentConfigurationBuilder =
			xmlConfiguration.derive();

		Map<String, CacheConfiguration<?, ?>> cacheConfigurations =
			xmlConfiguration.getCacheConfigurations();

		for (Map.Entry<String, CacheConfiguration<?, ?>> entry :
				cacheConfigurations.entrySet()) {

			CacheConfiguration<?, ?> cacheConfiguration = entry.getValue();

			fluentConfigurationBuilder.updateCache(
				entry.getKey(),
				fluentCacheConfigurationBuilder ->
					fluentCacheConfigurationBuilder.withExpiry(
						new EhcacheExpiryPolicy(
							(ExpiryPolicy<Object, Object>)
								cacheConfiguration.getExpiryPolicy())));
		}

		return fluentConfigurationBuilder.build();
	}

	private final String _defaultReplicatorPropertiesString;
	private final Properties _replicatorProperties;

}