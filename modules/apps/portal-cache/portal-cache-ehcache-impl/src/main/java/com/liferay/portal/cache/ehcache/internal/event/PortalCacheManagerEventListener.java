/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.ehcache.internal.event;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.PortalCacheManagerListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.ehcache.Cache;
import org.ehcache.Status;
import org.ehcache.core.events.CacheManagerListener;

/**
 * @author Shuyang Zhou
 */
public class PortalCacheManagerEventListener implements CacheManagerListener {

	public PortalCacheManagerEventListener(
		PortalCacheManagerListener portalCacheManagerListener,
		String portalCacheManagerName) {

		_portalCacheManagerListener = portalCacheManagerListener;

		_log = LogFactoryUtil.getLog(
			PortalCacheManagerEventListener.class.getName() +
				StringPool.PERIOD + portalCacheManagerName);
	}

	@Override
	public void cacheAdded(String alias, Cache<?, ?> cache) {
		if (_log.isDebugEnabled()) {
			_log.debug("Added cache " + alias);
		}

		_portalCacheManagerListener.notifyPortalCacheAdded(alias);
	}

	@Override
	public void cacheRemoved(String alias, Cache<?, ?> cache) {
		if (_log.isDebugEnabled()) {
			_log.debug("Removed cache " + alias);
		}

		_portalCacheManagerListener.notifyPortalCacheRemoved(alias);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PortalCacheManagerEventListener)) {
			return false;
		}

		PortalCacheManagerEventListener ehcacheCacheManagerListener =
			(PortalCacheManagerEventListener)object;

		return _portalCacheManagerListener.equals(
			ehcacheCacheManagerListener._portalCacheManagerListener);
	}

	@Override
	public int hashCode() {
		return _portalCacheManagerListener.hashCode();
	}

	@Override
	public void stateTransition(Status from, Status to) {
		if (to == Status.AVAILABLE) {
			_portalCacheManagerListener.init();
		}
		else if (to == Status.UNINITIALIZED) {
			_portalCacheManagerListener.dispose();
		}
	}

	private final Log _log;
	private final PortalCacheManagerListener _portalCacheManagerListener;

}