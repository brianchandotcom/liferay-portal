/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactory;
import com.liferay.portal.kernel.dao.orm.ProjectionList;
import com.liferay.portal.kernel.dao.orm.Type;

/**
 * @author Brian Wing Shun Chan
 */
public class ProjectionFactoryImpl implements ProjectionFactory {

	@Override
	public Projection alias(Projection projection, String alias) {
		return new ProjectionImpl(alias, projection);
	}

	@Override
	public Projection avg(String propertyName) {
		return new ProjectionImpl(ProjectionType.AVG, propertyName);
	}

	@Override
	public Projection count(String propertyName) {
		return new ProjectionImpl(ProjectionType.COUNT, propertyName);
	}

	@Override
	public Projection countDistinct(String propertyName) {
		return new ProjectionImpl(ProjectionType.COUNT_DISTINCT, propertyName);
	}

	@Override
	public Projection distinct(Projection projection) {
		return new ProjectionImpl(projection);
	}

	@Override
	public Projection groupProperty(String propertyName) {
		return new PropertyImpl(true, propertyName);
	}

	@Override
	public Projection max(String propertyName) {
		return new ProjectionImpl(ProjectionType.MAX, propertyName);
	}

	@Override
	public Projection min(String propertyName) {
		return new ProjectionImpl(ProjectionType.MIN, propertyName);
	}

	@Override
	public ProjectionList projectionList() {
		return new ProjectionListImpl();
	}

	@Override
	public Projection property(String propertyName) {
		return new PropertyImpl(propertyName);
	}

	@Override
	public Projection rowCount() {
		return new ProjectionImpl(ProjectionType.ROW_COUNT, null);
	}

	@Override
	public Projection sqlGroupProjection(
		String sql, String groupBy, String[] columnAliases, Type[] types) {

		return new ProjectionImpl(columnAliases, groupBy, sql, types);
	}

	@Override
	public Projection sqlProjection(
		String sql, String[] columnAliases, Type[] types) {

		return new ProjectionImpl(columnAliases, null, sql, types);
	}

	@Override
	public Projection sum(String propertyName) {
		return new ProjectionImpl(ProjectionType.SUM, propertyName);
	}

}