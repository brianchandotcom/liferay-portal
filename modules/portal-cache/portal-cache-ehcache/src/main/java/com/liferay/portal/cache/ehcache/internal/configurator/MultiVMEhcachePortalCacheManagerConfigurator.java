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

import com.liferay.portal.cache.PortalCacheReplicator;
import com.liferay.portal.cache.configuration.PortalCacheConfiguration;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.Properties;
import java.util.Set;

import net.sf.ehcache.config.CacheConfiguration;

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
		_clusterEnabled = GetterUtil.getBoolean(
			props.get(PropsKeys.CLUSTER_LINK_ENABLED));

		_defaultBootstrapLoaderPropertiesString = props.get(
			PropsKeys.EHCACHE_BOOTSTRAP_CACHE_LOADER_PROPERTIES_DEFAULT);
		_bootstrapLoaderProperties = props.getProperties(
			PropsKeys.EHCACHE_BOOTSTRAP_CACHE_LOADER_PROPERTIES +
				StringPool.PERIOD,
			true);

		_defaultReplicatorPropertiesString = props.get(
			PropsKeys.EHCACHE_CLUSTER_LINK_REPLICATOR_PROPERTIES_DEFAULT);
		_replicatorProperties = props.getProperties(
			PropsKeys.EHCACHE_CLUSTER_LINK_REPLICATOR_PROPERTIES +
				StringPool.PERIOD,
			true);
	}

	@Override
	protected boolean isRequireSerialization(
		CacheConfiguration cacheConfiguration) {

		if (_clusterEnabled) {
			return true;
		}

		return super.isRequireSerialization(cacheConfiguration);
	}

	@Override
	protected PortalCacheConfiguration parseCacheListenerConfigurations(
		CacheConfiguration cacheConfiguration, boolean usingDefault) {

		PortalCacheConfiguration portalCacheConfiguration =
			super.parseCacheListenerConfigurations(
				cacheConfiguration, usingDefault);

		if (!_clusterEnabled) {
			return portalCacheConfiguration;
		}

		String cacheName = cacheConfiguration.getName();

		if (_bootstrapLoaderEnabled) {
			String bootstrapLoaderPropertiesString =
				_bootstrapLoaderProperties.getProperty(cacheName);

			if (Validator.isNull(bootstrapLoaderPropertiesString)) {
				bootstrapLoaderPropertiesString =
					_defaultBootstrapLoaderPropertiesString;
			}

			portalCacheConfiguration.setPortalCacheBootstrapLoaderProperties(
				parseProperties(
					bootstrapLoaderPropertiesString, StringPool.COMMA));
		}

		String replicatorPropertiesString = _replicatorProperties.getProperty(
			cacheName);

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

	private boolean _bootstrapLoaderEnabled;
	private Properties _bootstrapLoaderProperties;
	private boolean _clusterEnabled;
	private String _defaultBootstrapLoaderPropertiesString;
	private String _defaultReplicatorPropertiesString;
	private Properties _replicatorProperties;

}