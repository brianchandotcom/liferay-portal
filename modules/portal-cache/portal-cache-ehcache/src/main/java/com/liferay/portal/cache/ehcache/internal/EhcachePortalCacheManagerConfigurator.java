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

import com.liferay.portal.cache.PortalCacheReplicator;
import com.liferay.portal.cache.configuration.PortalCacheConfiguration;
import com.liferay.portal.cache.configuration.PortalCacheManagerConfiguration;
import com.liferay.portal.cache.ehcache.EhcacheConstants;
import com.liferay.portal.kernel.cache.PortalCacheListenerScope;
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

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.CacheConfiguration.BootstrapCacheLoaderFactoryConfiguration;
import net.sf.ehcache.config.CacheConfiguration.CacheEventListenerFactoryConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ConfigurationFactory;
import net.sf.ehcache.config.FactoryConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration;
import net.sf.ehcache.event.NotificationScope;

/**
 * @author Tina Tian
 */
@Component(
	immediate = true, service = EhcachePortalCacheManagerConfigurator.class
)
public class EhcachePortalCacheManagerConfigurator {

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

		_handleCacheManagerPeerFactoryConfigurations(
			ehcacheConfiguration.
				getCacheManagerPeerProviderFactoryConfiguration(),
			clusterAware);

		_handleCacheManagerPeerFactoryConfigurations(
			ehcacheConfiguration.
				getCacheManagerPeerListenerFactoryConfigurations(),
			clusterAware);

		Set<Properties> cacheManagerListenerPropertiesSet =
			_getCacheManagerListenerPropertiesSet(ehcacheConfiguration);

		PortalCacheConfiguration defaultPortalCacheConfiguration =
			_getPortalCacheConfiguration(
				ehcacheConfiguration.getDefaultCacheConfiguration(),
				clusterAware, usingDefault);

		Set<PortalCacheConfiguration> portalCacheConfigurations =
			new HashSet<>();

		Map<String, CacheConfiguration> cacheConfigurations =
			ehcacheConfiguration.getCacheConfigurations();

		for (Map.Entry<String, CacheConfiguration> entry :
				cacheConfigurations.entrySet()) {

			portalCacheConfigurations.add(
				_getPortalCacheConfiguration(
					entry.getValue(), clusterAware, usingDefault));
		}

		PortalCacheManagerConfiguration portalCacheManagerConfiguration =
			new PortalCacheManagerConfiguration(
				cacheManagerListenerPropertiesSet,
				defaultPortalCacheConfiguration, portalCacheConfigurations);

