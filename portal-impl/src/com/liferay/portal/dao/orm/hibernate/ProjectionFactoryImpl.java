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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactory;
import com.liferay.portal.kernel.dao.orm.ProjectionList;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.Validator;

import org.hibernate.criterion.Projections;

/**
 * @author Brian Wing Shun Chan
 */
@DoPrivileged
public class ProjectionFactoryImpl implements ProjectionFactory {

	public Projection alias(Projection projection, String alias) {
		ProjectionImpl projectionImpl = (ProjectionImpl)projection;

		return new ProjectionImpl(
			Projections.alias(projectionImpl.getWrappedProjection(), alias));
	}

	public Projection avg(String propertyName) {
		return new ProjectionImpl(Projections.avg(propertyName));
	}

	public Projection count(String propertyName) {
		return new ProjectionImpl(Projections.count(propertyName));
	}

	public Projection countDistinct(String propertyName) {
		return new ProjectionImpl(Projections.countDistinct(propertyName));
	}

	public Projection distinct(Projection projection) {
		ProjectionImpl projectionImpl = (ProjectionImpl)projection;

		return new ProjectionImpl(
			Projections.distinct(projectionImpl.getWrappedProjection()));
	}

	public Projection groupProperty(String propertyName) {
		return new ProjectionImpl(Projections.groupProperty(propertyName));
	}

	public Projection max(String propertyName) {
		return new ProjectionImpl(Projections.max(propertyName));
	}

	public Projection min(String propertyName) {
		return new ProjectionImpl(Projections.min(propertyName));
	}

	public ProjectionList projectionList() {
		return new ProjectionListImpl(Projections.projectionList());
	}

	public Projection property(String propertyName) {
		return new ProjectionImpl(Projections.property(propertyName));
	}

	public Projection rowCount() {
		return new ProjectionImpl(Projections.rowCount());
	}

	public Projection sqlProjection(
		String sql, String[] columnAliases, Type[] types) {

		if (Validator.isNull(types)) {
			return new ProjectionImpl(
				Projections.sqlProjection(sql, columnAliases, null));
		}

		org.hibernate.type.Type[] hibernateTypes =
			new org.hibernate.type.Type[types.length];

		for (int i = 0; i < types.length; i++) {
			hibernateTypes[i] = TypeTranslator.translate(types[i]);
		}

		return new ProjectionImpl(
			Projections.sqlProjection(sql, columnAliases, hibernateTypes));
	}

	public Projection sum(String propertyName) {
		return new ProjectionImpl(Projections.sum(propertyName));
	}

}