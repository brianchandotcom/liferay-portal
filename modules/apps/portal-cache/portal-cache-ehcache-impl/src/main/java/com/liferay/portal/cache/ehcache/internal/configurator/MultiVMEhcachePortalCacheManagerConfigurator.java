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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.cache.PortalCacheReplicator;
import com.liferay.portal.cache.configuration.PortalCacheConfiguration;
import com.liferay.portal.cache.configuration.PortalCacheManagerConfiguration;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dante Wang
 */
@Component(
	immediate = true,
	service = MultiVMEhcachePortalCacheManagerConfigurator.class
)
public class MultiVMEhcachePortalCacheManagerConfigurator
	extends BaseEhcachePortalCacheManagerConfigurator {

	@Activate
	protected void activate() {
		_bootstrapLoaderEnabled = GetterUtil.getBoolean(
			props.get(PropsKeys.EHCACHE_BOOTSTRAP_CACHE_LOADER_ENABLED));
		_bootstrapLoaderProperties = props.getProperties(
			PropsKeys.EHCACHE_BOOTSTRAP_CACHE_LOADER_PROPERTIES +
				StringPool.PERIOD,
			true);
		clusterEnabled = GetterUtil.getBoolean(
			props.get(PropsKeys.CLUSTER_LINK_ENABLED));
		_defaultBootstrapLoaderPropertiesString = getPortalPropertiesString(
			PropsKeys.EHCACHE_BOOTSTRAP_CACHE_LOADER_PROPERTIES_DEFAULT);
		_defaultReplicatorPropertiesString = getPortalPropertiesString(
			PropsKeys.EHCACHE_REPLICATOR_PROPERTIES_DEFAULT);
		_replicatorProperties = props.getProperties(
			PropsKeys.EHCACHE_REPLICATOR_PROPERTIES + StringPool.PERIOD, true);
	}

	protected String getPortalPropertiesString(String portalPropertyKey) {
		String[] array = props.getArray(portalPropertyKey);

		if (array.length == 0) {
			return null;
		}

		if (array.length == 1) {
			return array[0];
		}

		StringBundler sb = new StringBundler(array.length * 2);

		for (String value : array) {
			sb.append(value);
			sb.append(StringPool.COMMA);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	@Override
	protected boolean isRequireSerialization(
		CacheConfiguration cacheConfiguration) {

		if (clusterEnabled) {
			return true;
		}

		return super.isRequireSerialization(cacheConfiguration);
	}

	@Override
	protected void manageConfiguration(
		Configuration configuration,
		PortalCacheManagerConfiguration portalCacheManagerConfiguration) {

		if (!clusterEnabled) {
			return;
		}

		PortalCacheConfiguration defaultPortalCacheConfiguration =
			portalCacheManagerConfiguration.
				getDefaultPortalCacheConfiguration();

		Map<String, ObjectValuePair<Properties, Properties>>
			mergedPropertiesMap = _getMergedPropertiesMap();

		for (Map.Entry<String, ObjectValuePair<Properties, Properties>> entry :
				mergedPropertiesMap.entrySet()) {

			String portalCacheName = entry.getKey();

			PortalCacheConfiguration portalCacheConfiguration =
				portalCacheManagerConfiguration.getPortalCacheConfiguration(
					portalCacheName);

			if (portalCacheConfiguration == null) {
				portalCacheConfiguration =
					defaultPortalCacheConfiguration.newPortalCacheConfiguration(
						portalCacheName);

				portalCacheManagerConfiguration.putPortalCacheConfiguration(
					portalCacheName, portalCacheConfiguration);
			}

			ObjectValuePair<Properties, Properties> propertiesPair =
				entry.getValue();

			if (_bootstrapLoaderEnabled && (propertiesPair.getKey() != null)) {
				portalCacheConfiguration.
					setPortalCacheBootstrapLoaderProperties(
						propertiesPair.getKey());
			}

			if (propertiesPair.getValue() != null) {
				Set<Properties> portalCacheListenerPropertiesSet =
					portalCacheConfiguration.
						getPortalCacheListenerPropertiesSet();

				Iterator<Properties> itr =
					portalCacheListenerPropertiesSet.iterator();

				while (itr.hasNext()) {
					Properties properties = itr.next();

					if ((Boolean)properties.get(
							PortalCacheReplicator.REPLICATOR)) {

						itr.remove();
					}
				}

				portalCacheListenerPropertiesSet.add(propertiesPair.getValue());
			}
		}
	}

	@Override
	protected PortalCacheConfiguration parseCacheListenerConfigurations(
		CacheConfiguration cacheConfiguration, ClassLoader classLoader,
		boolean usingDefault) {

		PortalCacheConfiguration portalCacheConfiguration =
			super.parseCacheListenerConfigurations(
				cacheConfiguration, classLoader, usingDefault);

		if (!clusterEnabled) {
			return portalCacheConfiguration;
		}

		String cacheName = cacheConfiguration.getName();

		if (_bootstrapLoaderEnabled) {
			String bootstrapLoaderPropertiesString =
				(String)_bootstrapLoaderProperties.remove(cacheName);

			if (Validator.isNull(bootstrapLoaderPropertiesString)) {
				bootstrapLoaderPropertiesString =
					_defaultBootstrapLoaderPropertiesString;
			}

			portalCacheConfiguration.setPortalCacheBootstrapLoaderProperties(
				parseProperties(
					bootstrapLoaderPropertiesString, StringPool.COMMA));
		}

		String replicatorPropertiesString =
			(String)_replicatorProperties.remove(cacheName);

		if (Validator.isNull(replicatorPropertiesString)) {
			replicatorPropertiesString = _defaultReplicatorPropertiesString;
		}

		Properties replicatorProperties = parseProperties(
			replicatorPropertiesString, StringPool.COMMA);

		replicatorProperties.put(PortalCacheReplicator.REPLICATOR, true);

		Set<Properties> portalCacheListenerPropertiesSet =
			portalCacheConfiguration.getPortalCacheListenerPropertiesSet();

		portalCacheListenerPropertiesSet.add(replicatorProperties);

		return portalCacheConfiguration;
	}

	@Reference(unbind = "-")
	protected void setProps(Props props) {
		this.props = props;
	}

	protected boolean clusterEnabled;

	private Map<String, ObjectValuePair<Properties, Properties>>
		_getMergedPropertiesMap() {

		Map<String, ObjectValuePair<Properties, Properties>>
			mergedPropertiesMap = new HashMap<>();

		if (_bootstrapLoaderEnabled) {
			for (String portalCacheName :
					_bootstrapLoaderProperties.stringPropertyNames()) {

				mergedPropertiesMap.put(
					portalCacheName,
					new ObjectValuePair(
						parseProperties(
							_bootstrapLoaderProperties.getProperty(
								portalCacheName),
							StringPool.COMMA),
						null));
			}
		}

		for (String portalCacheName :
				_replicatorProperties.stringPropertyNames()) {

			Properties replicatorProperties = parseProperties(
				_replicatorProperties.getProperty(portalCacheName),
				StringPool.COMMA);

			replicatorProperties.put(PortalCacheReplicator.REPLICATOR, true);

			ObjectValuePair<Properties, Properties> objectValuePair =
				mergedPropertiesMap.get(portalCacheName);

			if (objectValuePair == null) {
				mergedPropertiesMap.put(
					portalCacheName,
					new ObjectValuePair(null, replicatorProperties));
			}
			else {
				objectValuePair.setValue(replicatorProperties);
			}
		}

		return mergedPropertiesMap;
	}

	private boolean _bootstrapLoaderEnabled;
	private Properties _bootstrapLoaderProperties;
	private String _defaultBootstrapLoaderPropertiesString;
	private String _defaultReplicatorPropertiesString;
	private Properties _replicatorProperties;

}