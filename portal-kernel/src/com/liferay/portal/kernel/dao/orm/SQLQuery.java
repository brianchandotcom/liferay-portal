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