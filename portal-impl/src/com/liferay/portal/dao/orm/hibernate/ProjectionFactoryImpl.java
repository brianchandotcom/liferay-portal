/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactory;
import com.liferay.portal.kernel.dao.orm.ProjectionList;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.hibernate.criterion.Projections;

/**
 * @author Brian Wing Shun Chan
 */
public class ProjectionFactoryImpl implements ProjectionFactory {

	@Override
	public Projection alias(Projection projection, String alias) {
		ProjectionImpl projectionImpl = (ProjectionImpl)projection;

		return new ProjectionImpl(
			Projections.alias(projectionImpl.getWrappedProjection(), alias));
	}

	@Override
	public Projection avg(String propertyName) {
		return new ProjectionImpl(Projections.avg(propertyName));
	}

	@Override
	public Projection count(String propertyName) {
		return new ProjectionImpl(Projections.count(propertyName));
	}

	@Override
	public Projection countDistinct(String propertyName) {
		return new ProjectionImpl(Projections.countDistinct(propertyName));
	}

	@Override
	public Projection distinct(Projection projection) {
		ProjectionImpl projectionImpl = (ProjectionImpl)projection;

		return new ProjectionImpl(
			Projections.distinct(projectionImpl.getWrappedProjection()));
	}

	@Override
	public Projection groupProperty(String propertyName) {
		return new ProjectionImpl(Projections.groupProperty(propertyName));
	}

	@Override
	public Projection max(String propertyName) {
		return new ProjectionImpl(Projections.max(propertyName));
	}

	@Override
	public Projection min(String propertyName) {
		return new ProjectionImpl(Projections.min(propertyName));
	}

	@Override
	public ProjectionList projectionList() {
		return new ProjectionListImpl(Projections.projectionList());
	}

	@Override
	public Projection property(String propertyName) {
		return new ProjectionImpl(Projections.property(propertyName));
	}

	@Override
	public Projection rowCount() {
		return new ProjectionImpl(Projections.rowCount());
	}

	@Override
	public Projection sqlGroupProjection(
		String sql, String groupBy, String[] columnAliases, Type[] types) {

		if (ArrayUtil.isEmpty(types)) {
			return new ProjectionImpl(
				Projections.sqlGroupProjection(
					sql, groupBy, columnAliases, null));
		}

		org.hibernate.type.Type[] hibernateTypes =
			new org.hibernate.type.Type[types.length];

		for (int i = 0; i < types.length; i++) {
			hibernateTypes[i] = TypeTranslator.translate(types[i]);
		}

		return new ProjectionImpl(
			Projections.sqlGroupProjection(
				sql, groupBy, columnAliases, hibernateTypes));
	}

	@Override
	public Projection sqlProjection(
		String sql, String[] columnAliases, Type[] types) {

		if (ArrayUtil.isEmpty(types)) {
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

	@Override
	public Projection sum(String propertyName) {
		return new ProjectionImpl(Projections.sum(propertyName));
	}

}