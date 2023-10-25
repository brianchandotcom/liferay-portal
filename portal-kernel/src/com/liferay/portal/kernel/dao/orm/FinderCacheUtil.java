/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.orm;

import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * @author Brian Wing Shun Chan
 */
public class FinderCacheUtil {

	public static void clearCache() {
		FinderCache finderCache = _finderCacheSnapshot.get();

		finderCache.clearCache();
	}

	public static void clearCache(Class<?> clazz) {
		FinderCache finderCache = _finderCacheSnapshot.get();

		finderCache.clearCache(clazz);
	}

	public static void clearDSLQueryCache(String tableName) {
		FinderCache finderCache = _finderCacheSnapshot.get();

		finderCache.clearDSLQueryCache(tableName);
	}

	public static void clearLocalCache() {
		FinderCache finderCache = _finderCacheSnapshot.get();

		finderCache.clearLocalCache();
	}

	public static FinderCache getFinderCache() {
		return _finderCacheSnapshot.get();
	}

	public static Object getResult(
		FinderPath finderPath, Object[] args,
		BasePersistence<?> basePersistence) {

		FinderCache finderCache = _finderCacheSnapshot.get();

		return finderCache.getResult(finderPath, args, basePersistence);
	}

	public static void invalidate() {
		FinderCache finderCache = _finderCacheSnapshot.get();

		finderCache.invalidate();
	}

	public static void putResult(
		FinderPath finderPath, Object[] args, Object result) {

		FinderCache finderCache = _finderCacheSnapshot.get();

		finderCache.putResult(finderPath, args, result);
	}

	public static void removeCache(String className) {
		FinderCache finderCache = _finderCacheSnapshot.get();

		finderCache.removeCache(className);
	}

	public static void removeResult(FinderPath finderPath, Object[] args) {
		FinderCache finderCache = _finderCacheSnapshot.get();

		finderCache.removeResult(finderPath, args);
	}

	private static final Snapshot<FinderCache> _finderCacheSnapshot =
		new Snapshot<>(FinderCacheUtil.class, FinderCache.class);

}