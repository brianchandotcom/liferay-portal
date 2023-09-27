/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl.spi;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.ast.ASTNodeListener;
import com.liferay.petra.sql.dsl.base.BaseTable;
import com.liferay.petra.sql.dsl.expression.ColumnAlias;
import com.liferay.petra.sql.dsl.spi.ast.BaseASTNode;
import com.liferay.petra.sql.dsl.spi.expression.DefaultColumnAlias;
import com.liferay.petra.sql.dsl.spi.expression.DefaultExpression;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Preston Crary
 */
public class DefaultColumn<T extends BaseTable<T>, C>
	extends BaseASTNode implements Column<T, C>, DefaultExpression<C> {

	public DefaultColumn(
		T table, String name, Class<C> javaType, int sqlType, int flags) {

		_table = Objects.requireNonNull(table);
		_name = Objects.requireNonNull(name);
		_javaType = Objects.requireNonNull(javaType);
		_sqlType = sqlType;

		if ((flags & FLAG_PRIMARY) != 0) {
			flags |= FLAG_NULLITY;
		}

		_flags = flags;
	}

	@Override
	public ColumnAlias<T, C> as(String name) {
		if (_name.equals(name)) {
			return new DefaultColumnAlias<>(this, name);
		}

		return new DefaultColumnAlias<>(_table.aliasColumn(this, name), name);
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

		if (_name.equals(column.getName()) &&
			_table.equals(column.getTable())) {

			return true;
		}

		return false;
	}

	@Override
	public int getFlags() {
		return _flags;
	}

	@Override
	public Class<C> getJavaType() {
		return _javaType;
	}

	@Override
	public String getName() {
		return _name;
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
		int hash = HashUtil.hash(0, _name);

		return HashUtil.hash(hash, _table);
	}

	@Override
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		consumer.accept(_table.getName());
		consumer.accept(".");
		consumer.accept(_name);
	}

	private final int _flags;
	private final Class<C> _javaType;
	private final String _name;
	private final int _sqlType;
	private final T _table;

}