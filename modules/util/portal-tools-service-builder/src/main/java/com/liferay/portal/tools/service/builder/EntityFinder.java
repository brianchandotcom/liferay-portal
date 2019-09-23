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

package com.liferay.portal.tools.service.builder;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.TextFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Connor McKay
 */
public class EntityFinder {

	public EntityFinder(
		String name, String returnType, boolean unique, String where,
		String dbWhere, boolean dbIndex, List<EntityColumn> entityColumns) {

		_name = name;
		_returnType = returnType;
		_unique = unique;
		_where = where;
		_dbWhere = dbWhere;
		_dbIndex = dbIndex;
		_entityColumns = entityColumns;

		_arrayableColumns = new ArrayList<>();

		for (EntityColumn column : _entityColumns) {
			if (column.hasArrayableOperator()) {
				_arrayableColumns.add(column);
			}
		}

		if (isCollection() && isUnique() && !hasArrayableOperator()) {
			throw new IllegalArgumentException(
				"A finder cannot return a Collection and be unique unless it " +
					"has an arrayable column. See the ExpandoColumn " +
						"service.xml declaration for an example.");
		}

		if ((!isCollection() || isUnique()) && hasCustomComparator()) {
			throw new IllegalArgumentException(
				"A unique finder cannot have a custom comparator");
		}
	}

	public List<EntityColumn> getArrayableColumns() {
		return _arrayableColumns;
	}

	public String getDBWhere() {
		return _dbWhere;
	}

	public EntityColumn getEntityColumn(String name) {
		for (EntityColumn entityColumn : _entityColumns) {
			if (name.equals(entityColumn.getName())) {
				return entityColumn;
			}
		}

		return null;
	}

	public List<EntityColumn> getEntityColumns() {
		return _entityColumns;
	}

	public String getHumanConditions(boolean arrayable) {
		if (_entityColumns.size() == 1) {
			EntityColumn entityColumn = _entityColumns.get(0);

			return entityColumn.getHumanCondition(arrayable);
		}

		StringBundler sb = new StringBundler(_entityColumns.size() * 2);

		for (EntityColumn column : _entityColumns) {
			sb.append(column.getHumanCondition(arrayable));
			sb.append(" and ");
		}

		if (!_entityColumns.isEmpty()) {
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
		for (EntityColumn column : _entityColumns) {
			if (column.hasArrayableOperator()) {
				return true;
			}
		}

		return false;
	}

	public boolean hasArrayablePagination() {
		for (EntityColumn column : _entityColumns) {
			if (column.hasArrayablePagination()) {
				return true;
			}
		}

		return false;
	}

	public boolean hasCustomComparator() {
		for (EntityColumn column : _entityColumns) {
			String comparator = column.getComparator();

			if (!comparator.equals(StringPool.EQUAL)) {
				return true;
			}
		}

		return false;
	}

	public boolean hasEntityColumn(String name) {
		return Entity.hasEntityColumn(name, _entityColumns);
	}

	public boolean isCollection() {
		if ((_returnType != null) && _returnType.equals("Collection")) {
			return true;
		}

		return false;
	}

	public boolean isDBIndex() {
		return _dbIndex;
	}

	public boolean isUnique() {
		return _unique;
	}

	private final List<EntityColumn> _arrayableColumns;
	private final boolean _dbIndex;
	private final String _dbWhere;
	private final List<EntityColumn> _entityColumns;
	private final String _name;
	private final String _returnType;
	private final boolean _unique;
	private final String _where;

}