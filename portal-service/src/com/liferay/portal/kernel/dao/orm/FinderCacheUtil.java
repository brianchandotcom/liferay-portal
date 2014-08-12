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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class FinderCacheUtil {

	public static void clearCache() {
		getFinderCache().clearCache();
	}

	public static void clearCache(String className) {
		getFinderCache().clearCache(className);
	}

	public static void clearLocalCache() {
		getFinderCache().clearLocalCache();
	}

	public static FinderCache getFinderCache() {
		PortalRuntimePermission.checkGetBeanProperty(FinderCacheUtil.class);

		return _finderCache;
	}

	public static Object getResult(
		FinderPath finderPath, Object[] args,
		BasePersistenceImpl<? extends BaseModel<?>> basePersistenceImpl) {

		return getFinderCache().getResult(
			finderPath, args, basePersistenceImpl);
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	@Deprecated
	public static Object getResult(
		String className, String methodName, String[] params, Object[] args,
		SessionFactory sessionFactory) {

		_log.error(
			"Regenerate " + className +
				" via \"ant build-service\" or else caching will not work");

		return null;
	}

	public static void invalidate() {
		getFinderCache().invalidate();
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	@Deprecated
	public static void putResult(
		boolean classNameCacheEnabled, String className, String methodName,
		String[] params, Object[] args, Object result) {

		_log.error(
			"Regenerate " + className +
				" via \"ant build-service\" or else caching will not work");
	}

	public static void putResult(
		FinderPath finderPath, Object[] args, Object result) {

		getFinderCache().putResult(finderPath, args, result);
	}

	public static void putResult(
		FinderPath finderPath, Object[] args, Object result, boolean quiet) {

		getFinderCache().putResult(finderPath, args, result, quiet);
	}

	public static void removeCache(String className) {
		getFinderCache().removeCache(className);
	}

	public static void removeResult(FinderPath finderPath, Object[] args) {
		getFinderCache().removeResult(finderPath, args);
	}

	public void setFinderCache(FinderCache finderCache) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_finderCache = finderCache;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FinderCacheUtil.class);

	private static FinderCache _finderCache;

}