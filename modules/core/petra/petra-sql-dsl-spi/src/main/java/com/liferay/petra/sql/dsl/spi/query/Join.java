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

import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.ast.ASTNodeListener;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.petra.sql.dsl.spi.ast.BaseASTNode;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Preston Crary
 */
public class Join extends BaseASTNode implements DefaultJoinStep {

	public Join(
		JoinStep joinStep, JoinType joinType, Table<?> table,
		Predicate onPredicate) {

		super(joinStep);

		_joinType = Objects.requireNonNull(joinType);
		_table = Objects.requireNonNull(table);
		_onPredicate = Objects.requireNonNull(onPredicate);
	}

	public JoinType getJoinType() {
		return _joinType;
	}

	public Predicate getOnPredicate() {
		return _onPredicate;
	}

	public Table<?> getTable() {
		return _table;
	}

	@Override
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		consumer.accept(_joinType.getStringWithJoin());

		_table.toSQL(consumer, astNodeListener);

		consumer.accept(" on ");

		_onPredicate.toSQL(consumer, astNodeListener);
	}

	private final JoinType _joinType;
	private final Predicate _onPredicate;
	private final Table<?> _table;

}