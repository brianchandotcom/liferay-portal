/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.orm;

import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.util.ProxyFactory;

/**
 * @author Brian Wing Shun Chan
 */
public class FinderCacheUtil {

	public static void clearCache() {
		FinderCache finderCache = getFinderCache();

		finderCache.clearCache();
	}

	public static void clearCache(Class<?> clazz) {
		FinderCache finderCache = getFinderCache();

		finderCache.clearCache(clazz);
	}

	public static void clearDSLQueryCache(String tableName) {
		FinderCache finderCache = getFinderCache();

		finderCache.clearDSLQueryCache(tableName);
	}

	public static void clearLocalCache() {
		FinderCache finderCache = getFinderCache();

		finderCache.clearLocalCache();
	}

	public static FinderCache getFinderCache() {
		FinderCache finderCache = _finderCacheSnapshot.get();

		if (finderCache == null) {
			return DummyFinderCacheHolder._dummyFinderCache;
		}

		return finderCache;
	}

	public static Object getResult(
		FinderPath finderPath, Object[] args,
		BasePersistence<?> basePersistence) {

		FinderCache finderCache = getFinderCache();

		return finderCache.getResult(finderPath, args, basePersistence);
	}

	public static void invalidate() {
		FinderCache finderCache = getFinderCache();

		finderCache.invalidate();
	}

	public static void putResult(
		FinderPath finderPath, Object[] args, Object result) {

		FinderCache finderCache = getFinderCache();

		finderCache.putResult(finderPath, args, result);
	}

	public static void removeCache(String className) {
		FinderCache finderCache = getFinderCache();

		finderCache.removeCache(className);
	}

	public static void removeResult(FinderPath finderPath, Object[] args) {
		FinderCache finderCache = getFinderCache();

		finderCache.removeResult(finderPath, args);
	}

	private static final Snapshot<FinderCache> _finderCacheSnapshot =
		new Snapshot<>(FinderCacheUtil.class, FinderCache.class);

	private static class DummyFinderCacheHolder {

		private static final FinderCache _dummyFinderCache =
			ProxyFactory.newDummyInstance(FinderCache.class);

	}

}