/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	@Override
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

			if ((value.getMvccVersion() < oldValue.getMvccVersion()) ||
				_lowLevelCache.replace(key, oldValue, value, timeToLive)) {

				return;
			}
		}
	}

	private final LowLevelCache<K, V> _lowLevelCache;

}