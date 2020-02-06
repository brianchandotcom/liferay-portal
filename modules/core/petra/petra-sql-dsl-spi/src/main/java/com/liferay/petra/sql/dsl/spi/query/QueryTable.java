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

package com.liferay.petra.sql.dsl.spi.query;

import com.liferay.petra.sql.dsl.ast.ASTNodeListener;
import com.liferay.petra.sql.dsl.base.BaseTable;
import com.liferay.petra.sql.dsl.query.DSLQuery;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Shuyang Zhou
 */
public class QueryTable extends BaseTable<QueryTable> {

	public QueryTable(String name, DSLQuery dslQuery) {
		super(null, () -> new QueryTable(name, dslQuery));

		setAlias(Objects.requireNonNull(name));

		_dslQuery = Objects.requireNonNull(dslQuery);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return System.identityHashCode(this);
	}

	@Override
	public void toSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		if (astNodeListener != null) {
			astNodeListener.process(this);
		}

		consumer.accept("(");

		_dslQuery.toSQL(consumer, astNodeListener);

		consumer.accept(") ");

		consumer.accept(getName());
	}

	private final DSLQuery _dslQuery;

}