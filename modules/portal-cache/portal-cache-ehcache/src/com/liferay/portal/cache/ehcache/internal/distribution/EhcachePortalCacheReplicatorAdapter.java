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

package com.liferay.portal.cache.ehcache.internal.distribution;

import com.liferay.portal.cache.ehcache.internal.event.EhcachePortalCacheListenerAdapter;
import com.liferay.portal.kernel.cache.PortalCacheReplicator;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.io.Serializable;

import java.lang.reflect.Field;

import net.sf.ehcache.distribution.RMIAsynchronousCacheReplicator;
import net.sf.ehcache.event.CacheEventListener;

/**
 * @author Tina Tian
 */
public class EhcachePortalCacheReplicatorAdapter
	<K extends Serializable, V extends Serializable>
		extends EhcachePortalCacheListenerAdapter<K, V>
		implements PortalCacheReplicator<K, V> {

	public EhcachePortalCacheReplicatorAdapter(
		CacheEventListener cacheEventListener) {

		super(cacheEventListener);
	}

	@Override
	public void dispose() {
		super.dispose();

		if (!(cacheEventListener instanceof RMIAsynchronousCacheReplicator)) {
			return;
		}

		RMIAsynchronousCacheReplicator rmiAsynchronousCacheReplicator =
			(RMIAsynchronousCacheReplicator)cacheEventListener;

		try {
			Thread replicationThread = (Thread)_REPLICATION_THREAD_FIELD.get(
				rmiAsynchronousCacheReplicator);

			replicationThread.interrupt();

			replicationThread.join(_WAIT_TIME);

			if (replicationThread.isAlive() && _log.isWarnEnabled()) {
				_log.warn(
					"Give up waiting on thread " + replicationThread +
						" after waiting for " + _WAIT_TIME + "ms");
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to dispose cache event listener " +
						cacheEventListener,
					e);
			}
		}
	}

	private static final Field _REPLICATION_THREAD_FIELD;

	private static final long _WAIT_TIME = 1000;

	private static final Log _log = LogFactoryUtil.getLog(
		EhcachePortalCacheReplicatorAdapter.class);

	static {
		try {
			_REPLICATION_THREAD_FIELD = ReflectionUtil.getDeclaredField(
				RMIAsynchronousCacheReplicator.class, "replicationThread");
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

}