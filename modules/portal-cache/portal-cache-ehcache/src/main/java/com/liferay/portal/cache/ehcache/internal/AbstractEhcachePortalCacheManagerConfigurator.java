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

package com.liferay.portal.cache.ehcache.internal;

import com.liferay.portal.cache.configuration.PortalCacheConfiguration;
import com.liferay.portal.cache.configuration.PortalCacheManagerConfiguration;
import com.liferay.portal.cache.ehcache.EhcacheConstants;
import com.liferay.portal.kernel.cache.PortalCacheListenerScope;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.net.URL;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
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
public abstract class AbstractEhcachePortalCacheManagerConfigurator {

	public ObjectValuePair
		<Configuration, PortalCacheManagerConfiguration>
			getConfigurationObjectValuePair(
				String portalCacheManagerName, URL configurationURL,
				boolean clusterAware, boolean usingDefault) {

		if (configurationURL == null) {
			throw new NullPointerException("Configuration path is null");
		}

		Configuration ehcacheConfiguration =
			ConfigurationFactory.parseConfiguration(configurationURL);

		ehcacheConfiguration.setName(portalCacheManagerName);

		handleCacheManagerPeerFactoryConfigurations(
			ehcacheConfiguration.
				getCacheManagerPeerProviderFactoryConfiguration());

		handleCacheManagerPeerFactoryConfigurations(
			ehcacheConfiguration.
				getCacheManagerPeerListenerFactoryConfigurations());

		Set<Properties> cacheManagerListenerPropertiesSet =
			getCacheManagerListenerPropertiesSet(ehcacheConfiguration);

		PortalCacheConfiguration defaultPortalCacheConfiguration =
			getPortalCacheConfiguration(
				ehcacheConfiguration.getDefaultCacheConfiguration(),
				usingDefault);

		Set<PortalCacheConfiguration> portalCacheConfigurations =
			new HashSet<>();

		Map<String, CacheConfiguration> cacheConfigurations =
			ehcacheConfiguration.getCacheConfigurations();

		for (Map.Entry<String, CacheConfiguration> entry :
				cacheConfigurations.entrySet()) {

			portalCacheConfigurations.add(
				getPortalCacheConfiguration(entry.getValue(), usingDefault));
		}

		PortalCacheManagerConfiguration portalCacheManagerConfiguration =
			new PortalCacheManagerConfiguration(
				cacheManagerListenerPropertiesSet,
				defaultPortalCacheConfiguration, portalCacheConfigurations);

		return new ObjectValuePair<>(
			ehcacheConfiguration, portalCacheManagerConfiguration);
	}

	protected Set<Properties>
		getCacheManagerListenerPropertiesSet(
			Configuration ehcacheConfiguration) {

		FactoryConfiguration<?> factoryConfiguration =
			ehcacheConfiguration.
				getCacheManagerEventListenerFactoryConfiguration();

		if (factoryConfiguration == null) {
			return Collections.emptySet();
		}

		Properties properties = parseProperties(
			factoryConfiguration.getProperties(),
			factoryConfiguration.getPropertySeparator());

		properties.put(
			EhcacheConstants.CACHE_MANAGER_LISTENER_FACTORY_CLASS_NAME,
			parseFactoryClassName(
				factoryConfiguration.getFullyQualifiedClassPath()));

		factoryConfiguration.setClass(null);

		return Collections.singleton(properties);
	}

	protected PortalCacheConfiguration getPortalCacheConfiguration(
		CacheConfiguration cacheConfiguration, boolean usingDefault) {

		if (cacheConfiguration == null) {
			return null;
		}

		String portalCacheName = cacheConfiguration.getName();

		if (portalCacheName == null) {
			portalCacheName =
				PortalCacheConfiguration.DEFAULT_PORTAL_CACHE_NAME;
		}

		Set<Properties> portalCacheListenerPropertiesSet =
			parseCacheEventListenerFactoryConfiguration(
				cacheConfiguration, usingDefault);

		Properties portalCacheBootstrapLoaderProperties =
			parsePortalCacheBootstrapLoaderProperties(cacheConfiguration);

		boolean requireSerialization = isRequireSerialization(
			cacheConfiguration);

		return new EhcachePortalCacheConfiguration(
			portalCacheName, portalCacheListenerPropertiesSet,
			portalCacheBootstrapLoaderProperties, requireSerialization);
	}

	protected String getPropertiesString(
		Properties properties, String propertySeparator) {

		if (propertySeparator == null) {
			propertySeparator = StringPool.COMMA;
		}

		StringBundler sb = new StringBundler(properties.size() * 4);

		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			sb.append(entry.getKey());
			sb.append(StringPool.EQUAL);
			sb.append(entry.getValue());
			sb.append(propertySeparator);
		}

		if (!properties.isEmpty()) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	protected abstract void handleCacheManagerPeerFactoryConfigurations(
		List<FactoryConfiguration> factoryConfigurations);

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

