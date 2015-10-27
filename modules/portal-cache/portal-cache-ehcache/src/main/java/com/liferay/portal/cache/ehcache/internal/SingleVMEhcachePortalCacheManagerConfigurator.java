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

import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.util.Props;

import java.util.List;
import java.util.Properties;

import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.FactoryConfiguration;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dante Wang
 */
@Component(
	immediate = true,
	property = PortalCacheManager.PORTAL_CACHE_MANAGER_NAME + "=" + PortalCacheManagerNames.SINGLE_VM,
	service = AbstractEhcachePortalCacheManagerConfigurator.class
)
public class SingleVMEhcachePortalCacheManagerConfigurator
	extends AbstractEhcachePortalCacheManagerConfigurator {

	@Override
	@SuppressWarnings("rawtypes")
	protected void handleCacheManagerPeerFactoryConfigurations(
		List<FactoryConfiguration> factoryConfigurations) {

		if (!factoryConfigurations.isEmpty()) {
			factoryConfigurations.clear();
		}
	}

	@Override
	protected Properties parsePortalCacheBootstrapLoaderProperties(
		CacheConfiguration cacheConfiguration) {

		cacheConfiguration.addBootstrapCacheLoaderFactory(null);

		return null;
	}

	@Reference(unbind = "-")
	protected void setProps(Props props) {
		this.props = props;
	}

}