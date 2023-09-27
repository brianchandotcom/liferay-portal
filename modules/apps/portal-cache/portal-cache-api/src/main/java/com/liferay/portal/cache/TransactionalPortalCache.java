/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.transactional.TransactionalPortalCacheUtil;

import java.io.Serializable;

/**
 * @author Shuyang Zhou
 * @author Edward Han
 */
public class TransactionalPortalCache<K extends Serializable, V>
	extends PortalCacheWrapper<K, V> {

	public TransactionalPortalCache(
		PortalCache<K, V> portalCache, boolean mvcc) {

		super(portalCache);

		_mvcc = mvcc;
	}

	@Override
	public V get(K key) {
		V result = null;

		if (TransactionalPortalCacheUtil.isEnabled()) {
			if (key == null) {
				throw new NullPointerException("Key is null");
			}

			result = TransactionalPortalCacheUtil.get(portalCache, key);

			if (result == TransactionalPortalCacheUtil.getNullHolder()) {
				return null;
			}
		}

		if (result == null) {
			result = portalCache.get(key);
		}

		return result;
	}

	@Override
	public void put(K key, V value) {
		put(key, value, DEFAULT_TIME_TO_LIVE);
	}

	@Override
	public void put(K key, V value, int timeToLive) {
		if (TransactionalPortalCacheUtil.isEnabled()) {
			if (key == null) {
				throw new NullPointerException("Key is null");
			}

			if (value == null) {
				throw new NullPointerException("Value is null");
			}

			if (timeToLive < 0) {
				throw new IllegalArgumentException("Time to live is negative");
			}

			TransactionalPortalCacheUtil.put(
				portalCache, key, value, timeToLive, _mvcc);
		}
		else {
			portalCache.put(key, value, timeToLive);
		}
	}

	@Override
	public void remove(K key) {
		if (TransactionalPortalCacheUtil.isEnabled()) {
			if (key == null) {
				throw new NullPointerException("Key is null");
			}

			TransactionalPortalCacheUtil.put(
				portalCache, key,
				(V)TransactionalPortalCacheUtil.getNullHolder(),
				DEFAULT_TIME_TO_LIVE, _mvcc);
		}
		else {
			portalCache.remove(key);
		}
	}

	@Override
	public void removeAll() {
		if (TransactionalPortalCacheUtil.isEnabled()) {
			TransactionalPortalCacheUtil.removeAll(portalCache, _mvcc);
		}
		else {
			portalCache.removeAll();
		}
	}

	private final boolean _mvcc;

}