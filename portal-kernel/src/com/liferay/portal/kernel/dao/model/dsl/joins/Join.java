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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.model.dsl.ast.ASTNodeListener;
import com.liferay.portal.kernel.dao.model.dsl.base.BaseASTNode;
import com.liferay.portal.kernel.dao.model.dsl.clause.TableClause;
import com.liferay.portal.kernel.dao.model.dsl.expressions.Predicate;
import com.liferay.portal.kernel.dao.model.dsl.query.OrderByStep;
import com.liferay.portal.kernel.dao.model.dsl.query.Query;
import com.liferay.portal.kernel.dao.model.dsl.query.WhereStep;

import java.util.Objects;

/**
 * @author Preston Crary
 */
public class Join
	extends BaseASTNode implements JoinStep, OrderByStep, Query, WhereStep {

	public Join(
		JoinStep joinStep, JoinType joinType, TableClause tableClause,
		Predicate onPredicate) {

		super(joinStep);

		_joinType = Objects.requireNonNull(joinType);
		_tableClause = Objects.requireNonNull(tableClause);
		_onPredicate = Objects.requireNonNull(onPredicate);
	}

	@Override
	public void doToSQL(StringBundler sb, ASTNodeListener astNodeListener) {
		sb.append(_joinType);

		sb.append(" join ");

		_tableClause.toSQL(sb, astNodeListener);

		sb.append(" on ");

		_onPredicate.toSQL(sb, astNodeListener);
	}

	public JoinType getJoinType() {
		return _joinType;
	}

	public Predicate getOnPredicate() {
		return _onPredicate;
	}

	public TableClause getTableClause() {
		return _tableClause;
	}

	private final JoinType _joinType;
	private final Predicate _onPredicate;
	private final TableClause _tableClause;

}