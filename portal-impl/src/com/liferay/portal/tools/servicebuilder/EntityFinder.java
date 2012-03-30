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

package com.liferay.portal.tools.servicebuilder;

import com.liferay.portal.kernel.util.StringBundler;
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
	}

	public EntityColumn getColumn(String name) {
		for (EntityColumn column : _columns) {
			if (column.getName().equals(name)) {
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

	private List<EntityColumn> _columns;
	private boolean _dbIndex;
	private String _name;
	private String _returnType;
	private boolean _unique;
	private String _where;

}