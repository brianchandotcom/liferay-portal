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

package com.liferay.portal.cache.ehcache;

import com.liferay.portal.cache.cluster.ClusterReplicationThreadLocal;
import com.liferay.portal.kernel.cache.PortalCacheWrapper;
import com.liferay.portal.model.MVCCModel;

import java.io.Serializable;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

/**
 * @author Shuyang Zhou
 */
public class MVCCEhcachePortalCache<K extends Serializable, V extends MVCCModel>
	extends PortalCacheWrapper<K, V> {

	public MVCCEhcachePortalCache(EhcachePortalCache<K, V> ehcachePortalCache) {
		super(ehcachePortalCache);

		this.ehcachePortalCache = ehcachePortalCache;
	}

	@Override
	public void put(K key, V value) {
		doPut(key, value, false, -1);
	}

	@Override
	public void put(K key, V value, int timeToLive) {
		doPut(key, value, false, timeToLive);
	}

	@Override
	public void putQuiet(K key, V value) {
		doPut(key, value, true, -1);
	}

	@Override
	public void putQuiet(K key, V value, int timeToLive) {
		doPut(key, value, true, timeToLive);
	}

	protected void doPut(K key, V value, boolean quiet, int timeToLive) {
		boolean replicate = false;

		if (quiet) {
			replicate = ClusterReplicationThreadLocal.isReplicate();

			ClusterReplicationThreadLocal.setReplicate(false);
		}

		try {
			Element newElement = new Element(key, value);

			if (timeToLive >= 0) {
				newElement.setTimeToLive(timeToLive);
			}

			Ehcache ehcache = getEhcache();

			while (true) {
				Element oldElement = ehcache.get(key);

				if (oldElement == null) {
					oldElement = ehcache.putIfAbsent(newElement);

					if (oldElement == null) {
						return;
					}
				}

				V oldValue = (V)oldElement.getObjectValue();

				if ((oldValue != null) &&
					(value.getMvccVersion() <= oldValue.getMvccVersion())) {

					return;
				}

				if (ehcache.replace(oldElement, newElement)) {
					return;
				}
			}
		}
		finally {
			if (quiet) {
				ClusterReplicationThreadLocal.setReplicate(replicate);
			}
		}
	}

	protected Ehcache getEhcache() {
		return ehcachePortalCache.ehcache;
	}

	protected EhcachePortalCache<K, V> ehcachePortalCache;

}