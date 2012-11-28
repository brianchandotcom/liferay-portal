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

package com.liferay.portal.kernel.dao.orm.escape;

import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactory;
import com.liferay.portal.kernel.dao.orm.ProjectionList;
import com.liferay.portal.kernel.dao.orm.Type;

/**
 * @author Shuyang Zhou
 */
public class EscapedProjectionFactory implements ProjectionFactory {

	public EscapedProjectionFactory(ProjectionFactory projectionFactory) {
		_projectionFactory = projectionFactory;
	}

	public Projection alias(Projection projection, String alias) {
		return _projectionFactory.alias(projection, alias);
	}

	public Projection avg(String propertyName) {
		propertyName = ColumnEscapeUtil.escape(propertyName);

		return _projectionFactory.avg(propertyName);
	}

	public Projection count(String propertyName) {
		propertyName = ColumnEscapeUtil.escape(propertyName);

		return _projectionFactory.count(propertyName);
	}

	public Projection countDistinct(String propertyName) {
		propertyName = ColumnEscapeUtil.escape(propertyName);

		return _projectionFactory.countDistinct(propertyName);
	}

	public Projection distinct(Projection projection) {
		return _projectionFactory.distinct(projection);
	}

	public Projection groupProperty(String propertyName) {
		propertyName = ColumnEscapeUtil.escape(propertyName);

		return _projectionFactory.groupProperty(propertyName);
	}

	public Projection max(String propertyName) {
		propertyName = ColumnEscapeUtil.escape(propertyName);

		return _projectionFactory.max(propertyName);
	}

	public Projection min(String propertyName) {
		propertyName = ColumnEscapeUtil.escape(propertyName);

		return _projectionFactory.min(propertyName);
	}

	public ProjectionList projectionList() {
		return _projectionFactory.projectionList();
	}

	public Projection property(String propertyName) {
		propertyName = ColumnEscapeUtil.escape(propertyName);

		return _projectionFactory.property(propertyName);
	}

	public Projection rowCount() {
		return _projectionFactory.rowCount();
	}

	public Projection sqlProjection(
		String sql, String[] columnAliases, Type[] types) {

		return _projectionFactory.sqlProjection(sql, columnAliases, types);
	}

	public Projection sum(String propertyName) {
		propertyName = ColumnEscapeUtil.escape(propertyName);

		return _projectionFactory.sum(propertyName);
	}

	private ProjectionFactory _projectionFactory;

}