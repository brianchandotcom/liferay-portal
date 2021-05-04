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

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface FinderCache {

	public void clearCache();

	public void clearCache(Class<?> clazz);

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #clearCache(Class)}
	 */
	@Deprecated
	public void clearCache(String className);

	public void clearDSLQueryCache(String tableName);

	public void clearLocalCache();

	public Object getResult(FinderPath finderPath, Object[] args);

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #getResult(FinderPath, Object[])}
	 */
	@Deprecated
	public Object getResult(
		FinderPath finderPath, Object[] args,
		BasePersistenceImpl<? extends BaseModel<?>> basePersistenceImpl);

	public void invalidate();

	public void putResult(FinderPath finderPath, Object[] args, Object result);

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #putResult(FinderPath, Object[], Object)}
	 */
	@Deprecated
	public void putResult(
		FinderPath finderPath, Object[] args, Object result, boolean quiet);

	public void removeCache(String className);

	public void removeResult(FinderPath finderPath, Object[] args);

}