		return new ObjectValuePair<>(
			ehcacheConfiguration, portalCacheManagerConfiguration);
	}

	@Activate
	protected void activate() {
		_clusterEnabled = GetterUtil.getBoolean(
			props.get(PropsKeys.CLUSTER_LINK_ENABLED));
		_clusterLinkReplicationEnabled = GetterUtil.getBoolean(
			props.get(PropsKeys.EHCACHE_CLUSTER_LINK_REPLICATION_ENABLED));
	}

	@Reference(unbind = "-")
	protected void setProps(Props props) {
		this.props = props;
	}

	protected volatile Props props;

	private Set<Properties>
		_getCacheManagerListenerPropertiesSet(
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
			_parseFactoryClassName(
				factoryConfiguration.getFullyQualifiedClassPath()));

		factoryConfiguration.setClass(null);

		return Collections.singleton(properties);
	}

	private String _getPropertiesString(
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
	private void _handleCacheManagerPeerFactoryConfigurations(
		List<FactoryConfiguration> factoryConfigurations,
		boolean clusterAware) {

		if (factoryConfigurations.isEmpty()) {
			return;
		}

		if (!clusterAware || !_clusterEnabled ||
				_clusterLinkReplicationEnabled) {

			factoryConfigurations.clear();

			return;
		}

		for (FactoryConfiguration factoryConfiguration :
				factoryConfigurations) {

			Properties properties = null;

			factoryConfiguration.setClass(
				_parseFactoryClassName(
					factoryConfiguration.getFullyQualifiedClassPath()));

			String propertiesString = factoryConfiguration.getProperties();

			if (Validator.isNull(propertiesString)) {
				properties = new Properties();
			}
			else {
				properties = _parseProperties(
					propertiesString,
					factoryConfiguration.getPropertySeparator());
			}

			properties.put(PropsKeys.CLUSTER_LINK_ENABLED, _clusterEnabled);
			properties.put(
				PropsKeys.EHCACHE_CLUSTER_LINK_REPLICATION_ENABLED,
				_clusterLinkReplicationEnabled);

			factoryConfiguration.setProperties(
				_getPropertiesString(
					properties, factoryConfiguration.getPropertySeparator()));
		}
	}

	@SuppressWarnings("deprecation")
	private boolean _isRequireSerialization(
		CacheConfiguration cacheConfiguration, boolean clusterAware) {

		if (clusterAware && _clusterEnabled) {
			return true;
		}

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

	private PortalCacheConfiguration _getPortalCacheConfiguration(
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

		Set<Properties> portalCacheListenerPropertiesSet =
			_parseCacheEventListenerFactoryConfiguration(
				cacheConfiguration, clusterAware, usingDefault);

		Properties portalCacheBootstrapLoaderProperties =
			_parsePortalCacheBootstrapLoaderProperties(
				cacheConfiguration, clusterAware);

		boolean requireSerialization = _isRequireSerialization(
			cacheConfiguration, clusterAware);

		return new EhcachePortalCacheConfiguration(
			portalCacheName, portalCacheListenerPropertiesSet,
			portalCacheBootstrapLoaderProperties, requireSerialization);
	}

	private Set<Properties> _parseCacheEventListenerFactoryConfiguration(
		CacheConfiguration cacheConfiguration, boolean clusterAware,
		boolean usingDefault) {

		Set<Properties> portalCacheListenerPropertiesSet = new HashSet<>();

		List<CacheEventListenerFactoryConfiguration>
			cacheEventListenerConfigurations =
				cacheConfiguration.getCacheEventListenerConfigurations();

		for (CacheEventListenerFactoryConfiguration
				cacheEventListenerFactoryConfiguration :
					cacheEventListenerConfigurations) {

			String factoryClassName = _parseFactoryClassName(
				cacheEventListenerFactoryConfiguration.
					getFullyQualifiedClassPath());

			Properties properties = _parseProperties(
				cacheEventListenerFactoryConfiguration.getProperties(),
				cacheEventListenerFactoryConfiguration. getPropertySeparator());

			PortalCacheListenerScope portalCacheListenerScope =
				_portalCacheListenerScopes.get(
					cacheEventListenerFactoryConfiguration.getListenFor());

			_processCacheEventListenerFactoryProperties(
				clusterAware, factoryClassName,
				portalCacheListenerPropertiesSet, portalCacheListenerScope,
				properties, usingDefault);
		}

		cacheEventListenerConfigurations.clear();

		return portalCacheListenerPropertiesSet;
	}

	private Properties _parsePortalCacheBootstrapLoaderProperties(
		CacheConfiguration cacheConfiguration, boolean clusterAware) {

		Properties portalCacheBootstrapLoaderProperties = null;

		BootstrapCacheLoaderFactoryConfiguration
			bootstrapCacheLoaderFactoryConfiguration =
				cacheConfiguration.
					getBootstrapCacheLoaderFactoryConfiguration();

		if (bootstrapCacheLoaderFactoryConfiguration != null) {
			portalCacheBootstrapLoaderProperties = _parseProperties(
				bootstrapCacheLoaderFactoryConfiguration.getProperties(),
				bootstrapCacheLoaderFactoryConfiguration.
					getPropertySeparator());

			if (clusterAware && _clusterEnabled) {
				if (!_clusterLinkReplicationEnabled) {
					portalCacheBootstrapLoaderProperties.put(
						EhcacheConstants.
							BOOTSTRAP_CACHE_LOADER_FACTORY_CLASS_NAME,
						_parseFactoryClassName(
							bootstrapCacheLoaderFactoryConfiguration.
								getFullyQualifiedClassPath()));
				}
			}

			cacheConfiguration.addBootstrapCacheLoaderFactory(null);
		}

		return portalCacheBootstrapLoaderProperties;
	}

	private String _parseFactoryClassName(String factoryClassName) {
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

	private Properties _parseProperties(
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

	private void _processCacheEventListenerFactoryProperties(
		boolean clusterAware, String factoryClassName,
		Set<Properties> portalCacheListenerPropertiesSet,
		PortalCacheListenerScope portalCacheListenerScope,
		Properties properties, boolean usingDefault) {

		if (factoryClassName.equals(
				props.get(
					PropsKeys.EHCACHE_CACHE_EVENT_LISTENER_FACTORY))) {

			if (clusterAware && _clusterEnabled) {
				if (!_clusterLinkReplicationEnabled) {
					properties.put(
						EhcacheConstants.
							CACHE_EVENT_LISTENER_FACTORY_CLASS_NAME,
						factoryClassName);
				}

				properties.put(
					PortalCacheConfiguration.PORTAL_CACHE_LISTENER_SCOPE,
					portalCacheListenerScope);
				properties.put(PortalCacheReplicator.REPLICATOR, true);

				portalCacheListenerPropertiesSet.add(properties);
			}
		}
		else if (!usingDefault) {
			properties.put(
				EhcacheConstants.CACHE_EVENT_LISTENER_FACTORY_CLASS_NAME,
				factoryClassName);
			properties.put(
				PortalCacheConfiguration.PORTAL_CACHE_LISTENER_SCOPE,
				portalCacheListenerScope);

			portalCacheListenerPropertiesSet.add(properties);
		}
	}

	private String _unescape(String text) {
		return StringUtil.replace(text, "&", ";", _unescapeMap);
	}

	private static final String _PORTAL_PROPERTY_KEY = "portalPropertyKey";

	private static final Log _log = LogFactoryUtil.getLog(
		EhcachePortalCacheManagerConfigurator.class);

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

	private boolean _clusterEnabled;
	private boolean _clusterLinkReplicationEnabled;

}