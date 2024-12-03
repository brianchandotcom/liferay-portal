/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.test.util;

import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

import java.io.Serializable;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Dante Wang
 */
public class ShardedTestPortalCache<K extends Serializable, V>
	extends TestPortalCache<K, V> {

	public ShardedTestPortalCache(String portalCacheName) {
		super(portalCacheName);
	}

	@Override
	public boolean isSharded() {
		return true;
	}

	@Override
	protected ConcurrentMap<K, V> getConcurrentMap() {
		return _shardedConcurrentMaps.computeIfAbsent(
			CompanyThreadLocal.getNonsystemCompanyId(),
			companyId -> new ConcurrentHashMap<>());
	}

	private final ConcurrentMap<Long, ConcurrentMap<K, V>>
		_shardedConcurrentMaps = new ConcurrentHashMap<>();

}