/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.orm;

/**
 * @author Brian Wing Shun Chan
 */
public class ProjectionFactoryUtil {

	public static Projection alias(Projection projection, String alias) {
		return _projectionFactory.alias(projection, alias);
	}

	public static Projection avg(String propertyName) {
		return _projectionFactory.avg(propertyName);
	}

	public static Projection count(String propertyName) {
		return _projectionFactory.count(propertyName);
	}

	public static Projection countDistinct(String propertyName) {
		return _projectionFactory.countDistinct(propertyName);
	}

	public static Projection distinct(Projection projection) {
		return _projectionFactory.distinct(projection);
	}

	public static ProjectionFactory getProjectionFactory() {
		return _projectionFactory;
	}

	public static Projection groupProperty(String propertyName) {
		return _projectionFactory.groupProperty(propertyName);
	}

	public static Projection max(String propertyName) {
		return _projectionFactory.max(propertyName);
	}

	public static Projection min(String propertyName) {
		return _projectionFactory.min(propertyName);
	}

	public static ProjectionList projectionList() {
		return _projectionFactory.projectionList();
	}

	public static Projection property(String propertyName) {
		return _projectionFactory.property(propertyName);
	}

	public static Projection rowCount() {
		return _projectionFactory.rowCount();
	}

	public static Projection sqlGroupProjection(
		String sql, String groupBy, String[] columnAliases, Type[] types) {

		return _projectionFactory.sqlGroupProjection(
			sql, groupBy, columnAliases, types);
	}

	public static Projection sqlProjection(
		String sql, String[] columnAliases, Type[] types) {

		return _projectionFactory.sqlProjection(sql, columnAliases, types);
	}

	public static Projection sum(String propertyName) {
		return _projectionFactory.sum(propertyName);
	}

	public void setProjectionFactory(ProjectionFactory projectionFactory) {
		_projectionFactory = projectionFactory;
	}

	private static ProjectionFactory _projectionFactory;

}