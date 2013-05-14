/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Young
 */
@DoPrivileged
public class SingleVMPoolImpl implements SingleVMPool {

	@Override
	public void clear() {
		_portalCacheManager.clearAll();
	}

	@Override
	public PortalCache<? extends Serializable, ?> getCache(String name) {
		return _portalCacheManager.getCache(name);
	}

	@Override
	public PortalCache<? extends Serializable, ?> getCache(
		String name, boolean blocking) {

		return _portalCacheManager.getCache(name, blocking);
	}

	@Override
	public void removeCache(String name) {
		_portalCacheManager.removeCache(name);
	}

	public void setPortalCacheManager(
		PortalCacheManager<? extends Serializable, ?> portalCacheManager) {

		_portalCacheManager = portalCacheManager;
	}

	private PortalCacheManager<? extends Serializable, ?> _portalCacheManager;

}