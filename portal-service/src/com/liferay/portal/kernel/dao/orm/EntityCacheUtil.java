/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 */
public class EntityCacheUtil {

	public static void clearCache() {
		getEntityCache().clearCache();
	}

	public static void clearCache(String className) {
		getEntityCache().clearCache(className);
	}

	public static void clearLocalCache() {
		getEntityCache().clearLocalCache();
	}

	public static EntityCache getEntityCache() {
		return _entityCache;
	}

	public static Object getResult(
		boolean entityCacheEnabled, Class<?> clazz, Serializable primaryKey) {

		return getEntityCache().getResult(
			entityCacheEnabled, clazz, primaryKey);
	}

	public static void invalidate() {
		getEntityCache().invalidate();
	}

	public static Object loadResult(
		boolean entityCacheEnabled, Class<?> clazz, Serializable primaryKey,
		SessionFactory sessionFactory) {

		return getEntityCache().loadResult(
			entityCacheEnabled, clazz, primaryKey, sessionFactory);
	}

	public static void putResult(
		boolean entityCacheEnabled, Class<?> clazz, Serializable primaryKey,
		Object result) {

		getEntityCache().putResult(
			entityCacheEnabled, clazz, primaryKey, result);
	}

	public static void removeCache(String className) {
		getEntityCache().removeCache(className);
	}

	public static void removeResult(
		boolean entityCacheEnabled, Class<?> clazz, Serializable primaryKey) {

		getEntityCache().removeResult(entityCacheEnabled, clazz, primaryKey);
	}

	public void setEntityCache(EntityCache entityCache) {
		_entityCache = entityCache;
	}

	private static EntityCache _entityCache;

}