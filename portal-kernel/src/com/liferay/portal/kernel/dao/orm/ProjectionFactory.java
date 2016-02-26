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
public interface ProjectionFactory {

	public Projection alias(Projection projection, String alias);

	public Projection avg(String propertyName);

	public Projection count(String propertyName);

	public Projection countDistinct(String propertyName);

	public Projection distinct(Projection projection);

	public Projection groupProperty(String propertyName);

	public Projection max(String propertyName);

	public Projection min(String propertyName);

	public ProjectionList projectionList();

	public Projection property(String propertyName);

	public Projection rowCount();

	public Projection sqlGroupProjection(
		String sql, String groupBy, String[] columnAliases, Type[] types);

	public Projection sqlProjection(
		String sql, String[] columnAliases, Type[] types);

	public Projection sum(String propertyName);

}