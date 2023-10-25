/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.orm;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.module.service.Snapshot;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 */
public class EntityCacheUtil {

	public static void clearCache() {
		EntityCache entityCache = _entityCacheSnapshot.get();

		entityCache.clearCache();
	}

	public static void clearCache(Class<?> clazz) {
		EntityCache entityCache = _entityCacheSnapshot.get();

		entityCache.clearCache(clazz);
	}

	public static void clearLocalCache() {
		EntityCache entityCache = _entityCacheSnapshot.get();

		entityCache.clearLocalCache();
	}

	public static EntityCache getEntityCache() {
		return _entityCacheSnapshot.get();
	}

	public static Serializable getLocalCacheResult(
		Class<?> clazz, Serializable primaryKey) {

		EntityCache entityCache = _entityCacheSnapshot.get();

		return entityCache.getLocalCacheResult(clazz, primaryKey);
	}

	public static PortalCache<Serializable, Serializable> getPortalCache(
		Class<?> clazz) {

		EntityCache entityCache = _entityCacheSnapshot.get();

		return entityCache.getPortalCache(clazz);
	}

	public static Serializable getResult(
		Class<?> clazz, Serializable primaryKey) {

		EntityCache entityCache = _entityCacheSnapshot.get();

		return entityCache.getResult(clazz, primaryKey);
	}

	public static void invalidate() {
		EntityCache entityCache = _entityCacheSnapshot.get();

		entityCache.invalidate();
	}

	public static void putResult(
		Class<?> clazz, BaseModel<?> baseModel, boolean quiet,
		boolean updateFinderCache) {

		EntityCache entityCache = _entityCacheSnapshot.get();

		entityCache.putResult(clazz, baseModel, quiet, updateFinderCache);
	}

	public static void putResult(
		Class<?> clazz, Serializable primaryKey, Serializable result) {

		EntityCache entityCache = _entityCacheSnapshot.get();

		entityCache.putResult(clazz, primaryKey, result);
	}

	public static void removeCache(String className) {
		EntityCache entityCache = _entityCacheSnapshot.get();

		entityCache.removeCache(className);
	}

	public static void removeResult(Class<?> clazz, BaseModel<?> baseModel) {
		EntityCache entityCache = _entityCacheSnapshot.get();

		entityCache.removeResult(clazz, baseModel);
	}

	public static void removeResult(Class<?> clazz, Serializable primaryKey) {
		EntityCache entityCache = _entityCacheSnapshot.get();

		entityCache.removeResult(clazz, primaryKey);
	}

	private static final Snapshot<EntityCache> _entityCacheSnapshot =
		new Snapshot<>(EntityCacheUtil.class, EntityCache.class);

}