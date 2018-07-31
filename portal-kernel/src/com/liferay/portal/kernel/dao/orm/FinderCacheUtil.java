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

package com.liferay.portal.kernel.dao.orm;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Brian Wing Shun Chan
 */
public class FinderCacheUtil {

	public static void clearCache() {
		_finderCache.clearCache();
	}

	public static void clearCache(String className) {
		_finderCache.clearCache(className);
	}

	public static void clearLocalCache() {
		_finderCache.clearLocalCache();
	}

	public static FinderCache getFinderCache() {
		return _finderCache;
	}

	public static Object getResult(
		FinderPath finderPath, Object[] args,
		BasePersistenceImpl<? extends BaseModel<?>> basePersistenceImpl) {

		return _finderCache.getResult(finderPath, args, basePersistenceImpl);
	}

	public static void invalidate() {
		getFinderCache().invalidate();
	}

	public static void putResult(
		FinderPath finderPath, Object[] args, Object result) {

		_finderCache.putResult(finderPath, args, result);
	}

	public static void putResult(
		FinderPath finderPath, Object[] args, Object result, boolean quiet) {

		_finderCache.putResult(finderPath, args, result, quiet);
	}

	public static void removeCache(String className) {
		_finderCache.removeCache(className);
	}

	public static void removeResult(FinderPath finderPath, Object[] args) {
		_finderCache.removeResult(finderPath, args);
	}

	private static volatile FinderCache _finderCache =
		ServiceProxyFactory.newServiceTrackedInstance(
			FinderCache.class, FinderCacheUtil.class, "_finderCache", false);

}