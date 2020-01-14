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

package com.liferay.petra.sql.dsl.query;

import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.ast.ASTNodeListener;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Shuyang Zhou
 */
public class QueryTable extends Table<QueryTable> {

	public QueryTable(String name, Query query) {
		super(null, () -> new QueryTable(name, query));

		setAlias(Objects.requireNonNull(name));

		_query = Objects.requireNonNull(query);
	}

	@Override
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		consumer.accept("(");

		_query.toSQL(consumer, astNodeListener);

		consumer.accept(") ");

		consumer.accept(getAlias());
	}

	private final Query _query;

}