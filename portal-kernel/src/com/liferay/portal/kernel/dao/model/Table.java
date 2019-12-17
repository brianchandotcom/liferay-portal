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

import com.liferay.portal.kernel.dao.model.dsl.ast.ASTNodeVisitor;
import com.liferay.portal.kernel.dao.model.dsl.ast.impl.DefaultASTNodeVisitor;
import com.liferay.portal.kernel.dao.model.dsl.clause.TableClause;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Preston Crary
 */
public abstract class Table<T extends Table> implements TableClause {

	public Table(String tableName, Supplier<T> tableSupplier) {
		_tableName = Objects.requireNonNull(tableName);
		_tableSupplier = Objects.requireNonNull(tableSupplier);
	}

	@Override
	public void accept(ASTNodeVisitor astNodeVisitor) {
		astNodeVisitor.visit(this);
	}

	public T as(String alias) {
		T table = _tableSupplier.get();

		Table<?> castTable = (Table<?>)table;

		castTable._alias = alias;

		return table;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Table<?>)) {
			return false;
		}

		Table<?> table = (Table<?>)object;

		if (_tableName.equals(table._tableName)) {
			return true;
		}

		return false;
	}

	public String getAlias() {
		return _alias;
	}

	public Column<T, ?> getColumn(String columnName) {
		return _columnMap.get(columnName);
	}

	public Collection<Column<T, ?>> getColumns() {
		return _columnMap.values();
	}

	@Override
	public String getName() {
		if (_alias == null) {
			return _tableName;
		}

		return _alias;
	}

	public String getTableName() {
		return _tableName;
	}

	@Override
	public int hashCode() {
		return _tableName.hashCode();
	}

	@Override
	public String toString() {
		ASTNodeVisitor astNodeVisitor = new DefaultASTNodeVisitor();

		astNodeVisitor.visit(this);

		return astNodeVisitor.toString();
	}

	@SuppressWarnings("unchecked")
	protected <C> Column<T, C> aliasColumn(Column<T, C> column, String alias) {
		T table = _tableSupplier.get();

		Table<T> castTable = (Table<T>)table;

		castTable._alias = _alias;

		Map<String, Column<T, ?>> columnMap = new HashMap<>(_columnMap);

		columnMap.put(alias, column);

		castTable._columnMap = Collections.unmodifiableMap(columnMap);

		return Column.create(
			table, column.getColumnName(), column.getColumnType(),
			column.getSQLType());
	}

	@SafeVarargs
	protected final void setColumns(Column<T, ?>... columns) {
		Map<String, Column<T, ?>> columnMap = new HashMap<>();

		for (Column<T, ?> column : columns) {
			columnMap.put(column.getColumnName(), column);
		}

		_columnMap = Collections.unmodifiableMap(columnMap);
	}

	private String _alias;
	private Map<String, Column<T, ?>> _columnMap;
	private final String _tableName;
	private final Supplier<T> _tableSupplier;

}