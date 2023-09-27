/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl.base;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.ast.ASTNodeListener;
import com.liferay.petra.sql.dsl.factory.ColumnFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Preston Crary
 */
public abstract class BaseTable<T extends BaseTable<T>> implements Table<T> {

	public BaseTable(String tableName, Supplier<T> tableSupplier) {
		_tableName = tableName;
		_tableSupplier = Objects.requireNonNull(tableSupplier);
	}

	public <C> Column<T, C> aliasColumn(
		Column<T, C> column, String columnAlias) {

		T table = _tableSupplier.get();

		table.setAlias(_alias);

		column = _COLUMN_FACTORY.createColumn(
			table, column.getName(), column.getJavaType(), column.getSQLType(),
			column.getFlags());

		table.putColumn(columnAlias, column);

		return column;
	}

	@Override
	public T as(String alias) {
		T table = _tableSupplier.get();

		table.setAlias(alias);

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

		return _tableName.equals(table.getTableName());
	}

	@Override
	public Column<T, ?> getColumn(String name) {
		return _columnMap.get(name);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <C> Column<T, C> getColumn(String name, Class<C> clazz) {
		Column<T, ?> column = _columnMap.get(name);

		if ((column == null) || !clazz.isAssignableFrom(column.getJavaType())) {
			return null;
		}

		return (Column<T, C>)column;
	}

	@Override
	public Collection<Column<T, ?>> getColumns() {
		return Collections.unmodifiableCollection(_columnMap.values());
	}

	@Override
	public String getName() {
		if (_alias == null) {
			return _tableName;
		}

		return _alias;
	}

	@Override
	public String getTableName() {
		return _tableName;
	}

	@Override
	public int hashCode() {
		return _tableName.hashCode();
	}

	@Override
	public void toSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		if (astNodeListener != null) {
			astNodeListener.process(this);
		}

		consumer.accept(_tableName);

		if (_alias != null) {
			consumer.accept(" ");
			consumer.accept(_alias);
		}
	}

	@Override
	public String toString() {
		return toSQL(null);
	}

	protected <C> Column<T, C> createColumn(
		String name, Class<C> javaType, int sqlType, int flags) {

		@SuppressWarnings("unchecked")
		Column<T, C> column = _COLUMN_FACTORY.createColumn(
			(T)this, name, javaType, sqlType, flags);

		_columnMap.put(name, column);

		return column;
	}

	protected <C> void putColumn(String name, Column<T, C> column) {
		_columnMap.put(name, column);
	}

	protected void setAlias(String alias) {
		_alias = alias;
	}

	private static final ColumnFactory _COLUMN_FACTORY;

	static {
		ServiceLoader<ColumnFactory> serviceLoader = ServiceLoader.load(
			ColumnFactory.class, ColumnFactory.class.getClassLoader());

		Iterator<ColumnFactory> iterator = serviceLoader.iterator();

		_COLUMN_FACTORY = iterator.next();
	}

	private String _alias;
	private final Map<String, Column<T, ?>> _columnMap = new LinkedHashMap<>();
	private final String _tableName;
	private final Supplier<T> _tableSupplier;

}