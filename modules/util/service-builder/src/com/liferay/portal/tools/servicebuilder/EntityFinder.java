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

package com.liferay.portal.tools.servicebuilder;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TextFormatter;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Connor McKay
 */
public class EntityFinder {

	public EntityFinder(
		String name, String returnType, boolean unique, String where,
		boolean dbIndex, List<EntityColumn> columns) {

		_name = name;
		_returnType = returnType;
		_unique = unique;
		_where = where;
		_dbIndex = dbIndex;
		_columns = columns;

		if (isCollection() && isUnique() && !hasArrayableOperator()) {
			throw new IllegalArgumentException(
				"A finder cannot return a Collection and be unique unless " +
					"it has an arrayable column. See the ExpandoColumn " +
						"service.xml declaration for an example.");
		}

		if ((!isCollection() || isUnique()) && hasCustomComparator()) {
			throw new IllegalArgumentException(
				"A unique finder cannot have a custom comparator");
		}
	}

	public EntityColumn getColumn(String name) {
		for (EntityColumn column : _columns) {
			if (name.equals(column.getName())) {
				return column;
			}
		}

		return null;
	}

	public List<EntityColumn> getColumns() {
		return _columns;
	}

	public String getHumanConditions(boolean arrayable) {
		if (_columns.size() == 1) {
			return _columns.get(0).getHumanCondition(arrayable);
		}

		StringBundler sb = new StringBundler(_columns.size() * 2);

		for (EntityColumn column : _columns) {
			sb.append(column.getHumanCondition(arrayable));
			sb.append(" and ");
		}

		if (!_columns.isEmpty()) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	public String getName() {
		return _name;
	}

	public String getNames() {
		return TextFormatter.formatPlural(_name);
	}

	public String getReturnType() {
		return _returnType;
	}

	public String getWhere() {
		return _where;
	}

	public boolean hasArrayableOperator() {
		for (EntityColumn column : _columns) {
			if (column.hasArrayableOperator()) {
				return true;
			}
		}

		return false;
	}

	public boolean hasColumn(String name) {
		return Entity.hasColumn(name, _columns);
	}

	public boolean hasCustomComparator() {
		for (EntityColumn column : _columns) {
			String comparator = column.getComparator();

			if (!comparator.equals(StringPool.EQUAL)) {
				return true;
			}
		}

		return false;
	}

	public boolean isCollection() {
		if ((_returnType != null) && _returnType.equals("Collection")) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isDBIndex() {
		return _dbIndex;
	}

	public boolean isUnique() {
		return _unique;
	}

	private final List<EntityColumn> _columns;
	private final boolean _dbIndex;
	private final String _name;
	private final String _returnType;
	private final boolean _unique;
	private final String _where;

}