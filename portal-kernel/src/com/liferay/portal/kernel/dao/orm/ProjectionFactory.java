/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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