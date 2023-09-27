/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.internal;

import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;

import java.io.Serializable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Young
 */
@Component(service = MultiVMPool.class)
public class MultiVMPoolImpl implements MultiVMPool {

	@Override
	public void clear() {
		_portalCacheManager.clearAll();
	}

	@Override
	public PortalCache<? extends Serializable, ? extends Serializable>
		getPortalCache(String portalCacheName) {

		return _portalCacheManager.getPortalCache(portalCacheName);
	}

	@Override
	public PortalCache<? extends Serializable, ? extends Serializable>
		getPortalCache(String portalCacheName, boolean mvcc) {

		return _portalCacheManager.getPortalCache(portalCacheName, mvcc);
	}

	@Override
	public PortalCache<? extends Serializable, ? extends Serializable>
		getPortalCache(String portalCacheName, boolean mvcc, boolean sharded) {

		return _portalCacheManager.getPortalCache(
			portalCacheName, mvcc, sharded);
	}

	@Override
	public PortalCacheManager<? extends Serializable, ? extends Serializable>
		getPortalCacheManager() {

		return _portalCacheManager;
	}

	@Override
	public void removePortalCache(String portalCacheName) {
		_portalCacheManager.removePortalCache(portalCacheName);
	}

	@Reference(
		target = "(portal.cache.manager.name=" + PortalCacheManagerNames.MULTI_VM + ")",
		unbind = "-"
	)
	private PortalCacheManager<? extends Serializable, ? extends Serializable>
		_portalCacheManager;

}