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

package com.liferay.batch.planner.internal.sql.dsl;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Igor Beslic
 */
public class PredicateBuilder<Z extends BaseTable<Z>> {

	public static PredicateBuilder instance(BaseTable<?> table) {
		return new PredicateBuilder(table);
	}

	public <C> PredicateBuilder andEq(Column<Z, C> column, C value) {
		_getColumn(column.getName());

		if (_predicate == null) {
			_predicate = column.eq(value);
		}
		else {
			_predicate = _predicate.and(column.eq(value));
		}

		return this;
	}

	public <T> PredicateBuilder andEq(String columnName, T value) {
		return andEq((Column<Z, T>)_getColumn(columnName), value);
	}

	public PredicateBuilder andLike(Column<Z, String> column, String value) {
		if (Validator.isNull(value)) {
			return this;
		}

		_getColumn(column.getName());

		if (_predicate == null) {
			_predicate = column.like(
				StringUtil.quote(value, StringPool.PERCENT));
		}
		else {
			_predicate = _predicate.and(
				column.like(StringUtil.quote(value, StringPool.PERCENT)));
		}

		return this;
	}

	public PredicateBuilder andLike(String columnName, String value) {
		return andLike((Column<Z, String>)_getColumn(columnName), value);
	}

	public Predicate build() {
		return _predicate;
	}

	private PredicateBuilder(Z baseTable) {
		_baseTable = baseTable;
	}

	private <C> Column<Z, ?> _getColumn(String name) {
		Column<Z, ?> column = _baseTable.getColumn(name);

		if (column != null) {
			return column;
		}

		throw new IllegalArgumentException(
			StringBundler.concat(
				"Table ", _baseTable.getTableName(), " does not have column ",
				name));
	}

	private final BaseTable<Z> _baseTable;
	private Predicate _predicate;

}