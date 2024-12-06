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
import com.liferay.portal.cache.ehcache.internal.EhcachePortalCacheConfiguration;
import com.liferay.portal.cache.ehcache.internal.configuration.EhcachePortalCacheManagerConfiguration;
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

	public ObjectValuePair
		<Configuration, EhcachePortalCacheManagerConfiguration>
			getConfigurationObjectValuePair(
				URL configurationURL, ClassLoader classLoader) {

		if (configurationURL == null) {
			throw new NullPointerException("Configuration path is null");
		}

		XmlConfiguration xmlConfiguration = new XmlConfiguration(
			configurationURL, classLoader);

		EhcachePortalCacheManagerConfiguration
			ehcachePortalCacheManagerConfiguration = _parseConfiguration(
				xmlConfiguration);

		_populateCacheReplicator(ehcachePortalCacheManagerConfiguration);

		return new ObjectValuePair<>(
			_replaceExpiryPolicy(xmlConfiguration),
			ehcachePortalCacheManagerConfiguration);
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

	private EhcachePortalCacheManagerConfiguration _parseConfiguration(
		XmlConfiguration xmlConfiguration) {

		Set<PortalCacheConfiguration> portalCacheConfigurations =
			new HashSet<>();

		Map<String, CacheConfiguration<?, ?>> cacheConfigurations =
			xmlConfiguration.getCacheConfigurations();

		for (Map.Entry<String, CacheConfiguration<?, ?>> entry :
				cacheConfigurations.entrySet()) {

			String portalCacheName = entry.getKey();

			if (portalCacheName.equals(
					PortalCacheConfiguration.PORTAL_CACHE_NAME_DEFAULT)) {

				continue;
			}

			CacheConfiguration<?, ?> cacheConfiguration = entry.getValue();

			portalCacheConfigurations.add(
				new EhcachePortalCacheConfiguration(
					portalCacheName, new HashSet<>(),
					cacheConfiguration.getKeyType(),
					cacheConfiguration.getValueType(),
					_isRequireSerialization(cacheConfiguration)));
		}

		CacheConfiguration<?, ?> defaultCacheConfiguration =
			cacheConfigurations.get(
				PortalCacheConfiguration.PORTAL_CACHE_NAME_DEFAULT);

		PortalCacheConfiguration defaultPortalCacheConfiguration = null;

		if (defaultCacheConfiguration != null) {
			defaultPortalCacheConfiguration =
				new EhcachePortalCacheConfiguration(
					PortalCacheConfiguration.PORTAL_CACHE_NAME_DEFAULT,
					new HashSet<>(), defaultCacheConfiguration.getKeyType(),
					defaultCacheConfiguration.getValueType(),
					_isRequireSerialization(defaultCacheConfiguration));
		}

		return new EhcachePortalCacheManagerConfiguration(
			defaultCacheConfiguration, defaultPortalCacheConfiguration,
			portalCacheConfigurations);
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

	@SuppressWarnings("unchecked")
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