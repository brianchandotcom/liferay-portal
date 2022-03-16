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
import com.liferay.portal.kernel.model.BaseModel;

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

	public Serializable getResult(Class<?> clazz, Serializable primaryKey);

	public void invalidate();

	public void putResult(
		Class<?> clazz, BaseModel<?> baseModel, boolean quiet,
		boolean updateFinderCache);

	public void putResult(
		Class<?> clazz, Serializable primaryKey, Serializable result);

	public void removeCache(String className);

	public void removeResult(Class<?> clazz, BaseModel<?> baseModel);

	public void removeResult(Class<?> clazz, Serializable primaryKey);

}