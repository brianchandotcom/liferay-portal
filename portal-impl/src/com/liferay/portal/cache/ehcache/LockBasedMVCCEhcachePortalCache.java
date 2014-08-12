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

import com.liferay.portal.cache.mvcc.MVCCPortalCache;
import com.liferay.portal.kernel.cache.LowLevelCache;
import com.liferay.portal.model.MVCCModel;

import java.io.Serializable;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

/**
 * @author Shuyang Zhou
 */
public class LockBasedMVCCEhcachePortalCache
		<K extends Serializable, V extends MVCCModel>
	extends MVCCPortalCache<K, V> {

	public LockBasedMVCCEhcachePortalCache(LowLevelCache<K, V> lowLevelCache) {
		super(lowLevelCache);

		if (!(lowLevelCache instanceof EhcachePortalCache)) {
			throw new IllegalArgumentException(
				"LowLevelCache is not a EhcachePortalCache");
		}

		EhcachePortalCache<?, ?> ehcachePortalCache =
			(EhcachePortalCache<?, ?>)lowLevelCache;

		_ehcache = ehcachePortalCache.ehcache;
	}

	@Override
	public void remove(K key) {
		_ehcache.acquireWriteLockOnKey(key);

		try {
			super.remove(key);
		}
		finally {
			_ehcache.releaseWriteLockOnKey(key);
		}
	}

	@Override
	protected void doPut(K key, V value, int timeToLive, boolean quiet) {
		if (key == null) {
			throw new NullPointerException("Key is null");
		}

		if (value == null) {
			throw new NullPointerException("Value is null");
		}

		if (timeToLive < 0) {
			throw new IllegalArgumentException("Time to live is negative");
		}

		Element newElement = new Element(key, value);

		if (timeToLive > 0) {
			newElement.setTimeToLive(timeToLive);
		}

		_ehcache.acquireWriteLockOnKey(key);

		try {
			Element oldElement = _ehcache.get(key);

			if (oldElement == null) {
				_ehcache.put(newElement, quiet);

				return;
			}

			V oldValue = (V)oldElement.getObjectValue();

			if (value.getMvccVersion() <= oldValue.getMvccVersion()) {
				return;
			}

			_ehcache.put(newElement, quiet);
		}
		finally {
			_ehcache.releaseWriteLockOnKey(key);
		}
	}

	private final Ehcache _ehcache;

}