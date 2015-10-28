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
import com.liferay.portal.cache.ehcache.EhcacheConstants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;

import java.util.Properties;

import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.FactoryConfiguration;

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
	extends AbstractEhcachePortalCacheManagerConfigurator {

	@Activate
	protected void activate() {
		_clusterEnabled = GetterUtil.getBoolean(
			props.get(PropsKeys.CLUSTER_LINK_ENABLED));
		_clusterLinkReplicationEnabled = GetterUtil.getBoolean(
			props.get(PropsKeys.EHCACHE_CLUSTER_LINK_REPLICATION_ENABLED));
	}

	@Override
	protected boolean isClearCacheManagerPeerConfigurations() {
		if (_clusterEnabled && !_clusterLinkReplicationEnabled) {
			return false;
		}

		return true;
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
	protected boolean isValidCacheEventListener(
		Properties properties, boolean usingDefault) {

		if (Boolean.valueOf(
				properties.getProperty(PortalCacheReplicator.REPLICATOR))) {

			if (_clusterEnabled) {
				return true;
			}

			return false;
		}

		return super.isValidCacheEventListener(properties, usingDefault);
	}

	@Override
	protected Properties parsePortalCacheBootstrapLoaderProperties(
		FactoryConfiguration<?> factoryConfiguration) {

		if ((factoryConfiguration == null) || !_clusterEnabled) {
			return null;
		}

		Properties portalCacheBootstrapLoaderProperties = parseProperties(
			factoryConfiguration.getProperties(),
			factoryConfiguration.getPropertySeparator());

		if (!_clusterLinkReplicationEnabled) {
			portalCacheBootstrapLoaderProperties.put(
				EhcacheConstants.
					BOOTSTRAP_CACHE_LOADER_FACTORY_CLASS_NAME,
				factoryConfiguration.getFullyQualifiedClassPath());
		}

		return portalCacheBootstrapLoaderProperties;
	}

	@Reference(unbind = "-")
	protected void setProps(Props props) {
		this.props = props;
	}

	private boolean _clusterEnabled;
	private boolean _clusterLinkReplicationEnabled;

}