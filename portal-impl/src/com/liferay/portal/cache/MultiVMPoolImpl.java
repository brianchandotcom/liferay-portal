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

import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.resiliency.spi.cache.SPIPortalCacheManagerConfigurator;
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Young
 */
@DoPrivileged
public class MultiVMPoolImpl implements MultiVMPool {

	public MultiVMPoolImpl() {
		Registry registry = RegistryUtil.getRegistry();

		StringBundler sb = new StringBundler(11);

		sb.append("(&(objectClass=");
		sb.append(PortalCacheManager.class.getName());
		sb.append(")(");
		sb.append(PortalCacheManager.PORTAL_CACHE_MANAGER_NAME);
		sb.append("=");
		sb.append(PortalCacheManagerNames.MULTI_VM);
		sb.append(")(");
		sb.append(PortalCacheManager.PORTAL_CACHE_MANAGER_TYPE);
		sb.append("=");
		sb.append(PropsValues.PORTAL_CACHE_MANAGER_TYPE_MULTI_VM);
		sb.append("))");

		Filter filter = registry.getFilter(sb.toString());

		ServiceTracker<PortalCacheManager
			<? extends Serializable, ? extends Serializable>, PortalCacheManager
				<? extends Serializable, ? extends Serializable>>
					serviceTracker = registry.trackServices(filter);

		serviceTracker.open();

		try {
			_portalCacheManager =
				SPIPortalCacheManagerConfigurator.createSPIPortalCacheManager(
					serviceTracker.waitForService(0));
		}
		catch (Exception e) {
			throw new IllegalStateException(
				"Unable to initialize multi VM pool", e);
		}
	}

	@Override
	public void clear() {
		_portalCacheManager.clearAll();
	}

	@Override
	public PortalCache<? extends Serializable, ? extends Serializable> getCache(
		String portalCacheName) {

		return _portalCacheManager.getCache(portalCacheName);
	}

	@Override
	public PortalCache<? extends Serializable, ? extends Serializable> getCache(
		String portalCacheName, boolean blocking) {

		return _portalCacheManager.getCache(portalCacheName, blocking);
	}

	@Override
	public PortalCacheManager<? extends Serializable, ? extends Serializable>
		getCacheManager() {

		return _portalCacheManager;
	}

	@Override
	public void removeCache(String portalCacheName) {
		_portalCacheManager.removeCache(portalCacheName);
	}

	private final
		PortalCacheManager<? extends Serializable, ? extends Serializable>
			_portalCacheManager;

}