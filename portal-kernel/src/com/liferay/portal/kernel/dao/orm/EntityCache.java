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

import com.liferay.portal.kernel.cache.PortalCache;

import java.io.Serializable;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface EntityCache {

	public void clearCache();

	public void clearCache(Class<?> clazz);

	public void clearLocalCache();

	public PortalCache<Serializable, Serializable> getPortalCache(
		Class<?> clazz);

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 * 				#getResult(Class, Serializable)}
	 */
	@Deprecated
	public Serializable getResult(
		boolean entityCacheEnabled, Class<?> clazz, Serializable primaryKey);

	public Serializable getResult(Class<?> clazz, Serializable primaryKey);

	public void invalidate();

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public Serializable loadResult(
		boolean entityCacheEnabled, Class<?> clazz, Serializable primaryKey,
		SessionFactory sessionFactory);

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 * 				#putResult(Class, Serializable, Serializable)}
	 */
	@Deprecated
	public void putResult(
		boolean entityCacheEnabled, Class<?> clazz, Serializable primaryKey,
		Serializable result);

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 * 				#putResult(Class, Serializable, Serializable, boolean)}
	 */
	@Deprecated
	public void putResult(
		boolean entityCacheEnabled, Class<?> clazz, Serializable primaryKey,
		Serializable result, boolean quiet);

	public void putResult(
		Class<?> clazz, Serializable primaryKey, Serializable result);

	public void putResult(
		Class<?> clazz, Serializable primaryKey, Serializable result,
		boolean quiet);

	public void removeCache(String className);

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 * 				#removeResult(Class, Serializable)}
	 */
	@Deprecated
	public void removeResult(
		boolean entityCacheEnabled, Class<?> clazz, Serializable primaryKey);

	public void removeResult(Class<?> clazz, Serializable primaryKey);

}