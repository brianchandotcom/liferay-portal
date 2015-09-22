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

package com.liferay.portal.cache;

import com.liferay.portal.kernel.cache.AbstractPortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.configuration.PortalCacheConfiguration;
import com.liferay.portal.kernel.cache.configuration.PortalCacheManagerConfiguration;

import java.io.Serializable;

import java.net.URL;

/**
 * @author Shuyang Zhou
 */
public class DummyPortalCacheManager<K extends Serializable, V>
	extends AbstractPortalCacheManager<K, V> {

	public DummyPortalCacheManager(String portalCacheManagerName) {
		setPortalCacheManagerName(portalCacheManagerName);

		initialize();
	}

	@Override
	public void reconfigurePortalCaches(URL configurationURL) {
	}

	@Override
	protected PortalCache<K, V> createPortalCache(
		PortalCacheConfiguration portalCacheConfiguration) {

		return new DummyPortalCache<>(
			this, portalCacheConfiguration.getPortalCacheName());
	}

	@Override
	protected void doClearAll() {
	}

	@Override
	protected void doDestroy() {
	}

	@Override
	protected void doRemovePortalCache(String portalCacheName) {
	}

	@Override
	protected PortalCacheManagerConfiguration
		getPortalCacheManagerConfiguration() {

		return new PortalCacheManagerConfiguration(
			null,
			new PortalCacheConfiguration(
				PortalCacheConfiguration.DEFAULT_PORTAL_CACHE_NAME, null, null),
			null);
	}

	@Override
	protected void initPortalCacheManager() {
	}

}