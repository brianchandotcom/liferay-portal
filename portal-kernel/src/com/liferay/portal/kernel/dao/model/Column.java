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

package com.liferay.portal.kernel.dao.model;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.model.dsl.ast.ASTNodeListener;
import com.liferay.portal.kernel.dao.model.dsl.base.BaseASTNode;
import com.liferay.portal.kernel.dao.model.dsl.expressions.Alias;
import com.liferay.portal.kernel.dao.model.dsl.expressions.Expression;

import java.util.Objects;

/**
 * @author Preston Crary
 */
public class Column<T extends Table, C>
	extends BaseASTNode implements Expression<C> {

	public static <T extends Table, C> Column<T, C> create(
		T table, String columnName, Class<C> columnType, int sqlType) {

		return new Column<>(table, columnName, columnType, sqlType);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Alias<C> as(String name) {
		return new Alias<>(_table.aliasColumn(this, name), name);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Column<?, ?>)) {
			return false;
		}

		Column<?, ?> column = (Column<?, ?>)object;

		if (_columnName.equals(column.getColumnName()) &&
			_table.equals(column._table)) {

			return true;
		}

		return false;
	}

	public String getColumnName() {
		return _columnName;
	}

	public Class<C> getColumnType() {
		return _columnType;
	}

	public int getSQLType() {
		return _sqlType;
	}

	public T getTable() {
		return _table;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _columnName);

		return HashUtil.hash(hash, _table);
	}

	@Override
	protected void doToSQL(StringBundler sb, ASTNodeListener astNodeListener) {
		sb.append(_table.getName());

		sb.append(".");
		sb.append(_columnName);
	}

	private Column(
		T table, String columnName, Class<C> columnType, int sqlType) {

		_table = Objects.requireNonNull(table);
		_columnName = Objects.requireNonNull(columnName);
		_columnType = Objects.requireNonNull(columnType);
		_sqlType = sqlType;
	}

	private final String _columnName;
	private final Class<C> _columnType;
	private final int _sqlType;
	private final T _table;

}