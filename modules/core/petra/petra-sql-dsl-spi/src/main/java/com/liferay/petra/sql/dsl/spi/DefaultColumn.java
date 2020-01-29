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

package com.liferay.petra.sql.dsl.spi;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.ast.ASTNodeListener;
import com.liferay.petra.sql.dsl.base.BaseTable;
import com.liferay.petra.sql.dsl.expression.Alias;
import com.liferay.petra.sql.dsl.spi.ast.BaseASTNode;
import com.liferay.petra.sql.dsl.spi.expression.DefaultAlias;
import com.liferay.petra.sql.dsl.spi.expression.DefaultExpression;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Preston Crary
 */
public class DefaultColumn<T extends BaseTable<T>, C>
	extends BaseASTNode implements Column<T, C>, DefaultExpression<C> {

	public DefaultColumn(
		T table, String columnName, Class<C> columnType, int sqlType) {

		_table = Objects.requireNonNull(table);
		_columnName = Objects.requireNonNull(columnName);
		_columnType = Objects.requireNonNull(columnType);
		_sqlType = sqlType;
	}

	@Override
	public Alias<C> as(String name) {
		if (_columnName.equals(name)) {
			return new DefaultAlias<>(this, name);
		}

		return new DefaultAlias<>(_table.aliasColumn(this, name), name);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DefaultColumn<?, ?>)) {
			return false;
		}

		DefaultColumn<?, ?> column = (DefaultColumn<?, ?>)object;

		if (_columnName.equals(column.getColumnName()) &&
			_table.equals(column._table)) {

			return true;
		}

		return false;
	}

	@Override
	public String getColumnName() {
		return _columnName;
	}

	@Override
	public Class<C> getColumnType() {
		return _columnType;
	}

	@Override
	public int getSQLType() {
		return _sqlType;
	}

	@Override
	public T getTable() {
		return _table;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _columnName);

		return HashUtil.hash(hash, _table);
	}

	@Override
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		consumer.accept(_table.getName());

		consumer.accept(".");
		consumer.accept(_columnName);
	}

	private final String _columnName;
	private final Class<C> _columnType;
	private final int _sqlType;
	private final T _table;

}