	protected Set<Properties> parseCacheEventListenerFactoryConfiguration(
		CacheConfiguration cacheConfiguration, boolean usingDefault) {

		Set<Properties> portalCacheListenerPropertiesSet = new HashSet<>();

		List<CacheEventListenerFactoryConfiguration>
			cacheEventListenerConfigurations =
				cacheConfiguration.getCacheEventListenerConfigurations();

		for (CacheEventListenerFactoryConfiguration
				cacheEventListenerFactoryConfiguration :
					cacheEventListenerConfigurations) {

			String factoryClassName = parseFactoryClassName(
				cacheEventListenerFactoryConfiguration.
					getFullyQualifiedClassPath());

			Properties properties = parseProperties(
				cacheEventListenerFactoryConfiguration.getProperties(),
				cacheEventListenerFactoryConfiguration. getPropertySeparator());

			PortalCacheListenerScope portalCacheListenerScope =
				_portalCacheListenerScopes.get(
					cacheEventListenerFactoryConfiguration.getListenFor());

			processCacheEventListenerFactoryProperties(
				factoryClassName, portalCacheListenerPropertiesSet,
				portalCacheListenerScope, properties, usingDefault);
		}

		cacheEventListenerConfigurations.clear();

		return portalCacheListenerPropertiesSet;
	}

	protected String parseFactoryClassName(String factoryClassName) {
		if (factoryClassName.indexOf(CharPool.EQUAL) == -1) {
			return factoryClassName;
		}

		String[] factoryClassNameParts = StringUtil.split(
			factoryClassName, CharPool.EQUAL);

		if (factoryClassNameParts[0].equals(_PORTAL_PROPERTY_KEY)) {
			return props.get(factoryClassNameParts[1]);
		}

		if (_log.isWarnEnabled()) {
			_log.warn("Unable to parse factory class name " + factoryClassName);
		}

		return factoryClassName;
	}

	protected abstract Properties parsePortalCacheBootstrapLoaderProperties(
		CacheConfiguration cacheConfiguration);

	protected Properties parseProperties(
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

		String portalPropertyKey = (String)properties.remove(
			_PORTAL_PROPERTY_KEY);

		if (Validator.isNull(portalPropertyKey)) {
			return properties;
		}

		String[] values = props.getArray(portalPropertyKey);

		if (_log.isInfoEnabled()) {
			_log.info(
				"portalPropertyKey " + portalPropertyKey + " has value " +
					Arrays.toString(values));
		}

		for (String value : values) {
			String[] valueParts = StringUtil.split(value, CharPool.EQUAL);

			if (valueParts.length != 2) {
				if (_log.isWarnEnabled()) {
					_log.warn("Ignore malformed value " + value);
				}

				continue;
			}

			properties.put(valueParts[0], _unescape(valueParts[1]));
		}

		return properties;
	}

	protected void processCacheEventListenerFactoryProperties(
		String factoryClassName,
		Set<Properties> portalCacheListenerPropertiesSet,
		PortalCacheListenerScope portalCacheListenerScope,
		Properties properties, boolean usingDefault) {

		if (!usingDefault) {
			properties.put(
				EhcacheConstants.CACHE_EVENT_LISTENER_FACTORY_CLASS_NAME,
				factoryClassName);
			properties.put(
				PortalCacheConfiguration.PORTAL_CACHE_LISTENER_SCOPE,
				portalCacheListenerScope);

			portalCacheListenerPropertiesSet.add(properties);
		}
	}

	protected volatile Props props;

	private String _unescape(String text) {
		return StringUtil.replace(text, "&", ";", _unescapeMap);
	}

	private static final String _PORTAL_PROPERTY_KEY = "portalPropertyKey";

	private static final Log _log = LogFactoryUtil.getLog(
		AbstractEhcachePortalCacheManagerConfigurator.class);

	private static final Map<NotificationScope, PortalCacheListenerScope>
		_portalCacheListenerScopes = new EnumMap<>(NotificationScope.class);
	private static final Map<String, String> _unescapeMap = new HashMap<>();

	static {
		_portalCacheListenerScopes.put(
			NotificationScope.ALL, PortalCacheListenerScope.ALL);
		_portalCacheListenerScopes.put(
			NotificationScope.LOCAL, PortalCacheListenerScope.LOCAL);
		_portalCacheListenerScopes.put(
			NotificationScope.REMOTE, PortalCacheListenerScope.REMOTE);

		_unescapeMap.put("amp", "&");
		_unescapeMap.put("gt", ">");
		_unescapeMap.put("lt", "<");
		_unescapeMap.put("rsquo", "\u2019");
		_unescapeMap.put("#034", "\"");
		_unescapeMap.put("#039", "'");
		_unescapeMap.put("#040", "(");
		_unescapeMap.put("#041", ")");
		_unescapeMap.put("#044", ",");
		_unescapeMap.put("#035", "#");
		_unescapeMap.put("#037", "%");
		_unescapeMap.put("#059", ";");
		_unescapeMap.put("#061", "=");
		_unescapeMap.put("#043", "+");
		_unescapeMap.put("#045", "-");
	}

}