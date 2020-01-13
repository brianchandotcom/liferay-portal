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

package com.liferay.portal.kernel.dao.model.dsl.joins;

import com.liferay.portal.kernel.dao.model.Table;
import com.liferay.portal.kernel.dao.model.dsl.ast.ASTNodeListener;
import com.liferay.portal.kernel.dao.model.dsl.base.BaseASTNode;
import com.liferay.portal.kernel.dao.model.dsl.expressions.Predicate;
import com.liferay.portal.kernel.dao.model.dsl.query.OrderByStep;
import com.liferay.portal.kernel.dao.model.dsl.query.Query;
import com.liferay.portal.kernel.dao.model.dsl.query.WhereStep;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Preston Crary
 */
public class Join
	extends BaseASTNode implements JoinStep, OrderByStep, Query, WhereStep {

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

		consumer.accept(String.valueOf(_joinType));

		consumer.accept(" join ");

		_table.toSQL(consumer, astNodeListener);

		consumer.accept(" on ");

		_onPredicate.toSQL(consumer, astNodeListener);
	}

	private final JoinType _joinType;
	private final Predicate _onPredicate;
	private final Table<?> _table;

}