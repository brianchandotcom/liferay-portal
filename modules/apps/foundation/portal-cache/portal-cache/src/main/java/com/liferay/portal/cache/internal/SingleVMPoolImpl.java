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

package com.liferay.portal.cache.internal;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.cache.SingleVMPool;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Young
 */
@Component(immediate = true, service = SingleVMPool.class)
public class SingleVMPoolImpl implements SingleVMPool {

	@Override
	public void clear() {
		_portalCacheManager.clearAll();
	}

	/**
	 * @deprecated As of 2.1.0, replaced by {@link #getPortalCache(String)}
	 */
	@Deprecated
	@Override
	public PortalCache<? extends Serializable, ?> getCache(
		String portalCacheName) {

		return getPortalCache(portalCacheName);
	}

	/**
	 * @deprecated As of 2.1.0, replaced by {@link #getPortalCache(String,
	 *             boolean)}
	 */
	@Deprecated
	@Override
	public PortalCache<? extends Serializable, ?> getCache(
		String portalCacheName, boolean blocking) {

		return getPortalCache(portalCacheName, blocking);
	}

	/**
	 * @deprecated As of 2.1.0, replaced by {@link #getPortalCacheManager()}
	 */
	@Deprecated
	@Override
	public PortalCacheManager<? extends Serializable, ?> getCacheManager() {
		return getPortalCacheManager();
	}

	@Override
	public PortalCache<? extends Serializable, ?> getPortalCache(
		String portalCacheName) {

		return _portalCacheManager.getPortalCache(portalCacheName);
	}

	@Override
	public PortalCache<? extends Serializable, ?> getPortalCache(
		String portalCacheName, boolean blocking) {

		return _portalCacheManager.getPortalCache(portalCacheName, blocking);
	}

	@Override
	public PortalCacheManager<? extends Serializable, ?>
		getPortalCacheManager() {

		return _portalCacheManager;
	}

	/**
	 * @deprecated As of 2.1.0, replaced by {@link #removePortalCache(String)}
	 */
	@Deprecated
	@Override
	public void removeCache(String portalCacheName) {
		removePortalCache(portalCacheName);
	}

	@Override
	public void removePortalCache(String portalCacheName) {
		_portalCacheManager.removePortalCache(portalCacheName);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_portalCacheManager.clearAll();
	}

	@Deactivate
	protected void deactivate() {
		_portalCacheManager.clearAll();
	}

	@Reference(
		target = "(portal.cache.manager.name=" + PortalCacheManagerNames.SINGLE_VM + ")",
		unbind = "-"
	)
	protected void setPortalCacheManager(
		PortalCacheManager<? extends Serializable, ? extends Serializable>
			portalCacheManager) {

		_portalCacheManager = portalCacheManager;
	}

	private PortalCacheManager<? extends Serializable, ?> _portalCacheManager;

}