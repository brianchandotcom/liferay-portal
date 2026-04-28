/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.Type;

/**
 * @author Brian Wing Shun Chan
 */
public class ProjectionImpl implements Projection {

	public ProjectionImpl(Projection projection) {
		_projection = projection;
	}

	public ProjectionImpl(ProjectionType projectionType, String propertyName) {
		_projectionType = projectionType;
		_propertyName = propertyName;
	}

	public ProjectionImpl(String alias, Projection projection) {
		_alias = alias;
		_projection = projection;
	}

	public ProjectionImpl(
		String[] columnAliases, String groupBy, String sql, Type[] types) {

		_columnAliases = columnAliases;
		_groupBy = groupBy;
		_sql = sql;
		_types = types;
	}

	public String getAlias() {
		return _alias;
	}

	public String[] getColumnAliases() {
		return _columnAliases;
	}

	public String getGroupBy() {
		return _groupBy;
	}

	public Projection getProjection() {
		return _projection;
	}

	public ProjectionType getProjectionType() {
		return _projectionType;
	}

	public String getPropertyName() {
		return _propertyName;
	}

	public String getSQL() {
		return _sql;
	}

	public Type[] getTypes() {
		return _types;
	}

	@Override
	public String toString() {
		if (_alias != null) {
			return _projection + " as " + _alias;
		}

		if (_sql != null) {
			return _sql;
		}

		if (_projection != null) {
			return "distinct " + _projection;
		}

		if (_projectionType == ProjectionType.AVG) {
			return "avg(" + _propertyName + ")";
		}
		else if (_projectionType == ProjectionType.COUNT) {
			return "count(" + _propertyName + ")";
		}
		else if (_projectionType == ProjectionType.COUNT_DISTINCT) {
			return "count(distinct " + _propertyName + ")";
		}
		else if (_projectionType == ProjectionType.MAX) {
			return "max(" + _propertyName + ")";
		}
		else if (_projectionType == ProjectionType.MIN) {
			return "min(" + _propertyName + ")";
		}
		else if (_projectionType == ProjectionType.ROW_COUNT) {
			return "count(*)";
		}
		else if (_projectionType == ProjectionType.SUM) {
			return "sum(" + _propertyName + ")";
		}

		return "{projectionType=" + _projectionType + "}";
	}

	private String _alias;
	private String[] _columnAliases;
	private String _groupBy;
	private Projection _projection;
	private ProjectionType _projectionType;
	private String _propertyName;
	private String _sql;
	private Type[] _types;

}