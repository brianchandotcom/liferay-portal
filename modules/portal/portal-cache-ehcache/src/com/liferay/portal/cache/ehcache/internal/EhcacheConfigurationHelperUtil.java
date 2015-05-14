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

import com.liferay.portal.cache.cluster.ClusterLinkCallbackFactory;
import com.liferay.portal.kernel.cache.CacheListenerScope;
import com.liferay.portal.kernel.cache.configuration.CallbackConfiguration;
import com.liferay.portal.kernel.cache.configuration.PortalCacheConfiguration;
import com.liferay.portal.kernel.cache.configuration.PortalCacheManagerConfiguration;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.HtmlImpl;

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
		<Configuration, PortalCacheManagerConfiguration> getConfiguration(
			URL configurationURL, boolean clusterAware, boolean usingDefault,
			Props props) {

		if (configurationURL == null) {
			throw new NullPointerException("Configuration path is null");
		}

		Configuration ehcacheConfiguration =
			ConfigurationFactory.parseConfiguration(configurationURL);

		boolean clusterEnabled = GetterUtil.getBoolean(
			props.get(PropsKeys.CLUSTER_LINK_ENABLED));

		boolean clusterLinkReplicationEnabled = GetterUtil.getBoolean(
			props.get(PropsKeys.EHCACHE_CLUSTER_LINK_REPLICATION_ENABLED));

		_handlePeerFactoryConfigurations(
			ehcacheConfiguration.
				getCacheManagerPeerProviderFactoryConfiguration(),
			clusterAware, clusterEnabled, clusterLinkReplicationEnabled, props);

		_handlePeerFactoryConfigurations(
			ehcacheConfiguration.
				getCacheManagerPeerListenerFactoryConfigurations(),
			clusterAware, clusterEnabled, clusterLinkReplicationEnabled, props);

		Set<CallbackConfiguration> cacheManagerListenerConfigurations =
			_getCacheManagerListenerConfigurations(ehcacheConfiguration, props);

		PortalCacheConfiguration defaultPortalCacheConfiguration =
			_parseCacheConfiguration(
				ehcacheConfiguration.getDefaultCacheConfiguration(),
				clusterAware, usingDefault, clusterEnabled,
				clusterLinkReplicationEnabled, props);

		Set<PortalCacheConfiguration> portalCacheConfigurations =
			new HashSet<>();

		Map<String, CacheConfiguration> cacheConfigurations =
			ehcacheConfiguration.getCacheConfigurations();

		for (Map.Entry<String, CacheConfiguration> entry :
				cacheConfigurations.entrySet()) {

			portalCacheConfigurations.add(
				_parseCacheConfiguration(
					entry.getValue(), clusterAware, usingDefault,
					clusterEnabled, clusterLinkReplicationEnabled, props));
		}

		PortalCacheManagerConfiguration portalCacheManagerConfiguration =
			new PortalCacheManagerConfiguration(
				cacheManagerListenerConfigurations,
				defaultPortalCacheConfiguration, portalCacheConfigurations);

		return new ObjectValuePair<>(
			ehcacheConfiguration, portalCacheManagerConfiguration);
	}

	private static Set<CallbackConfiguration>
		_getCacheManagerListenerConfigurations(
			Configuration ehcacheConfiguration, Props props) {

		FactoryConfiguration<?> factoryConfiguration =
			ehcacheConfiguration.
				getCacheManagerEventListenerFactoryConfiguration();

		if (factoryConfiguration == null) {
			return Collections.emptySet();
		}

		Properties properties = _parseProperties(
			factoryConfiguration.getProperties(),
			factoryConfiguration.getPropertySeparator(), props);

		properties.put(
			EhcacheConstants.CACHE_MANAGER_LISTENER_FACTORY_CLASS_NAME,
			_parseFactoryClassName(
				factoryConfiguration.getFullyQualifiedClassPath(), props));
		properties.put(
			EhcacheConstants.PORTAL_CACHE_MANAGER_NAME,
			ehcacheConfiguration.getName());

		return Collections.singleton(
			new CallbackConfiguration(
				EhcacheCallbackFactory.INSTANCE, properties));
	}

	private static String _getPropertiesString(
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
			sb.setIndex(sb.length() - 1);
		}

		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	private static void _handlePeerFactoryConfigurations(
		List<FactoryConfiguration> factoryConfigurations, boolean clusterAware,
		boolean clusterEnabled, boolean clusterLinkReplicationEnabled,
		Props props) {

		if (factoryConfigurations.isEmpty()) {
			return;
		}

		if (!clusterAware || !clusterEnabled || clusterLinkReplicationEnabled) {
			factoryConfigurations.clear();

			return;
		}

		for (FactoryConfiguration factoryConfiguration :
				factoryConfigurations) {

			factoryConfiguration.setClass(
				_parseFactoryClassName(
					factoryConfiguration.getFullyQualifiedClassPath(), props));

			String propertiesString = factoryConfiguration.getProperties();
			String propertySeparator =
				factoryConfiguration.getPropertySeparator();

			Properties properties = null;

			if (Validator.isNull(propertiesString)) {
				properties = new Properties();
			}
			else {
				properties = _parseProperties(
					propertiesString, propertySeparator, props);
			}

			properties.put(PropsKeys.CLUSTER_LINK_ENABLED, clusterEnabled);
			properties.put(
				PropsKeys.EHCACHE_CLUSTER_LINK_REPLICATION_ENABLED,
				clusterLinkReplicationEnabled);

			factoryConfiguration.setProperties(
				_getPropertiesString(properties, propertySeparator));
		}
	}

	private static PortalCacheConfiguration _parseCacheConfiguration(
		CacheConfiguration cacheConfiguration, boolean clusterAware,
		boolean usingDefault, boolean clusterEnabled,
		boolean clusterLinkReplicationEnabled, Props props) {

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

			String factoryClassName = _parseFactoryClassName(
				cacheEventListenerFactoryConfiguration.
					getFullyQualifiedClassPath(), props);

			Properties properties = _parseProperties(
				cacheEventListenerFactoryConfiguration.getProperties(),
				cacheEventListenerFactoryConfiguration. getPropertySeparator(),
				props);

			CacheListenerScope cacheListenerScope = _cacheListenerScopes.get(
				cacheEventListenerFactoryConfiguration.getListenFor());

			if (factoryClassName.equals(
					props.get(
						PropsKeys.EHCACHE_CACHE_EVENT_LISTENER_FACTORY))) {

				if (clusterAware && clusterEnabled) {
					if (clusterLinkReplicationEnabled) {
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
							factoryClassName);

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
					factoryClassName);

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
					getPropertySeparator(), props);

			if (clusterAware && clusterEnabled) {
				if (clusterLinkReplicationEnabled) {
					bootstrapLoaderConfiguration = new CallbackConfiguration(
						ClusterLinkCallbackFactory.INSTANCE, properties);
				}
				else {
					properties.put(
						EhcacheConstants.
							BOOTSTRAP_CACHE_LOADER_FACTORY_CLASS_NAME,
						_parseFactoryClassName(
							bootstrapCacheLoaderFactoryConfiguration.
								getFullyQualifiedClassPath(), props));

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

	private static String _parseFactoryClassName(
		String factoryClassName, Props props) {

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

	private static Properties _parseProperties(
		String propertiesString, String propertySeparator, Props props) {

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

			properties.put(valueParts[0], _htmlUtil.unescape(valueParts[1]));
		}

		return properties;
	}

	private static final String _PORTAL_PROPERTY_KEY = "portalPropertyKey";

	private static final Log _log = LogFactoryUtil.getLog(
		EhcacheConfigurationHelperUtil.class);

	private static final Map<NotificationScope, CacheListenerScope>
		_cacheListenerScopes = new EnumMap<>(NotificationScope.class);
	private static final HtmlImpl _htmlUtil = new HtmlImpl();

	static {
		_cacheListenerScopes.put(NotificationScope.ALL, CacheListenerScope.ALL);
		_cacheListenerScopes.put(
			NotificationScope.LOCAL, CacheListenerScope.LOCAL);
		_cacheListenerScopes.put(
			NotificationScope.REMOTE, CacheListenerScope.REMOTE);
	}

}