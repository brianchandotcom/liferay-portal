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

import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Serializable;

/**
 * @author Shuyang Zhou
 */
public class MVCCPortalCache<K extends Serializable, V extends MVCCModel>
	extends PortalCacheWrapper<K, V> {

	public MVCCPortalCache(LowLevelCache<K, V> lowLevelCache) {
		super(lowLevelCache);

		_lowLevelCache = lowLevelCache;
	}

	public boolean isMVCC() {
		return true;
	}

	@Override
	public void put(K key, V value) {
		put(key, value, DEFAULT_TIME_TO_LIVE);
	}

	@Override
	public void put(K key, V value, int timeToLive) {
		while (true) {
			V oldValue = _lowLevelCache.get(key);

			if (oldValue == null) {
				oldValue = _lowLevelCache.putIfAbsent(key, value, timeToLive);

				if (oldValue == null) {
					return;
				}
			}

			if (value.getMvccVersion() <= oldValue.getMvccVersion()) {
				return;
			}

			if (_lowLevelCache.replace(key, oldValue, value, timeToLive)) {
				return;
			}
		}
	}

	private final LowLevelCache<K, V> _lowLevelCache;

}