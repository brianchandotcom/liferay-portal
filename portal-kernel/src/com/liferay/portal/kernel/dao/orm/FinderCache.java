/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.orm;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface FinderCache {

	public void clearCache();

	public void clearCache(Class<?> clazz);

	public void clearDSLQueryCache(String tableName);

	public void clearLocalCache();

	public Object getResult(
		FinderPath finderPath, Object[] args,
		BasePersistence<?> basePersistence);

	public void invalidate();

	public void putResult(FinderPath finderPath, Object[] args, Object result);

	public void removeCache(String className);

	public void removeResult(FinderPath finderPath, Object[] args);

}