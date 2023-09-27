/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.orm;

/**
 * @author Brian Wing Shun Chan
 */
public interface SQLQuery extends Query {

	public SQLQuery addEntity(String alias, Class<?> entityClass);

	public SQLQuery addScalar(String columnAlias, Type type);

	public SQLQuery addSynchronizedEntityClass(Class<?> entityClass);

	public SQLQuery addSynchronizedEntityClasses(Class<?>... entityClasses);

	public SQLQuery addSynchronizedEntityName(String entityName);

	public SQLQuery addSynchronizedEntityNames(String... entityNames);

	public SQLQuery addSynchronizedQuerySpace(String querySpace);

	public SQLQuery addSynchronizedQuerySpaces(String... querySpaces);

}